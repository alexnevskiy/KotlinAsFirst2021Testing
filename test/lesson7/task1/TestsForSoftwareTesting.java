package lesson7.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
        createFileWithDuplicateWords();
        Assertions.assertEquals(
                Map.of("kek", 101, "e", 101, "k", 102),
                countSubstrings(INPUT_FILENAME, List.of("kek", "e", "k"))
        );
        deleteFile();

        createFileWithSubstringsOnDifferentLines();
        Assertions.assertEquals(
                Map.of("kek", 4, "kek\nkek", 3),
                countSubstrings(INPUT_FILENAME, List.of("kek", "kek\nkek"))
        );
        deleteFile();
    }

    private void createFileWithDuplicateWords() {
        try (FileWriter writer = new FileWriter(INPUT_FILENAME, false)) {
            writer.write("kek");
            for (int i = 0; i < 100; i++) {
                writer.append("ek");
            }

            writer.flush();
        } catch (IOException exception) {
            System.out.println("Cannot be opened.");
        }
    }

    private void createFileWithSubstringsOnDifferentLines() {
        try (FileWriter writer = new FileWriter(INPUT_FILENAME, false)) {
            writer.write("kek");
            for (int i = 0; i < 3; i++) {
                writer.append("\nkek");
            }

            writer.flush();
        } catch (IOException exception) {
            System.out.println("Cannot be opened.");
        }
    }

    private void deleteFile() {
        File fileToDelete = new File(INPUT_FILENAME);
        boolean success = fileToDelete.delete();

        Assertions.assertTrue(success);
    }
}
