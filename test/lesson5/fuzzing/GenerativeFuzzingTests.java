package lesson5.fuzzing;

import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.generator.Size;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static lesson5.task1.MapKt.containsIn;
import static lesson5.task1.MapKt.mergePhoneBooks;

@RunWith(JQF.class)
public class GenerativeFuzzingTests {

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
    @Fuzz
    public void testContainsInDifferentSizes(@Size(min = 10, max = 20) Map<String, String> a,
                                             @Size(min = 21, max = 30)Map<String, String> b) {
        Assumptions.assumeTrue(a.size() != b.size());
        Assertions.assertFalse(containsIn(a, b));
    }

    @Fuzz
    public void testContainsInSameMaps(Map<String, String> a) {
        Map<String, String> b = new HashMap<>(a);
        Assertions.assertTrue(containsIn(a, b));
    }

    @Fuzz
    public void testContainsInAContainedInB(Map<String, String> a, Map<String, String> b) {
        b.putAll(a);
        Assertions.assertTrue(containsIn(a, b));
    }

    @Fuzz
    public void testContainsInFirstMapEmpty(Map<String, String> b) {
        Map<String, String> a = Map.of();
        Assertions.assertTrue(containsIn(a, b));
    }

    @Fuzz
    public void testContainsInSecondMapEmpty(Map<String, String> a) {
        Map<String, String> b = Map.of();
        Assertions.assertFalse(containsIn(a, b));
    }

    @Fuzz
    public void testContainsInDifferentValues(String key,
                                              @InRange(minByte = 48, maxByte = 78) byte @Size(min=32, max=128)[] firstValue,
                                              @InRange(minByte = 79, maxByte = 126) byte @Size(min=32, max=128)[] secondValue) {
        Map<String, String> a = Map.of(key, new String(firstValue));
        Map<String, String> b = Map.of(key, new String(secondValue));
        Assertions.assertFalse(containsIn(a, b));
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
     *   ) ->
     *   mapOf("Emergency" to "112, 911", "Police" to "02")
     */
    @Fuzz
    public void testMergePhoneBooksFirstMapEmpty(Map<String, String> b) {
        Map<String, String> a = Map.of();
        Assertions.assertEquals(b,mergePhoneBooks(a, b));
    }

    @Fuzz
    public void testMergePhoneBooksSecondMapEmpty(Map<String, String> a) {
        Map<String, String> b = Map.of();
        Assertions.assertEquals(a,mergePhoneBooks(a, b));
    }

    @Fuzz
    public void testMergePhoneBooksCommonKey(String key, String firstValue, String secondValue) {
        Map<String, String> a = Map.of(key, firstValue);
        Map<String, String> b = Map.of(key, secondValue);
        Assertions.assertEquals(Map.of(key, firstValue + ", " + secondValue),mergePhoneBooks(a, b));
    }

    @Fuzz
    public void testMergePhoneBooksTwoKeysWithOneCommon(String firstKey, String secondKey,
                                                        String firstValue, String secondValue, String thirdValue) {
        Map<String, String> a = Map.of(firstKey, firstValue);
        Map<String, String> b = Map.of(secondKey, secondValue, firstKey, thirdValue);
        Assertions.assertEquals(Map.of(firstKey, firstValue + ", " + thirdValue,
                secondKey, secondValue),mergePhoneBooks(a, b));
    }

    @Fuzz
    public void testMergePhoneBooksTwoCommonKeys(String firstKey, String secondKey,
                                                 String firstValue, String secondValue, String thirdValue) {
        Map<String, String> a = Map.of(firstKey, firstValue, secondKey, secondValue);
        Map<String, String> b = Map.of(secondKey, secondValue, firstKey, thirdValue);
        Assertions.assertEquals(Map.of(firstKey, firstValue + ", " + thirdValue,
                secondKey, secondValue),mergePhoneBooks(a, b));
    }
}
