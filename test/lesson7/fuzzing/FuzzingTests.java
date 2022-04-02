package lesson7.fuzzing;

import com.pholser.junit.quickcheck.Property;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static lesson7.task1.FilesKt.countSubstrings;

@RunWith(JQF.class)
public class FuzzingTests {
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
    @Property(trials = 1000)
    public void testCountSubstrings(String randomString, List<String> substrings) {
        try (FileWriter writer = new FileWriter(INPUT_FILENAME, false)) {
            writer.write(randomString);
            writer.flush();

            countSubstrings(INPUT_FILENAME, substrings);
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
