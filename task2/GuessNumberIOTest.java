import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class GuessNumberIOTest {

    @Test
    public void test1_GuessCorrect() {
        Random fixedRandom = new RandomStub(49);
        String userInput = "50\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(userInput.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));
        try {
            int actualAnswer = GuessNumber.guessingNumberGame(fixedRandom);
            assertEquals(50, actualAnswer);
            String consoleOutput = outContent.toString();
            assertTrue(consoleOutput.contains("Congratulations! You guessed the number.\n"));
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }

    @Test
    public void test2_WrongThenRight() {
        Random fixedRandom = new RandomStub(0);
        String userInput = "5\n1\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(userInput.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));
        try {
            int actualAnswer = GuessNumber.guessingNumberGame(fixedRandom);
            assertEquals(1, actualAnswer);
            String consoleOutput = outContent.toString();
            assertTrue(
                    consoleOutput.contains("The number is less than 5\n"));

            assertTrue(
                    consoleOutput.contains("Congratulations! You guessed the number.\n"));
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }

    @Test
    public void test3_FiveAttempts() {
        Random fixedRandom = new RandomStub(49);
        String userInput = "1\n2\n3\n4\n5\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(userInput.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));
        try {
            int actualAnswer = GuessNumber.guessingNumberGame(fixedRandom);
            assertEquals(50, actualAnswer);

            String consoleOutput = outContent.toString();
            assertTrue(
                    consoleOutput.contains("You have exhausted 5 trials.\n"));
            assertTrue(
                    consoleOutput.contains("The number was 50"));
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }

    // Error: java.util.NoSuchElementException
    // Possible Reason: You have to finish the test with a right or wrong answers. You cannot leave a unfinished answers.
    // I think the app should handle this properly. Even though this was not covered in the doc.
    // The app is expecting more input, but we ended it here, that is what caused the fault.
    @Test
    public void test4_GreaterThan() {
        Random fixedRandom = new RandomStub(49);
        String userInput = "5\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(userInput.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));
        try {
            int actualAnswer = GuessNumber.guessingNumberGame(fixedRandom);
            assertEquals(1, actualAnswer);
            String consoleOutput = outContent.toString();
            assertTrue(
                    consoleOutput.contains("The number is greater than 5\n"));
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }

    @Test
    public void test5_NonNumericInput() {
        Random fixedRandom = new RandomStub(49);
        String userInput = "abc\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(userInput.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));

        try {
            assertThrows(InputMismatchException.class, () -> {
                GuessNumber.guessingNumberGame(fixedRandom);
            });

        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }

    @Test
    public void test6_NegativeInput() {
        Random fixedRandom = new RandomStub(49);
        String userInput = "-5\n50\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(userInput.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));
        try {
            int answer = GuessNumber.guessingNumberGame(fixedRandom);
            assertEquals(50, answer);

            String consoleOutput = outContent.toString();
            assertTrue(consoleOutput.contains("The number is greater than -5\n"));
            assertTrue(consoleOutput.contains("Congratulations! You guessed the number.\n"));
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }

    static class RandomStub extends Random {
        private final int fixedNextInt;
        public RandomStub(int fixedNextInt) {
            this.fixedNextInt = fixedNextInt;
        }
        @Override
        public int nextInt(int bound) {
            return fixedNextInt;
        }
    }
}






