package lesson6.fuzzing;

import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.generator.Size;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.apache.maven.shared.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static lesson6.task1.ParseKt.fromRoman;
import static lesson6.task1.ParseKt.plusMinus;

@RunWith(JQF.class)
public class GenerativeFuzzingTests {

    /**
     * Сложная (6 баллов)
     *
     * В строке представлено выражение вида "2 + 31 - 40 + 13",
     * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
     * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
     * Вернуть значение выражения (6 для примера).
     * Про на
     * рушении формата входной строки бросить исключение IllegalArgumentException
     */
    @Fuzz
    public void testPlusMinusCorrectExpression(int first, int second, int third, int fourth) {
        int result = Math.abs(first) + second + third + fourth;
        String expression = String.valueOf(Math.abs(first));
        expression += second > 0 ? " + " + Math.abs(second) : " - " + Math.abs(second);
        expression += third > 0 ? " + " + Math.abs(third) : " - " + Math.abs(third);
        expression += fourth > 0 ? " + " + Math.abs(fourth) : " - " + Math.abs(fourth);

        Assertions.assertEquals(result, plusMinus(expression));
    }

    @Fuzz
    public void testPlusMinusNegativeNumbers(@InRange(max = "-1") int first, @InRange(max = "-1") int second,
                                             @InRange(max = "-1") int third, @InRange(max = "-1") int fourth) {
        String expression = first + " + " + second + " + " + third + " + " + fourth;

        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus(expression));
    }

    @Fuzz
    public void testPlusMinusFirstNegative(@InRange(max = "-1") int first, @InRange(min = "0") int second,
                                           @InRange(min = "0") int third, @InRange(min = "0") int fourth) {
        String expression = first + " + " + second + " + " + third + " + " + fourth;

        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus(expression));
    }

    @Fuzz
    public void testPlusMinusPositiveNumbers(@InRange(min = "0") int first, @InRange(min = "0") int second,
                                             @InRange(min = "0") int third, @InRange(min = "0") int fourth) {
        int result = first + second + third + fourth;
        String expression = first + " + " + second + " + " + third + " + " + fourth;

        Assertions.assertEquals(result, plusMinus(expression));
    }

    @Fuzz
    public void testPlusMinusOneNegativeNumber(@InRange(max = "-1") int number) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus(String.valueOf(number)));
    }

    @Fuzz
    public void testPlusMinusEmptyOrIncorrectExpression(@InRange(minByte = 0, maxByte = 47) byte @Size(max=10)[] textBytes) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus(new String(textBytes)));
    }

    @Fuzz
    public void testPlusMinusDuplicatedSpaces(int first, int second, int third, int fourth,
                                              @InRange(min = "2", max = "10") int spaceCounter) {
        String expression = first + StringUtils.repeat(" ", spaceCounter) + "+" +
                StringUtils.repeat(" ", spaceCounter) + second + StringUtils.repeat(" ", spaceCounter) + "+" +
                StringUtils.repeat(" ", spaceCounter) + third + StringUtils.repeat(" ", spaceCounter) + "+" +
                StringUtils.repeat(" ", spaceCounter) + fourth;

        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus(expression));
    }

    @Fuzz
    public void testPlusMinusWithoutSpaces(int first, int second, int third, int fourth,
                                           @InRange(min = "43", max = "45") byte @Size(min = 1, max=1)[] signBytes) {
        String sign = new String(signBytes);
        String expression = first + sign + second + sign + third + sign + fourth;

        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus(expression));
    }

    @Fuzz
    public void testPlusMinusFirstSymbolIsSign(int first, int second, int third, int fourth,
                                               boolean isPlus) {
        String sign = isPlus ? " + " : " - ";
        String expression = sign + first + sign + second + sign + third + sign + fourth;

        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus(expression));
    }

    @Fuzz
    public void testPlusMinusWithoutSigns(int first, int second, int third, int fourth,
                                          @InRange(min = "1", max = "10") int spaceCounter) {
        String expression = first + StringUtils.repeat(" ", spaceCounter) +
                second + StringUtils.repeat(" ", spaceCounter) +
                third + StringUtils.repeat(" ", spaceCounter) + fourth;

        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus(expression));
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
    @Fuzz
    public void testFromRomanCorrectRoman(@InRange(min = "1", max = "3999") int number) {
        String roman = RomanArabicConverter.arabicToRoman(number);
        Assertions.assertEquals(number,fromRoman(roman));
    }

    @Fuzz
    public void testFromRomanRandomString(byte @Size(max=10)[] textBytes) {
        List<String> romanNumbers = new ArrayList<>(3999);
        for (int i = 1; i <= 3999; i++) {
            romanNumbers.add(RomanArabicConverter.arabicToRoman(i));
        }
        String randomString = new String(textBytes);
        if (romanNumbers.contains(randomString)) {
            int number = romanNumbers.indexOf(randomString) + 1;
            Assertions.assertEquals(number,fromRoman(randomString));
        } else {
            Assertions.assertEquals(-1,fromRoman(randomString));
        }
    }
}
