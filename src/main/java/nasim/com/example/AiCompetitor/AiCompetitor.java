package nasim.com.example.AiCompetitor;

public class AiCompetitor {

    public static int guessNumber(String recentAiHistory) {

        String prompt = """
        You are playing a number guessing game.
        The secret number is between 1 and 500.

        You are the AI player.
        You can see the public game history, including both the human player's guesses and your own guesses.

        Game rules:
        - "too low" means the secret number is greater than that guess.
        - "too high" means the secret number is smaller than that guess.
        - Use all public history to make your next guess.
        - Do not choose a number that conflicts with the public history.
        - Return only one integer between 1 and 500.
        - Do not explain.

        Public game history:
        %s

        AI next guess:
        """.formatted(recentAiHistory.isBlank() ? "No previous guesses." : recentAiHistory);
        AiPromptAPI aiPromptAPI = new AiPromptAPI(prompt);
        String aiResponse = aiPromptAPI.callPrompt();

        return extractNumberOnly(aiResponse);
    }

    private static int extractNumberOnly(String aiResponse) {
        try {
            return Integer.parseInt(aiResponse.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}