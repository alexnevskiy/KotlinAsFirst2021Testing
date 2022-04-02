package lesson6.fuzzing;

import com.pholser.junit.quickcheck.Property;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import static lesson6.task1.ParseKt.fromRoman;
import static lesson6.task1.ParseKt.plusMinus;

@RunWith(JQF.class)
public class FuzzingTests {

    /**
     * Сложная (6 баллов)
     *
     * В строке представлено выражение вида "2 + 31 - 40 + 13",
     * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
     * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
     * Вернуть значение выражения (6 для примера).
     * Про нарушении формата входной строки бросить исключение IllegalArgumentException
     */
    @Property(trials = 10000)
    public void testPlusMinus(String expression) {
        try {
            plusMinus(expression);
        } catch (IllegalArgumentException e) {
            // Ignored
        }
    }

    /**
     * Сложная (6 баллов)
     *
     * Перевести число roman, заданное в римской системе счисления,
     * в десятичную систему и вернуть как результат.
     * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
     * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
     * Например: XXIII = 23, XLIV = 44, C = 100
     *
     * Вернуть -1, если roman не является корректным римским числом
     */
    @Property(trials = 10000)
    public void testFromRoman(String expression) {
        fromRoman(expression);
    }
}
