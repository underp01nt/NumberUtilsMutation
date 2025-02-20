package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


// ERNESTO THAI
// SE 350
class NumberUtilsTest {
    /**
     *
     * Step 1: understand the requirement, input type and output type
     *        Requirement: Add two list of integer, index by index, and returns another list
     * Step 2 (raw):  Perform partition and boundary analysis on input and output
     *        Each input: left | right
     *        Combination of input:
     *        Output:
     *  Step 3: Derive potential test cases
     *
     */

    /*
             Empty left | Empty right
            !Empty left | Empty right
             Empty left | !Empty right
            !Empty left | !Empty right

            Individual inputs:
                Null list
                Empty list
                List of size 1
                List of size > 1
     */

    // null and empty cases
    /*
        Here is the list of cases that could be used for testing a combination of (non)empty's and nulls:
            Null left | Empty right --> null
            Empty left | Empty right --> [0]
            !Empty left | Empty right --> the !empty list
            Empty left | !Empty right --> the !empty list
            !Empty left | !Empty right --> !empty list + !empty list, e.g. [1, 2] + [3, 4] = [4, 6]

            I choose to test a combination with null input to check if the program can handle null inputs and output null accordingly.
            There is only one null input test here because if one input is null, the program should nevertheless return null.
            I also choose to work with empty lists to ensure that the program can properly address empty lists.

            The !empty and !empty combination is just for testing basic functionality of the program's method of addition.
     */

    public static Stream<Arguments> InputDataProvider() {
        return Stream.of(
                Arguments.of(null, new LinkedList<>(), null),
                Arguments.of(new LinkedList<>(List.of()), new LinkedList<>(), new LinkedList<>(List.of())),
                Arguments.of(new LinkedList<>(), new LinkedList<>(List.of(1, 0)), new LinkedList<>(List.of(1, 0))),
                Arguments.of(new LinkedList<>(List.of(1, 0)), new LinkedList<>(List.of(1, 0)), new LinkedList<>(List.of(2, 0)))
        );
    }

    @Test
    @Tag("created_from_coverage")
    void leftDigitNegative() {
        // test to check if left digit is detected to be negative
        try {
            List<Integer> a = new LinkedList<>(); a.add(-4); a.add(1);
            List<Integer> b = new LinkedList<>(); b.add(2); b.add(4);
            NumberUtils.add(a, b);
            fail("Negative number should have been detected");
        }
        catch (IllegalArgumentException e) {
            System.out.println("A negative number was successfully detected");
        }
    }

    @Test
    @Tag("created_from_coverage")
    void leftDigitOOB() {
        // test to check if left digit is detected to be out of bounds
        try {
            List<Integer> a = new LinkedList<>(); a.add(10); a.add(1);
            List<Integer> b = new LinkedList<>(); b.add(3); b.add(4);
            NumberUtils.add(a, b);
            fail("A number out of range should have been detected");
        }
        catch (IllegalArgumentException e) {
            System.out.println("A number out of range was sucessfully detected");
        }
    }

    @Test
    @Tag("created_from_coverage")
    void nullRight() {
        // test to check if null is returned when the right digit is null
        List<Integer> a = new LinkedList<>(); a.add(9);
        List<Integer> b = null;
        List<Integer> sol = null;
        assertEquals(sol, NumberUtils.add(a, b));
    }


    @ParameterizedTest
    @MethodSource("InputDataProvider")
    public void testAdd(List<Integer> left, List<Integer> right, List<Integer> out) {
        assertEquals(out, NumberUtils.add(left, right));
    }

    @Test
    void simpleTest() {
        // just single digit addition without carry-over
        List<Integer> a = new LinkedList<>(); a.add(3);
        List<Integer> b = new LinkedList<>(); b.add(5);
        List<Integer> sol = new LinkedList<>(); sol.add(8);
        assertEquals(sol, NumberUtils.add(a, b));
    }

    @Test
    void threeDigitDifferingAddition() {
        // test case for differing list sizes
        // checks if addition method can account for a non-specified leading zero in one list
        List<Integer> a = new LinkedList<>(); a.add(3); a.add(5); a.add(4);
        List<Integer> b = new LinkedList<>(); b.add(8); b.add(6);
        List<Integer> sol = new LinkedList<>(); sol.add(4); sol.add(4); sol.add(0);
        assertEquals(sol, NumberUtils.add(a, b));
    }

