# Senior Backend Engineer — Study Guide

Mapped to the role's stated responsibilities and qualifications. Each section lists core concepts to understand deeply, followed by practical exercises or things to be able to demonstrate.

---

## 1. Java & JVM Backend Development

**The core of the role.** Expect deep questions on how the JVM works, not just the language.

### JVM Internals

- **Heap vs stack memory layout** — The stack stores primitive values and object references for the currently executing method frame; it is thread-local and automatically reclaimed when a frame pops. The heap stores all object instances and is shared across threads, which is why concurrent access requires synchronization. Understanding this split explains why `StackOverflowError` comes from deep recursion while `OutOfMemoryError` comes from unbounded object creation. *(see `Q14_StackVsHeap.java`)*

- **Garbage collection algorithms: G1GC, ZGC, Shenandoah** — G1GC (default since Java 9) divides the heap into equal-sized regions and collects the ones with the most garbage first ("garbage first"), targeting a configurable max pause time. ZGC and Shenandoah are concurrent collectors designed for sub-millisecond pauses even on multi-terabyte heaps by doing most work while the application runs. Choose G1GC for general-purpose workloads, ZGC/Shenandoah when p99 latency SLOs are tight and you can't tolerate stop-the-world pauses.

  ```bash
  # G1GC with 200ms max pause target
  java -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -jar app.jar

  # ZGC for low-latency services (Java 15+, production-ready)
  java -XX:+UseZGC -jar app.jar

  # Enable GC logging to diagnose pause spikes
  java -Xlog:gc*:file=gc.log:time,uptime:filecount=5,filesize=20m -jar app.jar
  ```

- **JIT compilation and warm-up time** — The JVM starts by interpreting bytecode, then the JIT compiler profiles hot code paths and compiles them to native machine code — typically after ~10,000 invocations (C2 threshold). During warm-up, throughput and latency are worse than steady-state, which matters for canary deployments, Lambda cold starts, and load testing (never benchmark a JVM process in its first few seconds). JVM warm-up can be mitigated with ahead-of-time compilation (GraalVM native image) or by prewarming instances before sending them production traffic.

- **Class loading: bootstrap, extension, application classloaders** — The bootstrap classloader loads core JDK classes (`java.lang.*`), the platform classloader (formerly extension) handles JDK modules, and the application classloader handles your classpath. Each classloader delegates to its parent first (parent-delegation model), which prevents user code from replacing `java.lang.String`. Understanding this matters when debugging `ClassNotFoundException`, `NoClassDefFoundError`, or classloader isolation in app servers and plugin architectures.

- **JVM tuning flags** — `-Xms` sets the initial heap size and `-Xmx` sets the maximum; setting them equal prevents the JVM from spending time resizing the heap under load. `-XX:MaxGCPauseMillis` is a soft target that G1GC will attempt to meet by adjusting region collection size. Undersizing the heap causes frequent GCs; oversizing wastes memory and can make full GCs catastrophically long.

  ```bash
  # Production-typical flags for a containerized service
  java \
    -Xms512m -Xmx512m \
    -XX:+UseG1GC \
    -XX:MaxGCPauseMillis=100 \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/var/dumps/heap.hprof \
    -jar app.jar
  ```

**Be able to answer:** "We're seeing latency spikes every 30 seconds — how do you diagnose this?" Check GC logs for stop-the-world pauses correlating with the spike timing. If G1GC full GCs are occurring, the heap may be undersized or objects are being promoted to old gen too fast. Check for large object allocations (objects > region size go directly to old gen) and tune `-XX:G1HeapRegionSize`.

---

### Functional Programming in Java

- **`Function`, `Predicate`, `Supplier`, `Consumer`, `BiFunction`** — These are the core `@FunctionalInterface` types in `java.util.function`. `Function<T,R>` transforms a value, `Predicate<T>` tests a condition, `Supplier<T>` produces a value lazily (useful for deferred computation and test fixtures), `Consumer<T>` performs a side effect, and `BiFunction<T,U,R>` takes two inputs. Knowing which one to reach for makes APIs expressive without forcing callers to use concrete classes. *(see `Q30_FunctionalInterfaces.java`)*

  ```java
  Function<String, Integer>   strLen  = String::length;
  Predicate<String>           isEmpty = String::isEmpty;
  Supplier<List<String>>      newList = ArrayList::new;
  Consumer<String>            printer = System.out::println;
  BiFunction<Integer, Integer, Integer> add = Integer::sum;

  // Compose: first trim, then measure length
  Function<String, Integer> trimLen = strLen.compose(String::trim);
  System.out.println(trimLen.apply("  hello  ")); // 5
  ```

- **Stream pipelines: `map`, `flatMap`, `filter`, `reduce`, `collect`** — Streams are lazy; no work happens until a terminal operation is called, which means you can build complex pipelines cheaply. `flatMap` is the key for flattening nested structures — use it when each element maps to zero-or-more results. Prefer `collect(Collectors.toUnmodifiableList())` over `collect(Collectors.toList())` when the result shouldn't be mutated downstream. *(see `Q20_Streams.java`)*

  ```java
  // Find the top-3 most common words across a list of sentences
  List<String> sentences = List.of("hello world", "hello java", "world of java");

  List<String> top3 = sentences.stream()
      .flatMap(s -> Arrays.stream(s.split(" ")))     // flatten to words
      .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
      .entrySet().stream()
      .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
      .limit(3)
      .map(Map.Entry::getKey)
      .collect(Collectors.toUnmodifiableList());
  // ["java", "hello", "world"]
  ```

- **Immutability and why it matters for concurrency** — An immutable object can be safely shared across threads without synchronization because its state cannot change after construction. This eliminates entire classes of bugs: no need for locks, no risk of one thread seeing half-initialized state. Design immutable value objects by making all fields `final`, not exposing setters, and defensively copying mutable inputs in the constructor. *(see `Q24_Immutability.java`)*

  ```java
  public final class Money {
      private final BigDecimal amount;
      private final Currency currency;

      public Money(BigDecimal amount, Currency currency) {
          this.amount   = Objects.requireNonNull(amount);
          this.currency = Objects.requireNonNull(currency);
      }

      public Money add(Money other) {
          if (!this.currency.equals(other.currency))
              throw new IllegalArgumentException("Currency mismatch");
          return new Money(this.amount.add(other.amount), this.currency); // new instance
      }
      // only getters, no setters
  }
  ```

- **`Optional` as a design tool** — `Optional<T>` communicates to the caller that a value may be absent and forces them to handle that case explicitly, unlike a nullable return which is easy to forget. Use it as a return type for methods that may have no result; never use it as a field type or method parameter (it adds overhead without benefit there). Chain `map`, `flatMap`, and `orElseGet` to transform absent-aware values without nested null checks. *(see `Q21_OptionalClass.java`)*

  ```java
  // Before: nullable chain — easy to miss NPE
  String city = user.getAddress() == null ? null
               : user.getAddress().getCity();

  // After: intent is explicit, compiler enforces handling
  Optional<String> city = Optional.ofNullable(user.getAddress())
      .map(Address::getCity);

  String display = city.orElse("Unknown");
  // or lazily compute a default:
  String display2 = city.orElseGet(() -> configService.getDefaultCity());
  ```

- **Method references and lambda capture** — Method references (`Class::method`) are syntactic sugar for lambdas and are preferred when they directly name the operation being performed. A lambda captures variables from the enclosing scope, but those variables must be effectively final — the compiler rejects capturing a mutable local. Capturing `this` in a lambda keeps the enclosing object alive, which can cause memory leaks in long-lived structures like caches or event bus subscriptions.

  ```java
  List<String> names = List.of("Alice", "Bob", "Charlie");

  // Method reference — preferred when it's a direct delegation
  names.forEach(System.out::println);

  // Effectively final capture
  String prefix = "Hello, ";  // effectively final — never reassigned
  names.stream()
       .map(n -> prefix + n)  // captures prefix legally
       .forEach(System.out::println);

  // This would NOT compile — prefix is mutated
  // prefix = "Hi, "; // <- makes it not effectively final
  ```

- **When functional style hurts readability** — Streams shine for data transformation pipelines, but they become harder to read when logic is complex, when side effects are involved, or when you need to debug intermediate state. A `for` loop with a local variable is often clearer than a multi-level `flatMap` chain. The rule of thumb: if you can't read the stream pipeline aloud in one breath, consider breaking it into named intermediate variables or reverting to a loop.

---

### Concurrency & Threading

- **`synchronized`, `volatile`, `java.util.concurrent`** — `synchronized` acquires an intrinsic lock, ensuring mutual exclusion and memory visibility, but it blocks all other threads even for read-only operations. `volatile` guarantees visibility (changes are immediately visible across threads) but not atomicity — `volatile int x; x++` is still a race condition. Prefer `java.util.concurrent` types over raw `synchronized` for anything non-trivial; they're better tuned and harder to deadlock. *(see `Q17_Synchronization.java`, `Q18_VolatileKeyword.java`)*

  ```java
  // volatile: safe for flags, NOT safe for compound operations
  private volatile boolean running = true;
  public void stop() { running = false; } // write visible to all threads

  // AtomicInteger: safe for compound ops like increment
  private final AtomicInteger counter = new AtomicInteger(0);
  public int nextId() { return counter.incrementAndGet(); } // atomic read-modify-write

  // ReentrantReadWriteLock: multiple readers OR one writer
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  public String read() {
      lock.readLock().lock();
      try { return data; } finally { lock.readLock().unlock(); }
  }
  public void write(String val) {
      lock.writeLock().lock();
      try { data = val; } finally { lock.writeLock().unlock(); }
  }
  ```

