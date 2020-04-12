package com.example.calculator;

import org.jetbrains.annotations.NotNull;

/**
 * Implementation may use different approaches according to the required precision
 * The operands are Strings in order to maintain the precision
 */
public interface Calculator {
    /**
     *
     * @param a String representation of first operand
     * @param b String representation of second operand
     * @return The String representation of the sum of the two params
     */
    String sum(@NotNull String a, @NotNull String b);
    /**
     *
     * @param a String representation of first operand
     * @param b String representation of second operand
     * @return The String representation of the subtraction of the two params
     */
    String subtraction(@NotNull String a, @NotNull String b);
    /**
     *
     * @param a String representation of first operand
     * @param b String representation of second operand
     * @return The String representation of the multiplication of the two params
     */
    String multiplication(@NotNull String a, @NotNull String b);
    /**
     *
     * @param a String representation of first operand
     * @param b String representation of second operand
     * @return The String representation of the division of the two params
     */
    String division(@NotNull String a, @NotNull String b);
}
