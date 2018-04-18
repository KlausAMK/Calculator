package Calculator;

public class Math {

    public double calclulate(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                System.out.println("lakjasd");
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
