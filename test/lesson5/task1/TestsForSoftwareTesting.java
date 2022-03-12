package lesson5.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static lesson5.task1.MapKt.containsIn;
import static lesson5.task1.MapKt.mergePhoneBooks;

public class TestsForSoftwareTesting {

    /**
     * Простая (2 балла)
     *
     * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
     * это выполняется, если все ключи из a содержатся в b с такими же значениями.
     *
     * Например:
     *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
     *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
     */
    @Test
    void testContainsIn() {
        Assertions.assertTrue(containsIn(
                Map.of("a", "b"),
                Map.of("a", "b", "b", "c"))
        );
        Assertions.assertTrue(containsIn(
                Map.of(),
                Map.of("a", "b", "b", "c"))
        );
        Assertions.assertTrue(containsIn(
                Map.of(),
                Map.of())
        );
        Assertions.assertTrue(containsIn(
                Map.of("a", "b", "b", "c", "c", "d"),
                Map.of("c", "d", "a", "b", "b", "c"))
        );

        Assertions.assertFalse(containsIn(
                Map.of("a", "b"),
                Map.of("c", "d"))
        );
        Assertions.assertFalse(containsIn(
                Map.of("a", "b"),
                Map.of("a", "c", "b", "c"))
        );
        Assertions.assertFalse(containsIn(
                Map.of("a", "b"),
                Map.of())
        );
        Assertions.assertFalse(containsIn(
                Map.of("a", "b", "b", "c"),
                Map.of("a", "b", "d", "e", "c", "d"))
        );
    }

    /**
     * Средняя (3 балла)
     *
     * Объединить два ассоциативных массива `mapA` и `mapB` с парами
     * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
     * значения для повторяющихся ключей через запятую.
     * В случае повторяющихся *ключей* значение из mapA должно быть
     * перед значением из mapB.
     *
     * Повторяющиеся *значения* следует добавлять только один раз.
     *
     * Например:
     *   mergePhoneBooks(
     *     mapOf("Emergency" to "112", "Police" to "02"),
     *     mapOf("Emergency" to "911", "Police" to "02")
     *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
     */
    @Test
    void testMergePhoneBooks() {
        Assertions.assertEquals(
                Map.of("Sber", "900"),
                mergePhoneBooks(
                        Map.of("Sber", "900"),
                        Map.of("Sber", "900")
                )
        );
        Assertions.assertEquals(
                Map.of("Sber", "900", "Ambulance", "03"),
                mergePhoneBooks(
                        Map.of("Sber", "900", "Ambulance", "03"),
                        Map.of("Ambulance", "03")
                )
        );
        Assertions.assertEquals(
                Map.of("Police", "02", "Ambulance", "03, 13"),
                mergePhoneBooks(
                        Map.of("Police", "02", "Ambulance", "03"),
                        Map.of("Ambulance", "13", "Police", "02")
                )
        );
        Assertions.assertEquals(
                Map.of("Police", "02, 102", "Ambulance", "03, 13", "Emergency gas", "04"),
                mergePhoneBooks(
                        Map.of("Police", "02", "Ambulance", "03"),
                        Map.of("Ambulance", "13", "Police", "102", "Emergency gas", "04")
                )
        );
        Assertions.assertEquals(
                Map.of("What", "W, h", "Is", "i, s", "It", "i, t"),
                mergePhoneBooks(
                        Map.of("What", "W", "Is", "i", "It", "i"),
                        Map.of("It", "t", "What", "h", "Is", "s")
                )
        );
        Assertions.assertEquals(
                Map.of(),
                mergePhoneBooks(
                        Map.of(),
                        Map.of()
                )
        );
        Assertions.assertEquals(
                Map.of("Test", "test"),
                mergePhoneBooks(
                        Map.of(),
                        Map.of("Test", "test")
                )
        );
        Assertions.assertEquals(
                Map.of("A", "a, b, c"),
                mergePhoneBooks(
                        Map.of("A", "a, b"),
                        Map.of("A", "c")
                )
        );
    }
}