- **`ExecutorService`, `ThreadPoolExecutor`, `ForkJoinPool`** — `ExecutorService` decouples task submission from thread management; always shut it down explicitly to avoid leaking threads. `ThreadPoolExecutor` gives full control: core pool size, max pool size, keep-alive time, and the work queue — the queue type (bounded vs unbounded) is the most important choice because an unbounded queue means the pool never grows beyond core size under load. `ForkJoinPool` uses work-stealing and is optimized for divide-and-conquer tasks; it's the backing pool for parallel streams.

  ```java
  // Bounded pool with a rejection policy — production safe
  ExecutorService pool = new ThreadPoolExecutor(
      10,                           // corePoolSize
      50,                           // maximumPoolSize
      60L, TimeUnit.SECONDS,        // keepAlive for idle threads above core
      new ArrayBlockingQueue<>(1000), // bounded queue — will throw when full
      new ThreadPoolExecutor.CallerRunsPolicy() // backpressure: caller executes task
  );

  // Always shut down — otherwise JVM won't exit
  Runtime.getRuntime().addShutdownHook(new Thread(pool::shutdown));
  ```

- **`CompletableFuture` — chaining, exception handling, combining** — `CompletableFuture` lets you build non-blocking pipelines: `thenApply` transforms a result synchronously, `thenCompose` chains async operations (flatMap equivalent), and `thenCombine` merges two independent futures. Always handle exceptions with `exceptionally` or `handle`; an unhandled exception in a `CompletableFuture` is silently swallowed unless you call `join()` or `get()`. Provide an explicit `Executor` to `thenApplyAsync` — otherwise it uses the common ForkJoinPool, which can starve other tasks.

  ```java
  CompletableFuture<User> userFuture    = fetchUser(userId);
  CompletableFuture<Account> acctFuture = fetchAccount(userId);

  CompletableFuture<String> summary = userFuture
      .thenCombine(acctFuture, (user, acct) ->
          user.getName() + " — balance: " + acct.getBalance())
      .thenApplyAsync(String::toUpperCase, ioThreadPool)
      .exceptionally(ex -> {
          log.error("Failed to build summary", ex);
          return "UNAVAILABLE";
      });

  // Non-blocking — returns immediately
  summary.thenAccept(System.out::println);
  ```

- **Lock-free structures: `AtomicInteger`, `ConcurrentHashMap`, `CopyOnWriteArrayList`** — Lock-free structures use CPU-level compare-and-swap (CAS) instructions instead of locks, so they never block — a thread that loses a CAS simply retries. `ConcurrentHashMap` segments the map to allow concurrent reads and writes without locking the whole map. `CopyOnWriteArrayList` is optimized for read-heavy workloads: every write copies the entire backing array, making reads lock-free at the cost of expensive writes — ideal for listener/observer lists that rarely change.

  ```java
  // ConcurrentHashMap: safe concurrent updates
  ConcurrentHashMap<String, AtomicLong> counters = new ConcurrentHashMap<>();

  // computeIfAbsent is atomic — safe to call from multiple threads
  counters.computeIfAbsent("requests", k -> new AtomicLong()).incrementAndGet();

  // merge is also atomic
  counters.merge("requests", new AtomicLong(1),
      (existing, one) -> { existing.addAndGet(1); return existing; });
  ```

- **Common pitfalls: deadlock, livelock, starvation, race conditions** — A deadlock occurs when two threads each hold a lock the other needs; prevent it by always acquiring locks in a consistent global order. Livelock is rarer: threads keep reacting to each other without making progress (like two people in a corridor both stepping aside in the same direction). Starvation happens when a thread never gets CPU time because higher-priority threads monopolize it. Race conditions occur when correctness depends on timing; they're often latent in production and only surface under load.

  ```java
  // Classic deadlock — thread 1 holds lockA, wants lockB
  //                  — thread 2 holds lockB, wants lockA
  // Prevention: always acquire in alphabetical/consistent order
  // or use tryLock with a timeout:
  if (lockA.tryLock(100, TimeUnit.MILLISECONDS)) {
      try {
          if (lockB.tryLock(100, TimeUnit.MILLISECONDS)) {
              try { /* critical section */ }
              finally { lockB.unlock(); }
          }
      } finally { lockA.unlock(); }
  }
  ```

- **`happens-before` and the Java Memory Model** — The Java Memory Model (JMM) defines when a write by one thread is guaranteed to be visible to a read by another. A `happens-before` edge is created by: releasing a monitor (exiting `synchronized`) before acquiring it, a `volatile` write before a subsequent volatile read, `Thread.start()` before any action in the started thread, and a task completion before its `Future.get()` returns. Without a `happens-before` relationship, the JIT and CPU are free to reorder operations, so you can see stale data.

---

### Async Programming Models

- **Callbacks → Futures → Promises → Reactive** — Callbacks were the first async primitive: pass a function to be invoked on completion. They lead to "callback hell" (deeply nested, hard to reason about). Futures (`Future<T>`) let you hold a reference to a pending result but early versions were blocking on `get()`. `CompletableFuture` (Java 8) added composable, non-blocking pipelines. Reactive streams (Project Reactor, RxJava) go further by adding backpressure and treating async streams of events as first-class.

  ```java
  // Callback style (pre-Java 8 pattern — hard to compose)
  service.fetchUser(id, (user, error) -> {
      if (error != null) { handleError(error); return; }
      service.fetchOrders(user.getId(), (orders, err2) -> {
          // nested callback — "callback hell"
      });
  });

  // CompletableFuture style — composable, readable
  service.fetchUser(id)
      .thenCompose(user -> service.fetchOrders(user.getId()))
      .thenAccept(orders -> display(orders))
      .exceptionally(ex -> { handleError(ex); return null; });
  ```

- **`CompletableFuture` vs reactive streams** — `CompletableFuture` models a single async value; Project Reactor's `Mono<T>` (0 or 1 value) and `Flux<T>` (0 to N values) model async streams. Reactive streams add backpressure: the subscriber signals how many items it can handle, preventing a fast producer from overwhelming a slow consumer. Use `CompletableFuture` for straightforward request/response patterns; use reactive when you need streaming, backpressure, or are building on a reactive framework like Spring WebFlux.

  ```java
  // Project Reactor example
  Flux<Order> orders = orderRepository.findByUserId(userId) // returns Flux
      .filter(o -> o.getStatus() == PENDING)
      .map(o -> o.withPriority(calculatePriority(o)))
      .onErrorResume(ex -> Flux.empty()); // graceful degradation

  // Backpressure: subscriber requests 10 at a time
  orders.subscribe(new BaseSubscriber<Order>() {
      protected void hookOnSubscribe(Subscription s) { request(10); }
      protected void hookOnNext(Order o) {
          process(o);
          request(1); // request next item after processing each
      }
  });
  ```

- **Backpressure: what it is and why it matters** — Backpressure is a mechanism for a consumer to signal to a producer "slow down, I can't keep up." Without it, a fast producer writes to an unbounded queue or drops messages, causing either OOM errors or silent data loss. In Kafka, backpressure is implicit: consumer lag grows and the producer naturally slows if the topic fills up. In reactive streams, it is explicit via the `request(n)` protocol. Design async pipelines with backpressure in mind to prevent cascading failures under load.

- **Event loops vs thread-per-request** — Thread-per-request (servlet model, Spring MVC) assigns one thread per incoming request; it is simple to program but threads block during I/O, so you need many threads to handle concurrency, and each thread costs ~1MB of stack. Event loops (Netty, Node.js, Spring WebFlux) use a small fixed pool of threads that never block; all I/O is async, and work is scheduled as callbacks. Event loops win on throughput for I/O-heavy workloads; thread-per-request is simpler to debug and sufficient for most CRUD services.

- **Blocking I/O inside an async context** — Calling a blocking method (JDBC, a REST template `getForObject`, `Thread.sleep`) inside an event loop or reactive pipeline starves the event loop thread and kills throughput for all other requests sharing that thread. The fix is either use an async driver (R2DBC instead of JDBC) or offload the blocking call to a dedicated bounded thread pool (`subscribeOn(Schedulers.boundedElastic())` in Reactor). This is one of the most common mistakes when migrating to reactive.

  ```java
  // WRONG: blocking JDBC call on event loop thread
  Mono<User> user = Mono.fromCallable(() -> jdbcRepo.findById(id)); // blocks!

  // CORRECT: offload to a bounded elastic thread pool for blocking I/O
  Mono<User> user = Mono.fromCallable(() -> jdbcRepo.findById(id))
      .subscribeOn(Schedulers.boundedElastic()); // runs on I/O-safe thread pool
  ```

---

## 2. RESTful API Design

- **HTTP semantics: idempotency, safety, status codes** — A *safe* method (GET, HEAD, OPTIONS) has no side effects; a client can retry it without concern. An *idempotent* method (GET, PUT, DELETE) produces the same server state no matter how many times it is called — critical for retry logic in unreliable networks. POST is neither safe nor idempotent, which is why retrying a payment POST without deduplication creates duplicate charges. Status codes: `201 Created` (POST that creates a resource), `409 Conflict` (resource already exists or business rule violation), `422 Unprocessable Entity` (valid syntax but invalid semantics, e.g. end date before start date).

  ```
  GET    /orders/123        — safe + idempotent (read only)
  PUT    /orders/123        — idempotent (replace entire resource)
  PATCH  /orders/123        — NOT idempotent by default (partial update can compound)
  DELETE /orders/123        — idempotent (deleting an already-deleted resource = 404 or 204, same state)
  POST   /orders            — neither safe nor idempotent (creates new resource each time)
  ```

- **REST constraints** — Statelessness means each request contains all the information the server needs; no session state lives on the server between calls. This is what makes horizontal scaling easy — any instance can serve any request. A uniform interface (standard HTTP methods + resource-oriented URLs) means clients and servers evolve independently. HATEOAS (links embedded in responses) is theoretically elegant but costly to implement; apply it selectively where discoverability genuinely helps clients.

  ```json
  // HATEOAS example: response embeds links to next valid actions
  {
    "orderId": "123",
    "status": "PENDING",
    "_links": {
      "self":   { "href": "/orders/123" },
      "cancel": { "href": "/orders/123/cancel", "method": "POST" },
      "pay":    { "href": "/orders/123/payment", "method": "POST" }
    }
  }
  ```

