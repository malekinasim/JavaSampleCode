package nasim.com.example.bmiCalculator;

import nasim.com.example.bmiCalculator.aiNutritionAdvisor.AiNutritionAdvisor;

import java.util.Scanner;

public class BmiCalculator {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to BMI Calculator with AI Nutrition Advice");
            double weightKg =0;
            do {
                System.out.print("Enter your weight in kg (Weight must be greater than zero): ");
                 weightKg = scanner.nextDouble();
            }while(weightKg <= 0);

            double heightCm =0;
            do {
                System.out.print("Enter your height in cm (height must be greater than zero): ");
                heightCm = scanner.nextDouble();
            }while(heightCm <= 0);


            int age = 0;
            do {
                System.out.println("how old are you (Age must be between 1 and 120):");
                age = scanner.nextInt();
            } while (age <= 0 || age > 120);


            double bmi = calculate(weightKg, heightCm);
            String category = getCategory(bmi);

            System.out.printf("Your BMI is: %.2f%n", bmi);
            System.out.println("BMI category: " + category);

            System.out.println();
            System.out.println("Getting AI food advice...");

           String advices= AiNutritionAdvisor.getAdvice(weightKg,heightCm,age,bmi,category);
            System.out.println();
            System.out.println("AI Nutrition Advice:");
            System.out.println(advices);
        }


    }

    public static double calculate(double weightKg, double heightCm) {
        double heightMeter = heightCm / 100;
        return weightKg / (heightMeter * heightMeter);
    }

    public static String getCategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal weight";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }
}