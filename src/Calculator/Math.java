package Calculator;

/**
 * Simple calculator class which can handle simple calculations
 *
 * @author Ryhmä3
 * *@version 1.0
 * @since 2018-04-19
 */

public class Math {

    /** calculate method calculates two given parameters with one operator
     * @param num1 first number
     * @param num2 second number
     * @param operator operator
     * @return result of calculation
     *
     */
    public double calclulate(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0)
                    return 0;
                return num1 / num2;
        }

        return 0;

    }
}