- **API versioning strategies** — URI path versioning (`/v1/orders`) is the most visible and cache-friendly; it works with any client but couples the version to the URL structure. Header versioning (`Accept: application/vnd.myapi.v2+json`) is cleaner architecturally but harder to test in a browser or curl. Content negotiation lets clients negotiate the exact representation. Regardless of strategy, never break a published API without a deprecation window and migration guide.

  ```
  # URI path (most common, easiest)
  GET /v1/orders/123
  GET /v2/orders/123

  # Custom header
  GET /orders/123
  API-Version: 2

  # Content negotiation
  Accept: application/vnd.company.orders-v2+json
  ```

- **Pagination: cursor-based vs offset** — Offset pagination (`?page=3&size=20`) is simple but degrades on large datasets: `OFFSET 10000 LIMIT 20` forces the database to scan and discard 10,000 rows. It also produces inconsistent results when rows are inserted or deleted between pages (the "page drift" problem). Cursor-based pagination encodes the last-seen record's sort key in an opaque token; the next page query uses `WHERE id > :cursor`, which is O(log n) with an index and stable even with concurrent mutations.

  ```json
  // Cursor-based pagination response
  {
    "data": [ { "id": "abc", "name": "Widget" }, ... ],
    "pagination": {
      "nextCursor": "eyJpZCI6ImFiYyJ9",   // base64-encoded last seen key
      "hasMore": true
    }
  }
  ```
  ```sql
  -- Efficient cursor query — uses index on (created_at, id)
  SELECT * FROM orders
  WHERE (created_at, id) > (:lastCreatedAt, :lastId)
  ORDER BY created_at, id
  LIMIT 20;
  ```

- **Request validation and error response contracts** — Validate at the boundary (controller layer) before business logic runs; don't let invalid data propagate into the domain. Return a consistent error shape so clients can parse errors programmatically rather than scraping message strings. Include a machine-readable error code alongside the human-readable message.

  ```json
  // Consistent error contract — clients parse "code", display "message"
  {
    "status": 422,
    "code": "VALIDATION_ERROR",
    "message": "Request validation failed",
    "errors": [
      { "field": "email",    "code": "INVALID_FORMAT",  "message": "Not a valid email" },
      { "field": "birthDate","code": "FUTURE_DATE",     "message": "Must be in the past" }
    ],
    "traceId": "abc-123-def"
  }
  ```

- **Rate limiting patterns: token bucket, leaky bucket** — Token bucket adds tokens at a fixed rate up to a max capacity; a request consumes one token or is rejected if the bucket is empty. This allows short bursts (the bucket can accumulate tokens when idle) while enforcing a sustained rate limit. Leaky bucket queues requests and processes them at a fixed rate, smoothing out bursts entirely — better for protecting downstream systems that can't handle spikes. Implement rate limiting at the API gateway or with Redis (`INCR` + `EXPIRE`) to enforce limits across multiple instances.

  ```java
  // Redis-based sliding window rate limit (pseudocode)
  String key = "rate:" + clientId + ":" + windowStart;
  long count = redis.incr(key);
  if (count == 1) redis.expire(key, windowSeconds);
  if (count > limit) throw new RateLimitExceededException();
  ```

- **Authentication: OAuth2, JWT, API keys** — OAuth2 is the standard for delegated authorization: a client gets a short-lived access token from an authorization server and presents it to the resource server. JWT (JSON Web Token) is a self-contained token format: the payload carries claims (user ID, roles, expiry) and is signed so the resource server can verify it without a database call. Validate JWT signature, expiry (`exp`), issuer (`iss`), and audience (`aud`) on every request. API keys are simpler but require a database lookup per request and can't carry claims.

  ```java
  // JWT validation in a filter (Spring Security style)
  Claims claims = Jwts.parserBuilder()
      .setSigningKey(publicKey)          // verify signature
      .requireIssuer("https://auth.myco.com")
      .requireAudience("orders-service")
      .build()
      .parseClaimsJws(token)
      .getBody();

  if (claims.getExpiration().before(new Date()))
      throw new ExpiredJwtException("Token expired");

  String userId = claims.getSubject();
  List<String> roles = claims.get("roles", List.class);
  ```

**Practice:** Design a paginated REST API for a resource with filtering, sorting, and field projection. Explain every design decision.

---

## 3. System Design & Architecture

This is where senior-level interviews spend the most time.

### High-Availability & Scalability Patterns

- **Horizontal vs vertical scaling** — Vertical scaling (bigger machine) is simple but has a ceiling and a single point of failure. Horizontal scaling (more machines) requires stateless services so any instance can serve any request, and introduces distributed systems challenges: consensus, distributed caching, and network partitions. Horizontal scaling gets hard when the bottleneck is a stateful resource — the database. Plan for the database bottleneck early: read replicas, connection pooling, and eventually sharding.

- **Stateless services and externalizing state** — A stateless service stores no request-scoped data in memory between calls; all state lives in external systems (databases, caches, message queues). This means any instance is identical and routing is trivial. Externalize session state to Redis, user preferences to a database, and in-flight workflow state to a durable queue. The hardest part is identifying what you're accidentally keeping in-memory (e.g., local caches, instance-level counters that skew metrics).

- **Circuit breaker, bulkhead, retry with jitter** — A circuit breaker wraps calls to a downstream service and "trips" after a configured failure threshold, returning a fallback immediately instead of waiting for timeouts — this prevents one slow dependency from exhausting all threads. A bulkhead isolates resources per downstream service (separate thread pools), so a flood of failures to service A doesn't prevent calls to service B. Retry with exponential backoff + jitter avoids thundering-herd retries: all clients backing off to the same second will spike the service again.

  ```java
  // Resilience4j circuit breaker configuration
  CircuitBreakerConfig config = CircuitBreakerConfig.custom()
      .failureRateThreshold(50)               // trip at 50% failure rate
      .waitDurationInOpenState(Duration.ofSeconds(30))
      .slidingWindowSize(10)
      .build();

  CircuitBreaker cb = CircuitBreakerRegistry.of(config)
                          .circuitBreaker("paymentService");

  // Retry with exponential backoff + jitter
  RetryConfig retryConfig = RetryConfig.custom()
      .maxAttempts(3)
      .intervalFunction(IntervalFunction.ofExponentialRandomBackoff(
          Duration.ofMillis(100), 2.0, Duration.ofSeconds(2)))
      .retryOnException(ex -> ex instanceof IOException)
      .build();
  ```

- **Load balancing: L4 vs L7** — L4 (transport layer) load balancers route based on IP and port — fast, minimal overhead, but cannot inspect HTTP content. L7 (application layer) balancers can route on URL path, headers, cookies, and perform SSL termination, enabling path-based routing and canary traffic splitting. Sticky sessions route a client to the same instance (usually via a cookie), which breaks horizontal scaling and failover — avoid them by externalizing session state.

- **Blue/green and canary deployments** — Blue/green maintains two identical environments; traffic is switched atomically from blue (current) to green (new), enabling instant rollback by flipping traffic back. Canary sends a small percentage of traffic (e.g., 5%) to the new version to validate it under real load before a full rollout. Use feature flags combined with canary to decouple deployment from feature release — deploy the code dark, enable the feature for internal users first.

- **Health checks: liveness vs readiness probes** — A liveness probe tells the orchestrator "is this process alive?" — if it fails, the container is restarted. A readiness probe tells the load balancer "is this instance ready to receive traffic?" — if it fails, the instance is removed from the pool without being restarted. Return `503` from the readiness endpoint during warm-up, while the circuit breaker is open, or during graceful shutdown to prevent traffic being routed to an impaired instance.

  ```java
  // Spring Boot Actuator — customizing readiness probe
  @Component
  public class DependencyReadinessIndicator implements HealthIndicator {
      @Override
      public Health health() {
          if (cacheWarmedUp && dbConnectionPool.isHealthy())
              return Health.up().build();
          return Health.down()
              .withDetail("reason", "cache not yet warmed up")
              .build();
      }
  }
  // Exposed at /actuator/health/readiness
  ```

---

### Data Layer Patterns

- **CQRS (Command Query Responsibility Segregation)** — CQRS separates the write model (commands that mutate state) from the read model (queries that return data). The read model can be a denormalized projection optimized for specific query patterns, updated asynchronously from the write side. This is powerful at scale because reads vastly outnumber writes, and you can scale them independently — but it introduces eventual consistency between the write and read stores that callers must tolerate.

- **Event sourcing vs traditional CRUD** — Traditional CRUD stores only current state; event sourcing stores the full sequence of events that produced the current state. The current state is derived by replaying events. This gives you a complete audit log for free, the ability to rewind and replay to fix bugs, and temporal queries ("what was the state at time T?"). The tradeoff is complexity: query patterns are harder, and schema evolution of event types requires care. Use it for domains where audit trails and temporal history matter (financial transactions, healthcare records).

- **Database sharding strategies** — Sharding horizontally partitions data across multiple database nodes. Range sharding assigns ranges of the key space to each shard — simple but can create hot shards if data is skewed. Hash sharding distributes keys uniformly using a hash function — better distribution but range queries require hitting all shards. Directory sharding uses a lookup table to map keys to shards — flexible but the lookup table becomes a bottleneck. All sharding strategies make cross-shard joins and transactions very hard.

- **Read replicas and eventual consistency** — A read replica asynchronously replicates from the primary; reads served from a replica may be milliseconds to seconds behind. This is fine for displaying a user's order history but unacceptable for reading back a resource you just created (read-your-writes consistency). After a write, either read from the primary for the immediately subsequent request, route the user to a sticky replica, or include a "read after write" consistency token.

- **Optimistic vs pessimistic locking** — Pessimistic locking acquires a database lock at read time (`SELECT FOR UPDATE`), preventing concurrent modification until the transaction completes — safe but reduces throughput. Optimistic locking reads a version number with the record and includes it in the `UPDATE WHERE version = :read_version`; if another transaction already incremented the version, the update affects 0 rows and you retry. Optimistic locking is better for low-contention scenarios (most cases); pessimistic for high-contention critical sections.

  ```sql
  -- Optimistic locking: update only if no concurrent modification
  UPDATE accounts
  SET balance = balance - 100, version = version + 1
  WHERE id = :id AND version = :readVersion;
  -- If rowsUpdated == 0, someone else modified it — retry
  ```

