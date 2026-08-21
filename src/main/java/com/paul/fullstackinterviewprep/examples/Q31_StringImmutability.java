package com.paul.fullstackinterviewprep.examples;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Q31: Why are Strings considered immutable in Java?
 *
 * String is immutable: once created, its internal character data can never
 * change. Every "mutating" method (concat, replace, toUpperCase, substring...)
 * returns a brand-new String instead of altering the original.
 *
 * Why Strings were designed this way:
 * 1. String pool / interning — literals can be safely shared across the JVM
 *    because no one can change a shared instance out from under another
 *    reference. This saves huge amounts of memory since Strings are so common.
 * 2. Security — Strings are used for class names, file paths, network hosts,
 *    DB connection URLs, reflection arguments. If a String were mutable, code
 *    could pass a validated value (e.g. a file path) and have it changed
 *    after the security check but before use (a TOCTOU-style exploit).
 * 3. Thread safety — an immutable object has no mutable state to
 *    synchronize, so Strings can be freely shared across threads with no
 *    locking.
 * 4. Safe hashcode caching — String caches its hashCode() the first time
 *    it's computed. That's only correct if the value can never change
 *    afterward, which makes String a fast, reliable HashMap/HashSet key.
 * 5. Class loading — class names are passed as Strings to the classloader;
 *    immutability guarantees the class actually loaded matches the name that
 *    was requested.
 */
public class Q31_StringImmutability {

  public static void main(String[] args) throws Exception {
    System.out.println("=== String Immutability Demo ===\n");

    demonstrateImmutabilityInPractice();
    demonstrateStringPool();
    demonstrateHashCodeCaching();
    demonstrateSecurityRationale();
  }

  static void demonstrateImmutabilityInPractice() {
    System.out.println("--- 'Mutating' methods return a new object ---");
    String original = "hello";
    String upper = original.toUpperCase();

    System.out.println("original = " + original);
    System.out.println("upper    = " + upper);
    System.out.println("original == upper reference? " + (original == upper));
    System.out.println("original identity unchanged - a new String was allocated for upper\n");
  }

  static void demonstrateStringPool() {
    System.out.println("--- String pool / interning ---");
    // Literals are interned automatically - both point at the same pooled instance
    String s1 = "java";
    String s2 = "java";
    System.out.println("s1 == s2 (both literals): " + (s1 == s2));

    // `new String(...)` forces a new heap object outside the pool
    String s3 = new String("java");
    System.out.println("s1 == s3 (new String()):  " + (s1 == s3));
    System.out.println("s1.equals(s3):             " + s1.equals(s3));

    // .intern() pulls it back into the pool
    System.out.println("s1 == s3.intern():        " + (s1 == s3.intern()));
    System.out.println("Sharing pooled instances is only safe because String is immutable\n");
  }

  static void demonstrateHashCodeCaching() throws Exception {
    System.out.println("--- Cached hashCode relies on immutability ---");
    String s = "immutable-key";
    int firstCall = s.hashCode();
    int secondCall = s.hashCode();
    System.out.println("hashCode() computed twice: " + firstCall + " == " + secondCall);

    // Reflection just to show the private `hash` field being reused, not recomputed
    Field hashField = String.class.getDeclaredField("hash");
    hashField.setAccessible(true);
    System.out.println("Cached internal hash field: " + hashField.get(s));
    System.out.println("Because Strings never change, the cached hash is always valid\n");
  }

  static void demonstrateSecurityRationale() {
    System.out.println("--- Security: Strings as trusted parameters ---");
    Map<String, String> permissions = new HashMap<>();
    permissions.put("/etc/app/config.yaml", "READ_ONLY");

    String requestedPath = "/etc/app/config.yaml";
    checkAndUse(requestedPath, permissions);
    System.out.println("If String were mutable, a caller could change requestedPath");
    System.out.println("after the permission check but before the file is opened -");
    System.out.println("immutability closes that gap entirely.");
  }

  static void checkAndUse(String path, Map<String, String> permissions) {
    String access = permissions.getOrDefault(path, "DENIED");
    System.out.println("Checked '" + path + "' -> " + access);
    System.out.println("Using the SAME String reference to open it: " + path + " (guaranteed unchanged)");
  }
}
