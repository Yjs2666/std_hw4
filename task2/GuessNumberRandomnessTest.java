import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class GuessNumberRandomnessTest {

    @Test
    public void testUniformDistribution() {
        int[] freq = new int[101];
        Arrays.fill(freq, 0);
        Random rnd = new Random(0);
        int N = 30000;

        PrintStream originalOut = System.out;
        InputStream originalIn = System.in;
        for (int i = 0; i < N; i++) {
            ByteArrayInputStream inContent = new ByteArrayInputStream("1\n2\n3\n4\n5\n".getBytes());
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setIn(inContent);
            System.setOut(new PrintStream(outContent));
            int result = GuessNumber.guessingNumberGame(rnd);
            if (result >= 1 && result <= 100) {
                freq[result]++;
            }
            else {
                fail();
            }
        }
        System.setIn(originalIn);
        System.setOut(originalOut);
        int minFreq = Integer.MAX_VALUE;
        int maxFreq = Integer.MIN_VALUE;

        for (int number = 1; number <= 100; number++) {
            minFreq = Math.min(minFreq, freq[number]);
            maxFreq = Math.max(maxFreq, freq[number]);
        }
        double ratio = (double) maxFreq / minFreq;
        assertTrue(ratio <= 1.5);
        System.out.printf("ratio = " + ratio);
    }
}

