package org.example;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NumberUtils {
    public static List<Integer> add(List<Integer> left, List<Integer> right) {
        // if any is null, return null
        if (left == null || right == null) return null;

        // reverse the numbers so that the least significant digit goes to the left.
        Collections.reverse(left);
        Collections.reverse(right);

        LinkedList<Integer> result = new LinkedList<>();

        // while there's a digit, keep summing them
        // if there's carry, take the carry into consideration
        int carry = 0;
        for (int i = 0; i < Math.max(left.size(), right.size()); i++) {
            int leftDigit = left.size() > i ? left.get(i) : 0;
            int rightDigit = right.size() > i ? right.get(i) : 0;

            if (leftDigit < 0 || leftDigit > 9 || rightDigit < 0 || rightDigit > 9) {
                throw new IllegalArgumentException();
            }

            int sum = leftDigit + rightDigit + carry;
            result.addFirst(sum % 10);
            carry = sum / 10;
        }

        // if there's some leftover carry, add it to the final number
        if (carry > 0) result.addFirst(carry);

        // remove leading zeroes from the result
        while (result.size() > 1 && result.get(0) == 0) {
            result.remove(0);
        }

        return result;
    }
}