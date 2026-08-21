package com.paul.fullstackinterviewprep.examples;

/**
 * Q34: Explain how @Transactional works in Spring and when to use it.
 *
 * @Transactional tells Spring to wrap a method call in a database
 * transaction: begin a transaction before the method runs, commit it if the
 * method returns normally, and roll it back if the method throws.
 *
 * How it actually works (AOP proxy mechanism):
 * 1. At startup, Spring detects @Transactional on a bean method and wraps
 *    that bean in a PROXY (JDK dynamic proxy if it implements an interface,
 *    CGLIB subclass proxy otherwise).
 * 2. Callers invoke the proxy, not the real object. The proxy's
 *    TransactionInterceptor runs BEFORE and AFTER the real method:
 *      - before: opens a transaction on the current thread (via a
 *        PlatformTransactionManager), binding a Connection to that thread
 *      - after (success): commits
 *      - after (RuntimeException/Error by default): rolls back
 * 3. Because it's proxy-based AOP, calling an @Transactional method from
 *    ANOTHER method in the SAME class bypasses the proxy entirely (a plain
 *    `this.otherMethod()` call, not through the proxy) - the transaction
 *    advice never fires. This is the #1 real-world @Transactional bug.
 *
 * Important defaults and gotchas:
 * - Only rolls back on unchecked exceptions (RuntimeException/Error) by
 *   default. A checked exception commits unless you add
 *   rollbackFor = Exception.class.
 * - Propagation.REQUIRED (default): joins an existing transaction if one is
 *   active on the thread, otherwise starts a new one.
 * - Propagation.REQUIRES_NEW: always suspends any existing transaction and
 *   starts a fresh one (e.g. for an audit-log write that must persist even
 *   if the outer transaction rolls back).
 * - readOnly = true is a hint to the driver/DB to optimize (e.g. skip dirty
 *   checking, allow read replicas) - use it on pure read methods.
 * - Must be applied to a public method (proxy limitation) called from
 *   OUTSIDE the class through the Spring-managed bean.
 *
 * When to use it:
 * - Any service-layer method that performs multiple related writes that
 *   must succeed or fail together (e.g. debit one account, credit another).
 * - Keep the boundary at the SERVICE layer, not the repository/DAO layer -
 *   a single business operation may call multiple repository methods that
 *   all need to share one transaction.
 * - Avoid putting @Transactional on long-running methods (external HTTP
 *   calls, heavy computation) - it holds a DB connection open the whole
 *   time, starving the connection pool. See Q32_ConnectionPool.
 *
 * This file has no Spring dependency (per project convention, examples/
 * classes run standalone) - it simulates the proxy + interceptor mechanism
 * with plain Java to show what Spring generates for you at runtime.
 */
public class Q34_TransactionalAnnotation {

  public static void main(String[] args) {
    System.out.println("=== @Transactional Simulated via AOP Proxy ===\n");

    demonstrateCommit();
    demonstrateRollbackOnRuntimeException();
    demonstrateCheckedExceptionDoesNotRollback();
    demonstrateSelfInvocationBypassesProxy();
  }

  static void demonstrateCommit() {
    System.out.println("--- Successful call: transaction commits ---");
    AccountService real = new AccountServiceImpl();
    AccountService proxied = TransactionProxyFactory.createProxy(real, "transfer");
    proxied.transfer("A", "B", 100);
    System.out.println();
  }

  static void demonstrateRollbackOnRuntimeException() {
    System.out.println("--- RuntimeException: transaction rolls back ---");
    AccountService real = new FailingAccountServiceImpl(true);
    AccountService proxied = TransactionProxyFactory.createProxy(real, "transfer");
    try {
      proxied.transfer("A", "B", 999999);
    } catch (RuntimeException e) {
      System.out.println("   Caller sees exception: " + e.getMessage());
    }
    System.out.println();
  }

  static void demonstrateCheckedExceptionDoesNotRollback() {
    System.out.println("--- Checked exception (default config): transaction still COMMITS ---");
    System.out.println("   This is the classic gotcha: @Transactional only rolls back on");
    System.out.println("   RuntimeException/Error by default, not checked exceptions,");
    System.out.println("   unless you declare rollbackFor = Exception.class.\n");
  }

  static void demonstrateSelfInvocationBypassesProxy() {
    System.out.println("--- Self-invocation bypasses the proxy (the #1 real bug) ---");
    SelfInvokingService real = new SelfInvokingService();
    // In real Spring, `real` here would be the injected proxy bean.
    real.outer();
    System.out.println("   'inner()' ran but its transactional advice never fired,");
    System.out.println("   because outer() called this.inner() directly - not through the proxy.");
  }

  // ===== Simulated Spring transaction infrastructure =====

  interface AccountService {
    void transfer(String from, String to, int amount);
  }

  static class AccountServiceImpl implements AccountService {
    @Override
    public void transfer(String from, String to, int amount) {
      System.out.println("   [business logic] debit " + from + " " + amount);
      System.out.println("   [business logic] credit " + to + " " + amount);
    }
  }

  static class FailingAccountServiceImpl implements AccountService {
    private final boolean fail;

    FailingAccountServiceImpl(boolean fail) {
      this.fail = fail;
    }

    @Override
    public void transfer(String from, String to, int amount) {
      System.out.println("   [business logic] debit " + from + " " + amount);
      if (fail) {
        throw new IllegalStateException("insufficient funds");
      }
      System.out.println("   [business logic] credit " + to + " " + amount);
    }
  }

  /** Stands in for Spring's TransactionInterceptor wrapped around a JDK dynamic proxy. */
  static class TransactionProxyFactory {
    static AccountService createProxy(AccountService target, String transactionalMethodName) {
      return (from, to, amount) -> {
        System.out.println("   [proxy] BEGIN TRANSACTION");
        try {
          target.transfer(from, to, amount);
          System.out.println("   [proxy] COMMIT");
        } catch (RuntimeException e) {
          System.out.println("   [proxy] ROLLBACK (RuntimeException)");
          throw e;
        }
      };
    }
  }

  /** Demonstrates why calling an @Transactional method from within the same class doesn't work. */
  static class SelfInvokingService {
    void outer() {
      System.out.println("   outer() running (not itself @Transactional)");
      this.inner(); // NOT going through the Spring proxy - advice is skipped
    }

    /* Imagine this annotated @Transactional in real Spring code */
    void inner() {
      System.out.println("   inner() running - annotated @Transactional but proxy was bypassed");
    }
  }
}
