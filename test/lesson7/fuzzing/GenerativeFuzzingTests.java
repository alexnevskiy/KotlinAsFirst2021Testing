package lesson7.fuzzing;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.generator.Size;
import edu.berkeley.cs.jqf.examples.common.ArbitraryLengthStringGenerator;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static lesson7.task1.FilesKt.countSubstrings;

@RunWith(JQF.class)
public class GenerativeFuzzingTests {

    private final String INPUT_FILENAME = "input/temp.txt";

    /**
     * Средняя (14 баллов)
     *
     * Во входном файле с именем inputName содержится некоторый текст.
     * На вход подаётся список строк substrings.
     * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
     * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
     *
     */
    @Fuzz
    public void testCountSubstringsOneSymbol(@From(ArbitraryLengthStringGenerator.class) @Size(min = 1, max = 1) String symbol,
                                             @InRange(min = "0", max = "1000") int count) {
        try (FileWriter writer = new FileWriter(INPUT_FILENAME, false)) {
            for (int i = 0; i < count; i++) {
                writer.append(symbol);
            }
            writer.flush();

            Assertions.assertEquals(
                    Map.of(symbol, count),
                    countSubstrings(INPUT_FILENAME, List.of(symbol))
            );
        } catch (IOException exception) {
            System.out.println("Cannot be opened.");
        } finally {
            deleteFile();
        }
    }

    @Fuzz
    public void testCountSubstringsEmptyList(@From(ArbitraryLengthStringGenerator.class) @Size(min = 1, max = 100) String randomString) {
        try (FileWriter writer = new FileWriter(INPUT_FILENAME, false)) {
            writer.write(randomString);
            writer.flush();

            Assertions.assertEquals(
                    Map.of(),
                    countSubstrings(INPUT_FILENAME, List.of())
            );
        } catch (IOException exception) {
            System.out.println("Cannot be opened.");
        } finally {
            deleteFile();
        }
    }

    @Fuzz
    public void testCountSubstringsMissingSubstrings(
            @InRange(minByte = 48, maxByte = 78) byte @Size(min=32, max=128)[] textBytes,
            @InRange(minByte = 79, maxByte = 126) byte @Size(min=32, max=128)[] patternBytes) {
        try (FileWriter writer = new FileWriter(INPUT_FILENAME, false)) {
            String text = new String(textBytes);
            Map<String, Integer> map = Map.of(new String(patternBytes),0);
            List<String> substrings = List.of(new String(patternBytes));
            writer.write(text);
            writer.flush();

            Assertions.assertEquals(
                    map,
                    countSubstrings(INPUT_FILENAME, substrings)
            );
        } catch (IOException exception) {
            System.out.println("Cannot be opened.");
        } finally {
            deleteFile();
        }
    }

    private void deleteFile() {
        File fileToDelete = new File(INPUT_FILENAME);
        boolean success = fileToDelete.delete();

        Assertions.assertTrue(success);
    }
}