- **N+1 query problem** — N+1 occurs when you fetch N parent records and then issue one query per record to load a related child — the classic ORM trap. For N=1000 orders, you execute 1001 queries instead of 2. Fix it with eager loading (JOIN FETCH in JPQL), batch loading, or a secondary query fetching all children in a single `WHERE id IN (...)`. Always check `EXPLAIN` output and query counts when working with ORM-mapped associations.

  ```java
  // N+1: bad
  List<Order> orders = orderRepo.findAll(); // 1 query
  orders.forEach(o -> o.getItems().size()); // N queries — lazy load per order

  // Fixed with JOIN FETCH — 1 query with a JOIN
  @Query("SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o.userId = :uid")
  List<Order> findWithItems(@Param("uid") Long userId);
  ```

---

### Caching

- **Cache aside vs read-through vs write-through vs write-behind** — Cache aside (lazy loading): the application checks the cache, and on miss reads from the database and populates the cache. Simple but the first request after expiry is slow. Read-through: the cache populates itself on miss by calling the database — logic is in the cache layer, not the app. Write-through: every write goes to the cache and database synchronously, ensuring consistency at the cost of write latency. Write-behind (write-back): writes go to the cache immediately and are asynchronously flushed to the database — fast writes but risk data loss if the cache fails before flushing.

- **TTL strategy** — A TTL that is too short causes frequent cache misses, increasing database load — especially dangerous if many clients expire simultaneously (cache miss storm). A TTL that is too long serves stale data; for some domains (product catalog) this is acceptable, for others (inventory count) it is not. Add a small random jitter to TTLs to prevent synchronized expiry across many keys: instead of all keys expiring at the same second, they expire over a 30-second window, smoothing the miss rate.

  ```java
  // Add jitter: base TTL of 5 minutes ± 30 seconds
  long ttlSeconds = 300 + ThreadLocalRandom.current().nextInt(-30, 30);
  redis.setex(key, ttlSeconds, value);
  ```

- **Cache stampede / thundering herd** — When a popular cache key expires, many simultaneous requests all miss and all query the database at once. Solutions: (1) Probabilistic early expiration — recompute the cache slightly before it expires, with probability proportional to how close to expiry. (2) Locking — the first thread to miss acquires a short lock and populates the cache; others wait and then read the now-populated value. (3) Background refresh — a background job proactively refreshes keys before they expire.

  ```java
  // Locking pattern to prevent stampede
  String value = redis.get(key);
  if (value == null) {
      String lockKey = "lock:" + key;
      boolean acquired = redis.setnx(lockKey, "1") == 1;
      if (acquired) {
          redis.expire(lockKey, 5); // lock expires after 5s to prevent deadlock
          try {
              value = db.load(key);
              redis.setex(key, ttl, value);
          } finally {
              redis.del(lockKey);
          }
      } else {
          Thread.sleep(50); // wait and retry
          value = redis.get(key);
      }
  }
  ```

- **Redis data structures beyond strings** — Sorted sets (`ZADD`, `ZRANGE`) store members with a numeric score, making them ideal for leaderboards, priority queues, and rate limiting windows. Hashes (`HSET`, `HGETALL`) store field-value pairs in one key — more memory-efficient than one key per field. Pub/Sub enables real-time event broadcasting to subscribers. Streams (Redis 5+) are append-only logs with consumer groups, making Redis a lightweight alternative to Kafka for lower-throughput event streaming.

  ```
  # Leaderboard: add score, get top 10
  ZADD leaderboard 1500 "player:alice"
  ZADD leaderboard 2300 "player:bob"
  ZREVRANGE leaderboard 0 9 WITHSCORES   # top 10 by score descending

  # Sliding window rate limit: count requests in the last 60s
  ZADD rate:user123 <currentTimestamp> <requestId>
  ZREMRANGEBYSCORE rate:user123 0 <60SecondsAgo>
  count = ZCARD rate:user123
  ```

- **What NOT to cache** — Financial balances and totals must always reflect current state — stale data here causes incorrect charges or fraud. Authentication and authorization state (is this token revoked?) must be fresh — caching this can allow revoked sessions. Any data that must be read-your-writes consistent immediately after write should not be served from an async-replicated cache. When in doubt, ask "what's the worst case if this value is 30 seconds stale?" — if the answer is a regulatory violation or financial loss, don't cache it.

**Practice:** Design a URL shortener, rate limiter, or notification service at 1M users. Articulate tradeoffs at each decision point.

---

## 4. Messaging & Event-Driven Systems

The role explicitly calls out Kafka/Kinesis/SQS.

### Kafka Core Concepts

- **Topics, partitions, consumer groups, offsets** — A topic is a named, ordered, append-only log. It is split into partitions for parallelism — each partition is an ordered sequence, but there is no ordering guarantee across partitions. A consumer group is a set of consumers that collectively read a topic: Kafka assigns each partition to exactly one consumer in the group, giving you parallel consumption while ensuring each message is processed by one consumer. The offset is a sequential ID per partition; consumers commit offsets to record progress, so they can resume after a restart.

  ```
  Topic: "orders"  (3 partitions)

  Partition 0: [offset 0: order-A] [offset 1: order-D] ...
  Partition 1: [offset 0: order-B] [offset 2: order-E] ...
  Partition 2: [offset 0: order-C] [offset 1: order-F] ...

  Consumer Group "order-processors" (3 consumers):
    Consumer 1 → Partition 0
    Consumer 2 → Partition 1
    Consumer 3 → Partition 2
  ```

- **Producer acks: `0`, `1`, `all`** — `acks=0`: fire and forget — maximum throughput, zero durability guarantee; the broker may not even receive the message. `acks=1`: the leader broker acknowledges after writing to its own log — fast, but if the leader crashes before replication, data is lost. `acks=all` (with `min.insync.replicas=2`): the leader waits for acknowledgment from all in-sync replicas before confirming — strongest durability, slightly higher latency. For financial or audit data, always use `acks=all`.

- **At-most-once vs at-least-once vs exactly-once** — At-most-once: messages may be lost (commit offset before processing). At-least-once: messages may be delivered multiple times (commit offset after successful processing, so a crash before commit causes replay). Exactly-once: Kafka's transactional producer + idempotent consumer achieves this end-to-end, but it requires using the Kafka transactions API and has performance cost. In practice, design consumers to be idempotent and use at-least-once — it is simpler and sufficient.

- **Consumer lag and what it signals** — Consumer lag is the difference between the latest offset produced and the latest offset consumed. Increasing lag means consumption is slower than production — investigate whether consumers are CPU-bound, blocked on downstream I/O, or under-provisioned. Lag that grows unboundedly will eventually exhaust disk on the broker. Alert on lag exceeding a threshold (e.g., 10,000 messages or 5 minutes of data), not just on errors.

- **Compacted topics** — Log compaction retains only the most recent message per key within a topic, making it behave like a key-value store rather than a pure event log. Used for change data capture (CDC): downstream services can read the full compacted topic to bootstrap their state, then tail new messages to stay current. Also used for materializing a view of the current state of an entity (e.g., the latest user profile) without keeping a separate database.

