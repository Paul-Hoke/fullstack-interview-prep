package com.paul.fullstackinterviewprep.examples;

import java.util.List;

/**
 * Q33: Is Java pass-by-value or pass-by-reference?
 *
 * Java is ALWAYS pass-by-value. There is no pass-by-reference in Java.
 *
 * The confusion comes from object references: when you pass an object to a
 * method, Java copies the REFERENCE (the pointer/handle to the object), not
 * the object itself. So:
 * - You CAN mutate the object the reference points to (both the caller's
 *   and the method's reference point at the same object on the heap).
 * - You CANNOT make the caller's reference point at a different object by
 *   reassigning the parameter inside the method - that only rebinds the
 *   local copy of the reference.
 *
 * Mental model: think of an object reference like a sticky note with an
 * address on it. Java photocopies the sticky note for the method parameter.
 * Both notes lead to the same house (object), so redecorating the house
 * (mutating fields) is visible to both. But writing a different address on
 * your copy of the note doesn't change the address on the original note.
 */
public class Q33_PassByValueOrReference {

  public static void main(String[] args) {
    System.out.println("=== Pass-by-Value Demo ===\n");

    demonstratePrimitives();
    demonstrateMutatingObjectState();
    demonstrateReassigningReference();
    demonstrateStringGotcha();
  }

  static void demonstratePrimitives() {
    System.out.println("--- Primitives: value is copied, caller is untouched ---");
    int x = 10;
    System.out.println("Before: x = " + x);
    incrementPrimitive(x);
    System.out.println("After:  x = " + x + " (unchanged - method got its own copy)\n");
  }

  static void incrementPrimitive(int value) {
    value = value + 1; // only changes the local copy
  }

  static void demonstrateMutatingObjectState() {
    System.out.println("--- Objects: the reference is copied, but both copies point to the SAME object ---");
    StringBuilder sb = new StringBuilder("Hello");
    System.out.println("Before: sb = " + sb);
    appendToBuilder(sb);
    System.out.println("After:  sb = " + sb + " (mutated in place - visible to caller)\n");
  }

  static void appendToBuilder(StringBuilder builder) {
    builder.append(", World"); // mutates the shared object; doesn't reassign the caller's reference
  }

  static void demonstrateReassigningReference() {
    System.out.println("--- Reassigning a parameter never affects the caller's reference ---");
    List<String> list = List.of("original");
    System.out.println("Before: list = " + list);
    reassignReference(list);
    System.out.println("After:  list = " + list + " (still the original - only the LOCAL copy was reassigned)\n");
  }

  static void reassignReference(List<String> ref) {
    ref = List.of("replaced"); // rebinds the local copy of the reference only
    System.out.println("   Inside method after reassignment: ref = " + ref);
  }

  static void demonstrateStringGotcha() {
    System.out.println("--- Strings look 'passed by value' because they're immutable ---");
    String s = "original";
    System.out.println("Before: s = " + s);
    tryToModifyString(s);
    System.out.println("After:  s = " + s + " (String is immutable - any 'change' makes a NEW object,");
    System.out.println("        and that new reference is local to the method, same rule as above)");
  }

  static void tryToModifyString(String str) {
    str = str + " modified"; // concat creates a new String; reassigns the local copy only
  }
}