    @Test
    void veryLarge() {
        // test to check if the program can work with large inputs and give output in a reasonable time
        List<Integer> a = new LinkedList<>(); a.add(9); a.add(9); a.add(9); a.add(9); a.add(9); a.add(9);
        List<Integer> b = new LinkedList<>(); b.add(9); b.add(5); b.add(9); b.add(4); b.add(9); b.add(9);
        List<Integer> sol = new LinkedList<>(); sol.add(1); sol.add(9); sol.add(5); sol.add(9); sol.add(4);
        sol.add(9); sol.add(8);
        assertEquals(sol, NumberUtils.add(a, b));
    }

    @Test
    void carryOverSingle() {
        // tests carry-over with single digits
        List<Integer> a = new LinkedList<>(); a.add(9);
        List<Integer> b = new LinkedList<>(); b.add(9);
        List<Integer> sol = new LinkedList<>(); sol.add(1); sol.add(8);
        assertEquals(sol, NumberUtils.add(a, b));
    }

    @Test
    void carryOverMultiple() {
        // test case for test method's carry-over ability with multiple digits
        List<Integer> a = new LinkedList<>(); a.add(9); a.add(9);
        List<Integer> b = new LinkedList<>(); b.add(3); b.add(9);
        List<Integer> sol = new LinkedList<>(); sol.add(1); sol.add(3); sol.add(8);
        assertEquals(sol, NumberUtils.add(a, b));
    }

    @Test
    void carryOverMultipleFlipped() {
        // test case for test method's carry-over ability with multiple digits, regardless of input order
        List<Integer> a = new LinkedList<>(); a.add(9); a.add(9);
        List<Integer> b = new LinkedList<>(); b.add(3); b.add(9);
        List<Integer> sol = new LinkedList<>(); sol.add(1); sol.add(3); sol.add(8);
        assertEquals(sol, NumberUtils.add(b, a));
    }

    @Test
    void multipleAndSingle() {
        // tests addition of multiple digits with a single digit
        List<Integer> a = new LinkedList<>(); a.add(1); a.add(3); a.add(9);
        List<Integer> b = new LinkedList<>(); b.add(1);
        List<Integer> sol = new LinkedList<>(); sol.add(1); sol.add(4); sol.add(0);
        assertEquals(sol, NumberUtils.add(b, a));
    }

    @Test
    void leadingZero() {
        // test to check if the program can properly handle leading zeroes
        List<Integer> a = new LinkedList<>(); a.add(0); a.add(0); a.add(1);
        List<Integer> b = new LinkedList<>(); b.add(0); b.add(9);
        List<Integer> sol = new LinkedList<>(); sol.add(1); sol.add(0);
        assertEquals(sol, NumberUtils.add(b, a));
    }

    @Test
    void boundaryInput() {
        // test to check if the program accepts 0 and 9 as valid inputs
        List<Integer> a = new LinkedList<>(); a.add(0);
        List<Integer> b = new LinkedList<>(); a.add(0);
        List<Integer> sol = new LinkedList<>(); sol.add(0);
        assertEquals(sol, NumberUtils.add(b, a)); // [0] + [0] = [0]
    }
    @Test
    void negativeNumber() {
        // test to check if program can reject negative numbers, which are out of 0-9 range
        try {
            List<Integer> a = new LinkedList<>(); a.add(4); a.add(1);
            List<Integer> b = new LinkedList<>(); b.add(-9); b.add(4);
            NumberUtils.add(a, b);
            fail("Negative number should have been detected");
        }
        catch (IllegalArgumentException e) {
            System.out.println("A negative number was successfully detected");
        }
    }

    @Test
    void numberOutOfRange() {
        // test to check if program can detect invalid output (10 is not within 0-9)
        try {
            List<Integer> a = new LinkedList<>();
            a.add(4);
            a.add(1);
            List<Integer> b = new LinkedList<>();
            b.add(10);
            b.add(4);
            NumberUtils.add(a, b);
            fail("A number out of range should have been detected");
        } catch (IllegalArgumentException e) {
            System.out.println("A number out of range was successfully detected");
        }
    }

    /* method written for the assignment, parameterized test also performed this case
        @Test
        void twoEmpty() {
            List<Integer> a = new LinkedList<>();
            List<Integer> b = new LinkedList<>();

            List<Integer> sol = new LinkedList<>(); sol.add(0);
            assertEquals(sol, NumberUtils.add(a, b));
        }
     */
}