- **Ordering guarantees** — Kafka guarantees ordering only within a partition. If you need all events for a given entity to be ordered (e.g., all state changes for order #123), use the entity ID as the partition key — all messages with the same key go to the same partition. If you need global ordering across all entities, you must use a single partition, which limits throughput to one consumer.

  ```java
  // Ensure all events for the same orderId go to the same partition
  ProducerRecord<String, OrderEvent> record = new ProducerRecord<>(
      "order-events",
      orderId.toString(), // partition key — same orderId always same partition
      event
  );
  producer.send(record);
  ```

---

### Messaging Patterns

- **Competing consumers vs pub/sub fanout** — Competing consumers: multiple consumers in the same group share the work — each message is processed by exactly one consumer. This is horizontal scaling of consumption. Pub/sub fanout: multiple consumer groups each subscribe to the same topic and each receive a copy of every message. Use competing consumers to scale processing throughput; use fanout when multiple independent services each need to react to the same event (e.g., an `order-placed` event triggers both an inventory service and an email service).

- **Dead-letter queues (DLQ)** — A DLQ is a separate topic or queue where messages that fail processing after N retries are written. Without a DLQ, a persistently failing message blocks the consumer or is silently dropped. A DLQ provides visibility into failures without blocking the happy path. Monitor DLQ depth as an alerting signal; create a replay mechanism so operators can reprocess DLQ messages after the root cause is fixed.

- **Idempotent consumers** — An idempotent consumer produces the same result regardless of how many times it processes the same message. Required with at-least-once delivery. Common techniques: store the message ID in a processed-IDs table and skip if already seen; design the business operation itself to be naturally idempotent (`UPDATE SET status = 'PAID' WHERE status != 'PAID'` is safe to run twice).

  ```java
  // Idempotency via a deduplication table
  @Transactional
  public void processPayment(PaymentEvent event) {
      if (processedEvents.existsById(event.getEventId())) {
          log.info("Duplicate event {}, skipping", event.getEventId());
          return; // idempotent skip
      }
      paymentService.apply(event);
      processedEvents.save(new ProcessedEvent(event.getEventId()));
  }
  ```

- **Saga pattern for distributed transactions** — A saga is a sequence of local transactions, each publishing an event or message to trigger the next step. If a step fails, compensating transactions are run in reverse to undo prior steps. Choreography: each service listens for events and decides what to do next — decoupled but harder to trace. Orchestration: a central coordinator service calls each participant in sequence — easier to trace and debug but introduces a central dependency.

- **SQS FIFO vs standard** — SQS Standard delivers messages at-least-once with best-effort ordering — throughput is effectively unlimited. SQS FIFO delivers messages exactly-once within a deduplication window and preserves order within a message group, but is limited to 3,000 messages/second (with batching). Use FIFO when order and deduplication are business requirements; use Standard when throughput is the priority and your consumer is idempotent.

**Be able to answer:** "An event is being processed twice and causing duplicate records — walk me through your investigation and fix." Check if the consumer is committing offsets before or after processing (at-least-once replay), inspect if a recent deployment caused consumer group rebalance and messages to replay, then implement idempotency at the consumer using the event ID as a deduplication key.

---

## 5. Cloud & Infrastructure (AWS focus)

### Compute & Networking

- **EC2 vs ECS vs EKS vs Lambda** — EC2 gives you raw VMs with full control but requires you to manage patching, scaling, and the OS. ECS manages Docker containers on EC2 or Fargate (serverless compute); simpler than Kubernetes for straightforward containerized services. EKS is managed Kubernetes — more powerful and portable but significantly more operational overhead. Lambda runs functions on-demand with no servers to manage, ideal for event-driven processing and sporadic workloads; unsuitable for long-running or latency-sensitive services due to cold start times.

- **VPC: subnets, security groups, NACLs, NAT gateways** — A VPC is a logically isolated virtual network. Public subnets have a route to an internet gateway (for load balancers); private subnets do not (for application and database tiers). Security groups are stateful firewalls at the instance level — if you allow inbound traffic, the return traffic is automatically allowed. NACLs are stateless firewalls at the subnet level — you must explicitly allow inbound and outbound. A NAT gateway lets instances in private subnets initiate outbound internet connections (for pulling packages, calling external APIs) without being directly reachable from the internet.

- **Load balancers: ALB vs NLB** — Application Load Balancer (ALB) operates at HTTP/HTTPS (layer 7) and can route based on URL paths (`/api/*` → service A), host headers, and HTTP method. It supports WebSockets, HTTP/2, and integrates with AWS WAF. Network Load Balancer (NLB) operates at TCP/UDP (layer 4), with dramatically lower latency (~100μs) and the ability to handle millions of requests per second — use it for non-HTTP protocols, gaming, or when ALB overhead is measurable.

- **Auto scaling: target tracking vs step scaling** — Target tracking is the simplest: pick a metric (e.g., "keep CPU at 60%") and AWS manages scale-out/in automatically. Step scaling responds to CloudWatch alarms with defined scaling actions ("if CPU > 80%, add 2 instances") — more control but more configuration. Ensure your scale-out is fast enough to handle traffic spikes by pre-warming or setting minimum capacity to handle baseline load without scaling.

---

### Storage & Data

- **S3: storage classes, lifecycle policies, presigned URLs** — S3 Standard for frequently accessed data; S3 Infrequent Access (IA) for data accessed monthly, at lower cost with a retrieval fee; S3 Glacier for archival with retrieval times from minutes to hours. Lifecycle policies automate transitions and deletion (e.g., move to IA after 30 days, delete after 365). Presigned URLs grant temporary time-limited access to a private object — use them to let clients download directly from S3 without routing through your service, avoiding egress bandwidth costs.

  ```java
  // Generate a presigned URL valid for 15 minutes
  S3Presigner presigner = S3Presigner.create();
  GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
      .signatureDuration(Duration.ofMinutes(15))
      .getObjectRequest(r -> r.bucket("my-bucket").key("reports/q4.pdf"))
      .build();
  URL url = presigner.presignGetObject(presignRequest).url();
  ```

- **RDS vs Aurora vs DynamoDB** — RDS is managed relational databases (Postgres, MySQL, etc.) with automated backups and patching but standard replication. Aurora is AWS's cloud-native relational engine: storage auto-scales, has 6-way replication across 3 AZs, and reads are 5x faster than standard MySQL — it's the default choice for relational workloads on AWS. DynamoDB is a fully managed NoSQL key-value and document store with single-digit millisecond latency at any scale; choose it when your access pattern is key-based lookups and you don't need relational queries.

- **ElastiCache: Redis vs Memcached** — Redis is the default choice: it persists data to disk, supports rich data structures, and has replication and clustering. Memcached is simpler (pure cache, no persistence, no replication) and multi-threaded which can slightly outperform Redis for simple get/set at extreme throughput. Use Redis unless you have a specific reason to use Memcached. Both should be in private subnets with security group access restricted to application tier only.

- **SQS, SNS, Kinesis** — SQS is a pull-based queue for decoupling services; consumers poll and delete messages. SNS is a push-based fan-out notification service; it pushes messages to subscribers (SQS queues, Lambda functions, HTTP endpoints, email). Kinesis Data Streams is a real-time data streaming service with configurable retention (up to 365 days) and replay capability, suited for high-throughput analytics pipelines; SQS messages are deleted on consumption and cannot be replayed.

---

### IAM & Security

- **Principle of least privilege** — Every principal (user, role, service) should have only the permissions required for its specific function and nothing more. Over-permissioned services create blast radius: if compromised, an attacker has whatever permissions that service has. Audit permissions regularly with IAM Access Analyzer to find unused permissions. In practice, start with a tight policy and expand as needed — it is much harder to remove permissions after the fact.

- **IAM roles for services** — EC2 instances, ECS tasks, and Lambda functions should assume IAM roles, not use long-lived access keys. Roles provide short-lived credentials rotated automatically by the AWS SDK. Cross-account access uses role assumption: account A's service assumes a role in account B, receiving temporary credentials scoped to that role. Never embed access keys in application code or container images — they will inevitably leak.

- **Secrets management** — AWS Secrets Manager stores credentials, API keys, and connection strings; it supports automatic rotation for supported databases (RDS, Redshift). Parameter Store is simpler and cheaper, suited for configuration values and non-sensitive parameters in addition to secrets (SecureString type). Fetch secrets at startup (or on-demand with caching) rather than baking them into environment variables, which are visible in process listings.

  ```java
  // Fetch a secret at runtime — never at build time
  SecretsManagerClient client = SecretsManagerClient.create();
  GetSecretValueRequest req = GetSecretValueRequest.builder()
      .secretId("prod/myapp/db-password")
      .build();
  String password = client.getSecretValue(req).secretString();
  ```

- **VPC endpoints** — A VPC endpoint routes traffic from your VPC to AWS services (S3, DynamoDB, SQS) over the AWS private network instead of the public internet. This eliminates the need for a NAT gateway for AWS service traffic, reduces data transfer costs, and improves security by removing the public internet path. Gateway endpoints (S3, DynamoDB) are free; Interface endpoints (most other services) incur a small hourly charge.

---

## 6. Observability

The role names Datadog, Splunk, Grafana specifically.

### The Three Pillars

- **Metrics: counters, gauges, histograms, percentiles** — A counter monotonically increases (total requests, total errors) — you derive rate from it. A gauge is a point-in-time snapshot (current heap usage, active connections). A histogram records the distribution of a value (request latency), bucketed so you can calculate percentiles. The p99 latency is the latency that 99% of requests are faster than — it captures the worst-case experience for real users, unlike average latency which hides long tails and can look fine while 1% of users suffer 10-second responses.

  ```java
  // Micrometer (used by Spring Boot) — instruments are auto-collected by Datadog/Prometheus
  @Autowired MeterRegistry registry;

  // Counter
  registry.counter("orders.created", "region", "us-east-1").increment();

  // Timer (records histogram + count + total)
  Timer timer = registry.timer("db.query.duration", "table", "orders");
  timer.record(() -> orderRepository.findAll());

  // Gauge for a live value
  registry.gauge("queue.depth", myQueue, Queue::size);
  ```

- **Logs: structured logging and correlation IDs** — Structured logs emit JSON (or key-value pairs) instead of plain strings, making them machine-parseable — you can filter by `userId`, `orderId`, or `errorCode` in Splunk/Datadog without regex. A correlation ID (also called trace ID) is a unique identifier generated at the entry point of a request and propagated through all downstream calls via headers and log context; it lets you reconstruct the full execution path of a single request across multiple services in a distributed system.

  ```java
  // SLF4J MDC (Mapped Diagnostic Context) for correlation ID propagation
  // In an HTTP filter:
  String correlationId = Optional.ofNullable(request.getHeader("X-Correlation-ID"))
      .orElse(UUID.randomUUID().toString());
  MDC.put("correlationId", correlationId);
  response.setHeader("X-Correlation-ID", correlationId);

  // In application code — correlationId appears in every log line automatically
  log.info("Processing order {}", orderId); // {"correlationId":"abc-123","message":"Processing order 42"}

  // Always clear MDC at end of request to prevent leaks in thread pools
  MDC.clear();
  ```

- **Traces: distributed tracing and OpenTelemetry** — A trace represents the end-to-end execution of a request as a tree of spans; each span records the start time, duration, and metadata of a unit of work (a service call, a DB query, a cache lookup). OpenTelemetry is the standard vendor-neutral API and SDK; it auto-instruments common frameworks (Spring, JDBC, gRPC) and exports to any backend (Datadog, Jaeger, Zipkin). The critical diagnostic workflow: see high p99 latency on a service → drill into a slow trace → find which span is slow (DB query? downstream API call?) → fix the root cause.

---

### Alerting Strategy

- **Alert on symptoms, not causes** — Symptom-based alerting means alerting on things users experience: high latency, elevated error rate, low throughput. Cause-based alerting (e.g., "CPU > 90%") fires frequently with no user impact and trains on-call engineers to ignore alerts. The four golden signals (Google SRE model) are: Latency, Traffic, Errors, and Saturation — these are the minimum set of metrics to alert on for any service.

- **SLIs, SLOs, SLAs** — A Service Level Indicator (SLI) is a quantitative measure of service quality (e.g., "the fraction of requests served under 200ms"). A Service Level Objective (SLO) is the target ("99.5% of requests under 200ms over a 30-day window"). An SLA is the contractual commitment to customers with financial penalties for breach. Set your internal SLO tighter than your SLA to give a buffer. Track error budget (100% - SLO attainment) — when the error budget is depleted, freeze feature work and focus on reliability.

- **Toil vs meaningful on-call work** — Toil is manual, repetitive, automatable on-call work with no lasting value: restarting a service, manually clearing a queue, re-running a failed job. Toil erodes morale and masks systemic problems. For every toil task, track it and plan to eliminate it: add a health check that restarts automatically, fix the root cause of the queue backup, build a retry mechanism. Meaningful on-call work is investigating novel incidents, writing runbooks, and improving detection.

- **Runbooks** — A runbook documents the steps to diagnose and mitigate a specific alert. A good runbook includes: what the alert means, the likely causes (in order of probability), diagnostic commands to run, mitigation steps, and escalation path. Runbooks should be written when the alert is created, not during an incident. Update them after every incident with new findings.

---

### What to Instrument

- **Every external call** — Each call to a database, cache, or downstream API should record latency (histogram), success/error counts, and a dimension for the specific operation. This lets you pinpoint which dependency is contributing to latency and allows per-dependency SLO tracking. Never aggregate all DB calls into one metric — tag by table or query type to identify slow queries.

- **Queue consumer lag** — Consumer lag is one of the most important operational metrics for event-driven services. It tells you whether processing is keeping up with production. Alert on lag exceeding a threshold that represents a meaningful delay for your use case — for a payment notification service, 1,000 messages of lag at 100 msg/s means a 10-second notification delay.

- **JVM metrics** — GC pause time and frequency indicate memory pressure. Heap utilization trending toward the max is a warning sign before OOM. Thread pool queue depth tells you whether the application is keeping up with requests — a growing queue means you need more threads or the work is taking longer. Monitor these alongside application metrics to correlate JVM behavior with latency spikes.

- **Business metrics** — Technical metrics tell you the system is working; business metrics tell you it's doing the right thing. Track orders per second alongside HTTP request rate — if HTTP traffic is up but orders are flat, something in the funnel is broken silently. Business metrics also demonstrate value to stakeholders in terms they understand.

**Practice:** Given a service with increased p99 latency, walk through your investigation from dashboard to root cause.

---

## 7. CI/CD & Containers

### Docker

- **Image layering and cache optimization** — A Docker image is a stack of read-only layers; each instruction in the Dockerfile creates a layer. When rebuilding, Docker reuses cached layers from the first changed instruction onward. Put rarely changing instructions (install dependencies) early and frequently changing ones (copy application code) last to maximize cache hits and speed up builds.

  ```dockerfile
  FROM eclipse-temurin:21-jre-alpine

  WORKDIR /app

  # Layer 1: dependencies — only rebuilt when pom.xml changes
  COPY pom.xml ./
  COPY .mvn/ .mvn/
  COPY mvnw ./
  RUN ./mvnw dependency:go-offline -q

  # Layer 2: source — rebuilt on every code change (but layer 1 is cached)
  COPY src/ src/
  RUN ./mvnw package -DskipTests -q

  EXPOSE 8080
  ENTRYPOINT ["java", "-jar", "target/app.jar"]
  ```

- **Multi-stage builds** — A multi-stage build uses multiple `FROM` instructions; earlier stages compile/build, the final stage copies only the artifacts needed to run. This keeps the final image small (no build tools, no source code, no caches) and reduces the attack surface.

  ```dockerfile
  # Stage 1: build
  FROM maven:3.9-eclipse-temurin-21 AS builder
  WORKDIR /build
  COPY . .
  RUN mvn package -DskipTests

  # Stage 2: runtime — only the JAR, minimal JRE
  FROM eclipse-temurin:21-jre-alpine AS runtime
  WORKDIR /app
  COPY --from=builder /build/target/app.jar app.jar
  # Final image has no Maven, no source, no build cache — ~200MB vs ~800MB
  ENTRYPOINT ["java", "-jar", "app.jar"]
  ```

- **Non-root users and security** — Containers run as root by default, which means a container escape gives the attacker root on the host. Create a dedicated non-root user in the Dockerfile and switch to it before the entry point. Use read-only filesystems where possible and mount writable volumes only for directories that need them (temp files, logs).

  ```dockerfile
  RUN addgroup -S appgroup && adduser -S appuser -G appgroup
  USER appuser   # all subsequent commands and the container process run as appuser
  ```

- **`ENTRYPOINT` vs `CMD`** — `ENTRYPOINT` defines the executable that runs when the container starts; it is not overridden by `docker run` arguments (unless you use `--entrypoint`). `CMD` provides default arguments to the `ENTRYPOINT` and IS overridden by `docker run` arguments. Use `ENTRYPOINT ["java", "-jar", "app.jar"]` for the main process and `CMD []` for default arguments — this allows operators to pass JVM flags at runtime (`docker run myimage -Xmx512m`).

---

### Jenkins / CI Pipelines

- **Pipeline as code (Jenkinsfile)** — Defining the pipeline in a `Jenkinsfile` checked into source control means the pipeline evolves with the code, is code-reviewed, and is reproducible. Declarative pipelines are simpler and more constrained; scripted pipelines offer full Groovy flexibility. Use declarative unless you need dynamic stage generation or complex conditional logic.

  ```groovy
  // Jenkinsfile (Declarative)
  pipeline {
      agent { docker { image 'maven:3.9-eclipse-temurin-21' } }
      stages {
          stage('Build')  { steps { sh './mvnw clean package -DskipTests' } }
          stage('Test')   { steps { sh './mvnw test' }
                            post { always { junit 'target/surefire-reports/**/*.xml' } } }
          stage('Publish'){ steps { sh './mvnw deploy -DskipTests' } }
          stage('Deploy') {
              when { branch 'main' }
              steps { sh './deploy.sh prod' }
          }
      }
      post {
          failure { slackSend channel: '#alerts', message: "Build failed: ${env.BUILD_URL}" }
      }
  }
  ```

- **Stages: build → test → package → publish → deploy** — Each stage should fail fast and provide clear feedback. Run unit tests before integration tests (faster feedback). Publish the artifact to a registry (Maven repository, Docker registry) with an immutable, traceable version tag. Deploy to lower environments (dev, staging) automatically; require a manual approval gate before production.

- **Artifact versioning** — Use semantic versioning or commit-based tags (Git SHA, timestamp) — never overwrite a published artifact. An immutable artifact version means you can always reproduce exactly what is running in production. Tag Docker images with both the Git SHA (for traceability) and a semantic version/latest alias (for convenience).

  ```bash
  GIT_SHA=$(git rev-parse --short HEAD)
  docker build -t myregistry/myapp:${GIT_SHA} -t myregistry/myapp:latest .
  docker push myregistry/myapp:${GIT_SHA}
  docker push myregistry/myapp:latest
  ```

- **Secrets in pipelines** — Never put passwords, API keys, or tokens in the Jenkinsfile or any file in source control. Use Jenkins Credentials (stored encrypted in Jenkins) or integrate with a secrets manager (AWS Secrets Manager, HashiCorp Vault). Inject secrets as environment variables that are masked in logs.

  ```groovy
  withCredentials([string(credentialsId: 'prod-db-password', variable: 'DB_PASS')]) {
      sh './deploy.sh'   // DB_PASS is available but masked in Jenkins logs as ****
  }
  ```

---

### Kubernetes Basics

- **Pod, Deployment, Service, Ingress** — A Pod is the smallest deployable unit: one or more containers sharing a network namespace and storage. A Deployment manages a desired number of Pod replicas, handling rolling updates and rollbacks. A Service provides a stable DNS name and IP that load-balances across Pods (which have ephemeral IPs). An Ingress exposes HTTP routes from outside the cluster to Services, handling TLS termination and path-based routing.

- **ConfigMaps vs Secrets** — ConfigMaps hold non-sensitive configuration (feature flags, environment-specific URLs) as key-value pairs or files. Secrets hold sensitive data (passwords, tokens) and are base64-encoded at rest (not encrypted by default — enable encryption at rest in the cluster). Inject both as environment variables or mounted files; prefer files for large configs. Never put Secrets in source control even if base64-encoded.

- **Resource requests vs limits** — A request is what the scheduler uses to decide which node can run the Pod; a limit is the maximum the container can consume. If a container exceeds its memory limit, it is OOMKilled; if it exceeds its CPU limit, it is throttled (slowed, not killed). Set requests equal to the normal operating level and limits at the burst ceiling. Missing resource requests means the scheduler makes poor placement decisions; missing limits means a runaway process can starve other pods on the node.

  ```yaml
  resources:
    requests:
      memory: "256Mi"
      cpu: "250m"       # 0.25 CPU cores — guaranteed
    limits:
      memory: "512Mi"
      cpu: "1000m"      # 1 CPU core — ceiling
  ```

- **Readiness/liveness probes** — The liveness probe determines if the container should be restarted (e.g., HTTP GET `/actuator/health/liveness`). The readiness probe determines if the container should receive traffic (e.g., `/actuator/health/readiness`). During a rolling deployment, new pods must pass readiness before old pods are terminated, ensuring zero-downtime. Set `initialDelaySeconds` to account for JVM warm-up time — otherwise probes fail during startup and the pod is killed before it finishes starting.

  ```yaml
  livenessProbe:
    httpGet: { path: /actuator/health/liveness, port: 8080 }
    initialDelaySeconds: 30
    periodSeconds: 10
    failureThreshold: 3
  readinessProbe:
    httpGet: { path: /actuator/health/readiness, port: 8080 }
    initialDelaySeconds: 10
    periodSeconds: 5
    failureThreshold: 3
  ```

---

## 8. CS Fundamentals

### Data Structures — Know the tradeoffs, not just definitions

| Structure | Insert | Lookup | Delete | Notes |
|-----------|--------|--------|--------|-------|
| HashMap | O(1) avg | O(1) avg | O(1) avg | Degrades to O(n) with hash collisions; unordered |
| TreeMap | O(log n) | O(log n) | O(log n) | Red-Black tree; ordered by key; use for range queries |
| LinkedList | O(1) head/tail | O(n) | O(1) with iterator | Use as deque/queue, not for random access |
| ArrayDeque | O(1) amortized | O(n) | O(1) head/tail | Preferred over LinkedList for queue/stack use cases |
| PriorityQueue | O(log n) | O(1) min | O(log n) | Binary min-heap; use for scheduling, Dijkstra's |
| HashSet | O(1) avg | O(1) avg | O(1) avg | HashMap under the hood, no values |
| TreeSet | O(log n) | O(log n) | O(log n) | Ordered set; use `ceiling()`, `floor()` for range ops |

- **HashMap internals** — HashMap stores entries in an array of buckets, indexed by `hashCode() % bucketCount`. Collisions in the same bucket are stored as a linked list (or a red-black tree when the bucket exceeds 8 entries, since Java 8). Load factor (default 0.75) triggers a resize when 75% of buckets are occupied, doubling the array and rehashing — an expensive O(n) operation. Pre-size a HashMap when you know the expected number of entries to avoid resizing: `new HashMap<>(expectedSize / 0.75 + 1)`.

- **TreeMap vs HashMap** — Use `TreeMap` when you need keys in sorted order or range operations: `subMap(from, to)`, `headMap(to)`, `tailMap(from)`, `floorKey(k)`, `ceilingKey(k)`. Every operation is O(log n) due to the Red-Black tree. Use `HashMap` when you just need fast key lookup and don't care about order.

  ```java
  TreeMap<Integer, String> scores = new TreeMap<>();
  scores.put(85, "Alice"); scores.put(92, "Bob"); scores.put(78, "Carol");

  // Efficient range query — "who scored between 80 and 90?"
  SortedMap<Integer, String> range = scores.subMap(80, true, 90, true);
  // {85=Alice}

  // Next higher/lower key
  Integer nextAbove90 = scores.higherKey(90); // 92
  ```

### Algorithms to Know Cold

- **Binary search and variants** — Binary search finds a target in a sorted array in O(log n) by halving the search space each iteration. The variants matter more in practice: finding the *first* occurrence of a value (left bound), finding the *last* occurrence (right bound), or finding the insertion point. Off-by-one errors in the loop invariant are the most common bug — be precise about whether bounds are inclusive or exclusive.

  ```java
  // Find leftmost (first) index of target
  int leftBound(int[] nums, int target) {
      int lo = 0, hi = nums.length;         // hi is exclusive
      while (lo < hi) {
          int mid = lo + (hi - lo) / 2;     // avoids overflow vs (lo+hi)/2
          if (nums[mid] < target) lo = mid + 1;
          else hi = mid;                    // not mid-1 — we want the leftmost
      }
      return lo; // lo == hi at termination
  }
  ```

- **BFS vs DFS** — BFS uses a queue and explores level by level — guarantees shortest path in an unweighted graph. DFS uses a stack (or recursion) and explores as deep as possible before backtracking — useful for topological sort, cycle detection, and exhaustive search. Use BFS for "fewest hops" problems; use DFS for reachability, component enumeration, and tree traversals.

  ```java
  // BFS — shortest path in unweighted graph
  int bfsDistance(Map<Integer, List<Integer>> graph, int start, int end) {
      Queue<Integer> queue = new ArrayDeque<>();
      Set<Integer> visited = new HashSet<>();
      queue.offer(start); visited.add(start);
      int distance = 0;
      while (!queue.isEmpty()) {
          for (int size = queue.size(); size-- > 0; ) {
              int node = queue.poll();
              if (node == end) return distance;
              for (int neighbor : graph.getOrDefault(node, List.of()))
                  if (visited.add(neighbor)) queue.offer(neighbor);
          }
          distance++;
      }
      return -1; // unreachable
  }
  ```

- **Sliding window, two pointers** — Sliding window maintains a window of elements and expands/contracts it to satisfy a constraint — O(n) for problems like "longest substring without repeating characters" or "minimum window substring." Two pointers uses a left and right pointer converging toward each other — O(n) for "two sum on sorted array" or "container with most water." Both patterns reduce O(n²) brute-force solutions to O(n).

  ```java
  // Sliding window: longest substring without repeating characters
  int lengthOfLongestSubstring(String s) {
      Map<Character, Integer> lastSeen = new HashMap<>();
      int maxLen = 0, left = 0;
      for (int right = 0; right < s.length(); right++) {
          char c = s.charAt(right);
          if (lastSeen.containsKey(c))
              left = Math.max(left, lastSeen.get(c) + 1); // shrink window
          lastSeen.put(c, right);
          maxLen = Math.max(maxLen, right - left + 1);
      }
      return maxLen;
  }
  ```

- **Sorting algorithms** — Merge sort is stable (equal elements maintain their relative order) and O(n log n) worst-case — prefer it when stability matters or for linked lists. Quicksort is O(n log n) average, O(n²) worst-case (avoided with random pivot), and in-place — Java's `Arrays.sort()` for primitives uses a dual-pivot quicksort. Heapsort is O(n log n) worst-case and in-place, but has poor cache locality — rarely used directly. Java's `Collections.sort()` uses Timsort (merge sort variant) which is stable and adaptive (O(n) for already-sorted input).

- **Dijkstra's for shortest path** — Dijkstra's finds the shortest path in a weighted graph with non-negative edge weights. It uses a min-heap (priority queue) to always expand the lowest-cost unvisited node — O((V + E) log V). It does not work with negative edge weights (use Bellman-Ford for that). Common interview application: "find the cheapest flight with at most K stops" or network routing.

  ```java
  Map<Integer, Integer> dijkstra(Map<Integer, List<int[]>> graph, int src) {
      Map<Integer, Integer> dist = new HashMap<>();
      PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
      pq.offer(new int[]{src, 0});
      while (!pq.isEmpty()) {
          int[] curr = pq.poll();
          int node = curr[0], cost = curr[1];
          if (dist.containsKey(node)) continue; // already settled
          dist.put(node, cost);
          for (int[] edge : graph.getOrDefault(node, List.of())) {
              if (!dist.containsKey(edge[0]))
                  pq.offer(new int[]{edge[0], cost + edge[1]});
          }
      }
      return dist;
  }
  ```

---

### Databases

- **B-tree indexes** — A B-tree index stores keys in a balanced tree where every leaf is at the same depth; each node holds multiple keys sorted in order. This makes range queries efficient: `WHERE created_at BETWEEN '2024-01-01' AND '2024-12-31'` walks a contiguous range of leaf nodes. Point lookups are O(log n). Hash indexes are faster for equality lookups but cannot serve range queries. Most relational databases default to B-tree indexes.

- **Index selectivity and composite index column ordering** — Selectivity is the ratio of distinct values to total rows; a highly selective index (e.g., `user_id`) filters out many rows, making it fast. For composite indexes `(a, b, c)`, the database can use the index for queries filtering on `a`, `a+b`, or `a+b+c`, but NOT `b` alone (the leftmost prefix rule). Put the highest-selectivity or most-queried column first. If your queries filter by `status` (low cardinality) and `user_id` (high cardinality), the composite should be `(user_id, status)`.

  ```sql
  -- This composite index supports all three queries below efficiently
  CREATE INDEX idx_orders_user_status ON orders (user_id, status, created_at);

  SELECT * FROM orders WHERE user_id = 42;                           -- uses index
  SELECT * FROM orders WHERE user_id = 42 AND status = 'PENDING';    -- uses index
  SELECT * FROM orders WHERE user_id = 42 ORDER BY created_at;       -- uses index + avoids sort
  SELECT * FROM orders WHERE status = 'PENDING';                     -- does NOT use index efficiently
  ```

- **`EXPLAIN` / `EXPLAIN ANALYZE`** — `EXPLAIN` shows the query execution plan the database chose: which indexes it uses, join algorithms, estimated row counts. `EXPLAIN ANALYZE` runs the query and shows actual row counts and timing — use it to spot estimates that are wildly off (causing bad plan choices) and to find sequential scans on large tables where an index scan should be used. Learn to read key nodes: `Seq Scan` (full table scan), `Index Scan`, `Nested Loop` vs `Hash Join`.

- **Transactions: ACID and isolation levels** — Atomicity: all operations in a transaction commit or all roll back. Consistency: a transaction moves the database from one valid state to another. Isolation: concurrent transactions don't interfere with each other. Durability: committed transactions survive crashes. Isolation levels and their anomalies: Read Uncommitted (dirty reads), Read Committed (non-repeatable reads possible — default in Postgres), Repeatable Read (phantom reads possible), Serializable (fully isolated, highest cost). Most applications work correctly at Read Committed; use Serializable for financial operations.

- **When NoSQL fits** — Use NoSQL when: your access pattern is almost exclusively key-based lookups (DynamoDB shines here), you need to store arbitrary schema-less documents (MongoDB), or you need extreme write throughput with eventual consistency. Relational databases are the right default for anything with complex queries, joins, or strong consistency requirements. The common mistake is choosing NoSQL for "scale" before understanding whether the relational DB is actually the bottleneck.

---

## 9. Design Patterns

The existing `Q25_DesignPatterns.java` covers basics. Understand these at a deeper level for backend systems:

- **Factory / Abstract Factory** — A Factory method encapsulates the construction logic for a family of objects behind an interface, letting callers create objects without knowing the concrete type. This is how JDBC works: `DriverManager.getConnection()` returns a `Connection` without the caller knowing if it's a Postgres or MySQL connection. Abstract Factory groups related factories — useful when building a feature that needs multiple coordinated objects (e.g., a cloud provider abstraction that creates matching storage, queue, and compute clients).

  ```java
  // Factory pattern for message producers — caller doesn't know concrete type
  interface MessageProducer { void send(String topic, String message); }

  class MessageProducerFactory {
      static MessageProducer create(String type, Config config) {
          return switch (type) {
              case "kafka"  -> new KafkaProducer(config);
              case "sqs"    -> new SqsProducer(config);
              case "memory" -> new InMemoryProducer(); // for tests
              default -> throw new IllegalArgumentException("Unknown type: " + type);
          };
      }
  }
  ```

- **Builder** — The builder pattern constructs a complex object step-by-step, making it clear what each parameter means (named parameters) and allowing optional fields without telescoping constructors. Lombok's `@Builder` generates this automatically. Use it for objects with more than 3-4 constructor parameters, especially when some are optional.

  ```java
  @Builder
  public class HttpRequest {
      private final String method;
      private final URI uri;
      @Builder.Default private final Duration timeout = Duration.ofSeconds(30);
      @Builder.Default private final Map<String, String> headers = new HashMap<>();
      private final String body;
  }

  // At call site — self-documenting, no positional ambiguity
  HttpRequest request = HttpRequest.builder()
      .method("POST")
      .uri(URI.create("https://api.example.com/orders"))
      .timeout(Duration.ofSeconds(5))
      .body(payload)
      .build();
  ```

- **Singleton** — Ensures a class has exactly one instance, providing a global access point. The classic use case is a connection pool or configuration holder. In a Spring application, all `@Component` and `@Bean` objects are singletons by default — the container manages it for you. For raw Java, use an enum singleton (thread-safe, serialization-safe, simplest) or double-checked locking with a `volatile` field.

  ```java
  // Enum singleton — simplest, safest Java singleton
  public enum Config {
      INSTANCE;
      private final Properties props = loadProperties();
      public String get(String key) { return props.getProperty(key); }
  }

  // Usage
  String dbUrl = Config.INSTANCE.get("db.url");
  ```

- **Strategy** — Defines a family of interchangeable algorithms behind a common interface, letting you swap them at runtime. This is how you avoid large `switch` statements on type codes. In backend systems: pricing strategies, shipping rate calculators, authentication providers, sorting algorithms for search results.

  ```java
  interface PricingStrategy { BigDecimal calculate(Order order); }

  class StandardPricing  implements PricingStrategy { /* ... */ }
  class MemberPricing    implements PricingStrategy { /* 10% discount */ }
  class FlashSalePricing implements PricingStrategy { /* 30% discount */ }

  class OrderService {
      private final PricingStrategy pricing; // injected — swap without changing OrderService
      BigDecimal quote(Order order) { return pricing.calculate(order); }
  }
  ```

- **Decorator** — Wraps an object to add behavior without changing its interface or subclassing. This is how `BufferedReader` wraps `FileReader`, how Spring's `@Transactional` adds transaction management to any service method, and how you'd add retry, logging, or metrics to an HTTP client. Decorators stack: `retry(logging(metrics(httpClient)))`.

  ```java
  interface OrderRepository {
      Optional<Order> findById(Long id);
  }

  // Decorator adds caching without changing OrderRepository interface
  class CachingOrderRepository implements OrderRepository {
      private final OrderRepository delegate;
      private final Cache<Long, Order> cache;

      public Optional<Order> findById(Long id) {
          return Optional.ofNullable(cache.get(id))
              .or(() -> {
                  Optional<Order> result = delegate.findById(id);
                  result.ifPresent(o -> cache.put(id, o));
                  return result;
              });
      }
  }
  ```

- **Observer / Event Bus** — Defines a one-to-many dependency: when an object changes state, all dependents are notified. An event bus (like Spring's `ApplicationEventPublisher` or Guava's `EventBus`) decouples the publisher from its subscribers — the publisher doesn't know who is listening. This is the in-process equivalent of Kafka: use it for domain events that multiple parts of the application need to react to without tight coupling.

  ```java
  // Spring ApplicationEvent — decoupled domain event publishing
  public record OrderPlacedEvent(Long orderId, String userId) {}

  @Service
  class OrderService {
      @Autowired ApplicationEventPublisher publisher;
      public Order place(OrderRequest req) {
          Order order = save(req);
          publisher.publishEvent(new OrderPlacedEvent(order.getId(), req.getUserId()));
          return order;
      }
  }

  @Component
  class EmailNotificationListener {
      @EventListener
      void onOrderPlaced(OrderPlacedEvent event) {
          emailService.sendConfirmation(event.userId(), event.orderId());
      }
  }
  ```

- **Circuit Breaker** — Wraps a remote call and tracks failures. After a threshold is exceeded, it "opens" and returns a fallback immediately without attempting the call, giving the downstream system time to recover. After a timeout, it enters "half-open" and allows a probe request through; if it succeeds, the circuit closes. This prevents cascading failures where one slow service causes thread exhaustion in all callers. Use Resilience4j in Java.

  ```java
  @CircuitBreaker(name = "inventoryService", fallbackMethod = "inventoryFallback")
  public InventoryStatus checkInventory(String sku) {
      return inventoryClient.getStatus(sku); // may be slow or down
  }

  public InventoryStatus inventoryFallback(String sku, Exception ex) {
      log.warn("Inventory service unavailable for {}, returning UNKNOWN", sku);
      return InventoryStatus.UNKNOWN; // graceful degradation
  }
  ```

- **Repository** — Abstracts the data access layer behind a domain-oriented interface, hiding whether the implementation talks to a relational database, NoSQL store, or an external API. This means business logic tests can use an in-memory repository, and you can swap storage implementations without touching the domain. Spring Data's `JpaRepository` and `CrudRepository` are pre-built implementations of this pattern.

  ```java
  // Domain-oriented interface — no SQL, no JDBC, no JPA annotations
  public interface OrderRepository {
      Order save(Order order);
      Optional<Order> findById(Long id);
      List<Order> findPendingOrdersOlderThan(Duration age);
  }

  // Production: JPA implementation
  @Repository
  class JpaOrderRepository implements OrderRepository { /* ... */ }

  // Test: in-memory implementation — fast, no DB needed
  class InMemoryOrderRepository implements OrderRepository {
      private final Map<Long, Order> store = new ConcurrentHashMap<>();
      /* ... */
  }
  ```

---

## 10. Leadership & Communication (Soft Skills for Senior Level)

This role explicitly includes leading architecture discussions, writing RCAs, mentoring, and tech talks.

### Architecture Decision Records (ADRs)

ADRs create a written record of significant technical decisions so future team members understand *why* the system is built the way it is, not just *how*. Without them, institutional knowledge lives only in people's heads and gets lost when engineers move on. Practice writing proposals with this structure:

1. **Context** — What problem are we solving and why now? What forces (business deadline, technical constraint, scaling need) are at play? What happens if we do nothing?
2. **Options considered** — At least 2-3 concrete alternatives with honest tradeoffs. "We only considered one option" is a red flag that the decision wasn't fully thought through.
3. **Decision** — What was chosen and why, explicitly linking back to the context. The decision should be defensible even if you later learn it was wrong.
4. **Consequences** — What gets better, what gets harder, what new problems does this decision introduce? What are the known risks?

---

### Root Cause Analysis (RCA / Post-Mortem)

An RCA documents what happened during an incident, why it happened, and what prevents recurrence. Blameless framing is essential: the goal is to understand system failure, not to assign fault to individuals — people do their best with the information they have. Use the 5-Why technique: ask "why?" repeatedly until you reach the systemic root cause, not just the proximate cause. A well-written RCA includes:

- **Timeline** — Chronological sequence of events with timestamps: when the issue started, when it was detected, when alerts fired, when on-call was paged, key diagnostic steps, when mitigation was applied, when service recovered.
- **Contributing factors** — Most incidents have multiple contributing causes, not one root cause. List each factor and how it contributed. Avoid single-factor explanations — they lead to incomplete fixes.
- **Action items** — Each with a specific owner, a due date, and a definition of done. Categorize as: detection improvements, prevention, mitigation speed, or process. Without action items, RCAs are just post-mortems with no mortuary.
- **What would have prevented this** — Both in terms of detection (would better alerting have caught this sooner?) and prevention (would a design change have made this failure mode impossible?).

---

### Mentoring

Effective mentoring at the senior level is less about teaching syntax and more about developing engineering judgment. Be ready to discuss:

- **How you've helped a junior engineer grow** — Specific examples: assigning increasingly complex tasks with appropriate scaffolding, pairing on hard debugging sessions so they learn the diagnostic process, reviewing their design proposals with questions rather than answers to build reasoning skills.
- **How you give code review feedback constructively** — Distinguish between blocking issues (correctness, security, performance) and suggestions (style, preference). Frame suggestions as questions ("have you considered...?") rather than directives ("change this to..."). Explain the *why* behind feedback so the engineer learns the principle, not just the fix. Acknowledge good decisions explicitly.
- **How you handle technical disagreement** — Listen to understand, not to respond. State your position with evidence and reasoning. Be willing to change your mind when presented with a better argument. If consensus isn't reached, escalate with context, not just competing opinions. Document the decision and the reasoning regardless of who "wins."

---

## Study Priority Order

1. **Java concurrency + CompletableFuture** — Asked in almost every senior backend interview; this differentiates senior from mid-level candidates.
2. **System design** — Will be its own 45-60 min round at most companies; practice narrating tradeoffs out loud.
3. **Kafka / messaging semantics** — Explicitly called out in qualifications; know delivery guarantees and idempotency cold.
4. **Observability story** — "How do you know your system is healthy?" is a common opener for the systems thinking discussion.
5. **REST API design** — Usually asked as a warm-up or embedded in system design; know idempotency and pagination well.
6. **Cloud (AWS)** — Breadth over depth unless the role is infrastructure-heavy; know the key services and when to use each.
7. **CS algorithms** — Likely a LeetCode medium, not hard, at senior level; practice narrating your thought process.

---

## Existing Code in This Repo to Review

| File | Relevance |
|------|-----------|
| `Q16_Multithreading.java` | Threading basics |
| `Q17_Synchronization.java` | `synchronized` keyword |
| `Q18_VolatileKeyword.java` | Memory visibility |
| `Q19_LambdaExpressions.java` | Functional style |
| `Q20_Streams.java` | Stream API |
| `Q24_Immutability.java` | Immutable design |
| `Q25_DesignPatterns.java` | Patterns overview |
| `Q30_FunctionalInterfaces.java` | Functional interfaces |
| `javaversions/Java24Features.java` | Modern Java features |
| `dependencyinjection/` | Spring DI / IoC |
