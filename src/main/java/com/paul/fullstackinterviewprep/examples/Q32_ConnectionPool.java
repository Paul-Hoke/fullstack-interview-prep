package com.paul.fullstackinterviewprep.examples;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Q32: Explain a connection pool.
 *
 * A connection pool is a cache of pre-established, reusable connections
 * (typically database connections, but the same idea applies to HTTP or
 * socket connections). Instead of opening a new physical connection for
 * every request and tearing it down afterward, the application "borrows" an
 * already-open connection from the pool and "returns" it when done.
 *
 * Why pooling matters:
 * - Establishing a TCP connection + DB handshake (auth, TLS, session setup)
 *   is expensive (can be 10s-100s of ms). Doing it per-request kills
 *   throughput and latency.
 * - Databases have a hard limit on concurrent connections; pooling caps how
 *   many are opened and reuses them instead of exhausting that limit.
 * - Pools provide backpressure: when all connections are busy, new callers
 *   wait (with a timeout) instead of the DB being overwhelmed by unbounded
 *   connection creation.
 *
 * Key pool parameters:
 * - minimum/initial size - connections kept open even when idle
 * - maximum size - hard ceiling on concurrent connections
 * - connection timeout - how long a caller waits for a free connection
 * - idle timeout / max lifetime - recycle connections periodically (avoids
 *   stale connections, DB-side timeouts, memory leaks)
 * - validation query / test-on-borrow - confirms a pooled connection is
 *   still alive before handing it out
 *
 * In the Spring/Java world this is usually HikariCP (Spring Boot's default),
 * Apache Commons DBCP2, or the vendor-agnostic javax.sql.DataSource
 * interface that all of them implement.
 */
public class Q32_ConnectionPool {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("=== Connection Pool Demo (simplified, hand-rolled) ===\n");

    SimpleConnectionPool pool = new SimpleConnectionPool(3);

    System.out.println("--- Borrow/return within capacity ---");
    PooledConnection c1 = pool.borrow();
    PooledConnection c2 = pool.borrow();
    System.out.println("Borrowed: " + c1 + ", " + c2);
    pool.release(c1);
    System.out.println("Released " + c1 + " back to pool");

    System.out.println("\n--- Reuse instead of recreating ---");
    PooledConnection c3 = pool.borrow();
    System.out.println("Borrowed again: " + c3 + " (came from the pool - notice no new");
    System.out.println("'[expensive] opened...' line was printed, unlike the constructor above)");

    System.out.println("\n--- Pool exhaustion + wait-with-timeout ---");
    PooledConnection c4 = pool.borrow(); // pool now full (c2, c3, c4)
    System.out.println("Pool is now at max capacity (3 in use)");

    long start = System.currentTimeMillis();
    PooledConnection c5 = pool.tryBorrow(500, TimeUnit.MILLISECONDS);
    long waited = System.currentTimeMillis() - start;
    System.out.println("tryBorrow with 500ms timeout while pool is exhausted -> "
        + (c5 == null ? "timed out after " + waited + "ms (as expected)" : "unexpectedly got a connection"));

    pool.release(c2);
    pool.release(c3);
    pool.release(c4);
    pool.shutdown();
  }

  /** A hand-rolled pool: NOT production code, just illustrates the mechanics. */
  static class SimpleConnectionPool {
    private final BlockingQueue<PooledConnection> available;
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    SimpleConnectionPool(int size) {
      this.available = new ArrayBlockingQueue<>(size);
      for (int i = 0; i < size; i++) {
        available.offer(createConnection());
      }
    }

    private PooledConnection createConnection() {
      // Simulates the "expensive" part: opening a real DB connection.
      return new PooledConnection(idGenerator.getAndIncrement());
    }

    PooledConnection borrow() throws InterruptedException {
      return available.take(); // blocks indefinitely until one is free
    }

    PooledConnection tryBorrow(long timeout, TimeUnit unit) throws InterruptedException {
      return available.poll(timeout, unit); // returns null on timeout instead of blocking forever
    }

    void release(PooledConnection connection) {
      connection.resetState();
      available.offer(connection); // hand it back for reuse, don't close it
    }

    void shutdown() {
      System.out.println("\nShutting down pool - closing all physical connections");
      available.forEach(PooledConnection::closeReal);
    }
  }

  static class PooledConnection {
    private final int id;

    PooledConnection(int id) {
      this.id = id;
      System.out.println("   [expensive] opened physical connection #" + id);
    }

    void resetState() {
      // e.g. rollback any open transaction, clear session state, before returning to pool
    }

    void closeReal() {
      System.out.println("   [expensive] closed physical connection #" + id);
    }

    @Override
    public String toString() {
      return "Connection#" + id;
    }
  }
}
