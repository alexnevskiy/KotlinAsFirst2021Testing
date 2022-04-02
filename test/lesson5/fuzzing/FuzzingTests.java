package lesson5.fuzzing;

import com.pholser.junit.quickcheck.Property;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.util.Map;

import static lesson5.task1.MapKt.containsIn;
import static lesson5.task1.MapKt.mergePhoneBooks;

@RunWith(JQF.class)
public class FuzzingTests {

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
    @Property(trials = 1000)
    public void testContainsIn(Map<String, String> a, Map<String, String> b) {
        containsIn(a, b);
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
    @Property(trials = 1000)
    public void testMergePhoneBooks(Map<String, String> a, Map<String, String> b) {
        mergePhoneBooks(a, b);
    }
}
