package nasim.com.example.bmiCalculator.aiNutritionAdvisor;

import nasim.com.example.ai.AiPromptAPI;

public class AiNutritionAdvisor {

    public static String getAdvice(double weightKg, double heightCm, int age, double bmi, String category) {
        String prompt = """
                You are a simple nutrition assistant.
                
                User information:
                - Age: %d years old
                - Weight: %.1f kg
                - Height: %.1f cm
                - BMI: %.2f
                - BMI category: %s
                
                Give simple, safe, general food advice based on the user's age, BMI category, weight, and height.
                
                Important rules:
                - Do not give a medical diagnosis.
                - Do not suggest extreme diets.
                - Do not suggest very low calorie diets.
                - Do not suggest supplements or medication.
                - Mention that the advice is general and not a replacement for a doctor or dietitian.
                - Use simple English.
                
                Give:
                1. One short explanation of the BMI result
                2. Three food suggestions
                3. One small daily habit
                4. One warning about when to ask a doctor or dietitian
                
                Keep the answer short and practical.
                """.formatted(age, weightKg, heightCm, bmi, category);
        AiPromptAPI aiPromptAPI = new AiPromptAPI(prompt);

        return aiPromptAPI.callPrompt();
    }

}
