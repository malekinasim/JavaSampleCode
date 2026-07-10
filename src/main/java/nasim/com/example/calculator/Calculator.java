package nasim.com.example.calculator;

import com.sun.tools.javac.Main;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Welcome to Expression Calculator");
            System.out.println("Supported operations:(, +, -, *, /, ^,)");
            System.out.println("Example: (2 + 3) * 4");
            System.out.println("Type 'q' to quit.");

            while (true) {
                System.out.print("Enter expression: ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("q")) {
                    System.out.println("Goodbye.");
                    break;
                }

                try {
                    double result = ExpressionCalculator.calculate(input);
                    System.out.println("Result: " + result);
                } catch (Exception e) {
                    System.out.println("Invalid expression: " + e.getMessage());
                }
            }
        }
    }
}


