package lesson7.task1;

import junit.framework.AssertionFailedError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static lesson7.task1.FilesKt.countSubstrings;


public class TestsForSoftwareTesting {
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
    @Test
    void testCountSubstringsPreparedFile() {
        Assertions.assertEquals(
                Map.of(),
                countSubstrings("input/empty.txt", List.of())
        );
        Assertions.assertEquals(
                Map.of("Котлетки", 0, "макарошки", 0, "пюрешка", 0),
                countSubstrings("input/substrings_in1.txt", List.of("Котлетки", "макарошки", "пюрешка"))
        );
        Assertions.assertEquals(
                Map.of("ЖиВоТнОе", 2, "-", 6, "длинно", 2),
                countSubstrings("input/substrings_in2.txt", List.of("ЖиВоТнОе", "-", "длинно"))
        );
    }

    @Test
    void testCountSubstringsGeneratedFile() {
        List<Integer> duplicateWordsCounter = createFileWithDuplicateWords();
        try {
            Assertions.assertEquals(
                    Map.of("kek", duplicateWordsCounter.get(0),
                            "e", duplicateWordsCounter.get(1),
                            "k", duplicateWordsCounter.get(2)),
                    countSubstrings(INPUT_FILENAME, List.of("kek", "e", "k"))
            );
        } catch (AssertionFailedError e) {
            e.getMessage();
        } finally {
            deleteFile();
        }

        // dropped tests
        createFileWithSubstringsOnDifferentLines();
        List<Integer> DifferentLinesNumbers = createFileWithSubstringsOnDifferentLines();
        try {
            Assertions.assertEquals(
                    Map.of("kek", DifferentLinesNumbers.get(0),
                            "kek\nkek", DifferentLinesNumbers.get(1)),
                    countSubstrings(INPUT_FILENAME, List.of("kek", "kek\nkek"))
            );
        } catch (AssertionFailedError e) {
            e.getMessage();
        } finally {
            deleteFile();
        }
    }

    private List<Integer> createFileWithDuplicateWords() {
        Random random = new Random();
        int number = random.nextInt(100) + 1;
        try (FileWriter writer = new FileWriter(INPUT_FILENAME, false)) {
            writer.write("kek");
            for (int i = 0; i < number; i++) {
                writer.append("ek");
            }

            writer.flush();
        } catch (IOException exception) {
            System.out.println("Cannot be opened.");
        }
        return List.of(number + 1, number + 1, number + 2);
    }

    private List<Integer> createFileWithSubstringsOnDifferentLines() {
        Random random = new Random();
        int number = random.nextInt(10) + 1;
        try (FileWriter writer = new FileWriter(INPUT_FILENAME, false)) {
            writer.write("kek");
            for (int i = 0; i < number; i++) {
                writer.append("\nkek");
            }

            writer.flush();
        } catch (IOException exception) {
            System.out.println("Cannot be opened.");
        }
        return List.of(number + 1, number);
    }

    private void deleteFile() {
        File fileToDelete = new File(INPUT_FILENAME);
        boolean success = fileToDelete.delete();

        Assertions.assertTrue(success);
    }
}
