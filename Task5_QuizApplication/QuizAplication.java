import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class QuizApplication {

    public static void main(String[] args) {
        QuizApp quizApp = new QuizApp();
        quizApp.startQuiz();
    }
}

class Question {

    private String questionText;
    private String[] options;
    private int correctOptionIndex; // 0-based index

    public Question(String questionText, String[] options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public boolean isCorrect(int selectedOptionIndex) {
        return selectedOptionIndex == correctOptionIndex;
    }
}

class QuizApp {

    private List<Question> questions;
    private int score;
    private List<String> resultSummary;
    private static final int TIME_LIMIT_SECONDS = 10;

    public QuizApp() {
        questions = new ArrayList<>();
        score = 0;
        resultSummary = new ArrayList<>();
        loadQuestions();
    }

    private void loadQuestions() {
        questions.add(new Question(
                "What is the size of an int in Java?",
                new String[]{"8 bits", "16 bits", "32 bits", "64 bits"},
                2
        ));
        questions.add(new Question(
                "Which keyword is used to inherit a class in Java?",
                new String[]{"implements", "extends", "inherits", "super"},
                1
        ));
        questions.add(new Question(
                "Which of these is not a Java OOP concept?",
                new String[]{"Encapsulation", "Polymorphism", "Compilation", "Inheritance"},
                2
        ));
        questions.add(new Question(
                "What is the default value of a boolean variable in Java?",
                new String[]{"true", "false", "0", "null"},
                1
        ));
        questions.add(new Question(
                "Which method is the entry point of a Java program?",
                new String[]{"start()", "run()", "main()", "init()"},
                2
        ));
    }

    public void startQuiz() {
        System.out.println("=================================");
        System.out.println("       JAVA QUIZ APPLICATION");
        System.out.println("=================================");
        System.out.println("You have " + TIME_LIMIT_SECONDS + " seconds to answer each question.\n");

        int questionNumber = 1;

        for (Question question : questions) {
            System.out.println("Question " + questionNumber + ": " + question.getQuestionText());
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println("  " + (i + 1) + ". " + options[i]);
            }

            int userAnswer = getAnswerWithTimeout();

            if (userAnswer == -1) {
                System.out.println("Time's up! No answer recorded.\n");
                resultSummary.add("Question " + questionNumber + ": Not answered (Time out). Correct answer: "
                        + options[question.getCorrectOptionIndex()]);
            } else if (question.isCorrect(userAnswer - 1)) {
                System.out.println("Correct!\n");
                score++;
                resultSummary.add("Question " + questionNumber + ": Correct");
            } else {
                System.out.println("Wrong! Correct answer: " + options[question.getCorrectOptionIndex()] + "\n");
                resultSummary.add("Question " + questionNumber + ": Incorrect. Correct answer: "
                        + options[question.getCorrectOptionIndex()]);
            }

            questionNumber++;
        }

        showResults();
    }

    private int getAnswerWithTimeout() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your answer (1-4): ");
            return scanner.nextInt();
        });

        int answer;
        try {
            answer = future.get(TIME_LIMIT_SECONDS, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            answer = -1; // signals timeout or invalid input
        } finally {
            executor.shutdownNow();
        }

        return answer;
    }

    private void showResults() {
        System.out.println("=================================");
        System.out.println("           QUIZ RESULTS");
        System.out.println("=================================");
        System.out.println("Final Score: " + score + " / " + questions.size());
        System.out.println("\nSummary:");

        for (String summaryLine : resultSummary) {
            System.out.println("- " + summaryLine);
        }

        System.out.println("=================================");
    }
}
