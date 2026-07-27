import java.util.Random;
import java.util.Scanner;

public class NumberGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int totalScore = 0;
        boolean playAgain = true;
        System.out.println("   WELCOME TO THE NUMBER GUESSING GAME");
        

        while (playAgain) {
            int numberToGuess = random.nextInt(100) + 1; // random number 1 to 100
            int maxAttempts = 5;
            int attemptsUsed = 0;
            boolean guessedCorrectly = false;

            System.out.println("\nI'm thinking of a number between 1 and 100.");
            System.out.println("You have " + maxAttempts + " attempts to guess it.");

            while (attemptsUsed < maxAttempts && !guessedCorrectly) {
                System.out.print("\nEnter your guess: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid number.");
                    scanner.next(); // clear invalid input
                    continue;
                }

                int userGuess = scanner.nextInt();
                attemptsUsed++;

                if (userGuess == numberToGuess) {
                    guessedCorrectly = true;
                    int pointsEarned = (maxAttempts - attemptsUsed + 1) * 10;
                    totalScore += pointsEarned;
                    System.out.println(" Correct! You guessed it in " + attemptsUsed + " attempt(s).");
                    System.out.println("Points earned this round: " + pointsEarned);
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low! Try again.");
                    System.out.println("Attempts remaining: " + (maxAttempts - attemptsUsed));
                } else {
                    System.out.println("Too high! Try again.");
                    System.out.println("Attempts remaining: " + (maxAttempts - attemptsUsed));
                }
            }

            if (!guessedCorrectly) {
                System.out.println("\n Out of attempts! The correct number was: " + numberToGuess);
            }

            System.out.println("\nYour total score so far: " + totalScore);

            System.out.print("\nDo you want to play again? (yes/no): ");
            String response = scanner.next();

            if (!response.equalsIgnoreCase("yes")) {
                playAgain = false;
            }
        }

        System.out.println("Thanks for playing! Final Score: " + totalScore);
       

        scanner.close();
    }
}
