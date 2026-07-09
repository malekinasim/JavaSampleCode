package nasim.com.example;

import nasim.com.example.AiCompetitor.AiCompetitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomNumber {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("""
                    Hello welcome to Guess Game.do you want to play alone or with AiCompetitor?!!!
                    enter 1 For Alone and 2 for play with Ai.""");
            int gameType = scanner.nextInt();

            boolean gameOver = false;

            int userAttempts = 0;
            int aiAttempts = 0;
            Random random = new Random();
            int secretNumber = random.nextInt(500) + 1;
            List<String> aiHistory = new ArrayList<>();

            System.out.println("I have chosen a number between 1 and 500.");
            System.out.println("Try to guess it!");

            while (!gameOver) {
                int userGuess =0;
                do{
                    System.out.print("Enter your Guess (between 1 and 500) : ");
                     userGuess = scanner.nextInt();
                }while (userGuess<0 ||  userGuess>500);

                userAttempts++;
                if (userGuess == secretNumber) {
                    System.out.println("you guessed correctly.");
                    System.out.println("you wins!");
                    System.out.println("Your attempts: " + userAttempts);
                    if (gameType == 2)
                        System.out.println("AI attempts: " + aiAttempts);
                    gameOver = true;
                    continue;
                } else if (userGuess < secretNumber) {
                    System.out.println("Too low. Try a bigger number.");
                    aiHistory.add("Human Guessed: " + userGuess + "  too low");
                } else {
                    System.out.println("Too high. Try a smaller number.");
                    aiHistory.add("Human Guessed: " + userGuess + "  too high");
                }
                if (gameType == 2) {
                    String lastThreeGuesses = getGuesses(aiHistory);

                    int aiGuess = AiCompetitor.guessNumber(lastThreeGuesses);
                    aiAttempts++;

                    System.out.println("AI guesses: " + aiGuess);

                    if (aiGuess == secretNumber) {
                        System.out.println("AI guessed correctly.");
                        System.out.println("AI wins!");
                        System.out.println("Your attempts: " + userAttempts);
                        System.out.println("AI attempts: " + aiAttempts);
                        gameOver = true;
                    } else if (aiGuess < secretNumber) {
                        System.out.println("AI guess was too low.");
                        aiHistory.add("AI Guessed: " + aiGuess + "  too low");
                    } else {
                        System.out.println("AI guess was too high.");
                        aiHistory.add("AI Guessed: " + aiGuess + "  too high");
                    }
                }
            }

        }
    }

    private static String getGuesses(List<String> history) {

        StringBuilder result = new StringBuilder();
        for (String s : history) {
            result.append(s).append("\n");
        }
        if (result.isEmpty()) {
            return "No previous guesses.";
        }
        return result.toString();
    }
}