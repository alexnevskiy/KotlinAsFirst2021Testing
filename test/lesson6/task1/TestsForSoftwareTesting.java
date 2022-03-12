package lesson6.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static lesson6.task1.ParseKt.fromRoman;
import static lesson6.task1.ParseKt.plusMinus;


public class TestsForSoftwareTesting {

    /**
     * Сложная (6 баллов)
     *
     * В строке представлено выражение вида "2 + 31 - 40 + 13",
     * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
     * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
     * Вернуть значение выражения (6 для примера).
     * Про нарушении формата входной строки бросить исключение IllegalArgumentException
     */
    @Test
    void testPlusMinus() {
        Assertions.assertEquals(0, plusMinus("0"));
        Assertions.assertEquals(4, plusMinus("2 + 2"));
        Assertions.assertEquals(0, plusMinus("2 - 2"));
        Assertions.assertEquals(-10, plusMinus("0 - 10"));
        Assertions.assertEquals(21, plusMinus("15 + 1 + 4 + 1"));
        Assertions.assertEquals(8, plusMinus("26 - 3 - 7 - 8"));
        Assertions.assertEquals(-169, plusMinus("4 - 200 + 58 - 31"));
        Assertions.assertEquals(0, plusMinus("0 - 0"));
        Assertions.assertEquals(Integer.MIN_VALUE, plusMinus(Integer.MAX_VALUE + " + 1"));
        Assertions.assertEquals(Integer.MAX_VALUE, plusMinus(Integer.MIN_VALUE + " - 1"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("+1"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("+ 1"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("1 - -1"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("1 -- 1"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("55 + + 55"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("1 + - 55"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("1+55"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("1-55"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("1 - 55.5"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("- 1 - 55"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("1 55"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("11         55"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("1       + 55"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("1       -  55"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("2 + 2 + n"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("2 * 2 + 1"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("2 / 2 + 1"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("Hello World!"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus(""));

        // Dropped tests
        Assertions.assertThrows(IllegalArgumentException.class, () -> plusMinus("-1 - 55"));
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
    @Test
    void testFromRoman() {
        Assertions.assertEquals(1, fromRoman("I"));
        Assertions.assertEquals(2, fromRoman("II"));
        Assertions.assertEquals(3, fromRoman("III"));
        Assertions.assertEquals(4, fromRoman("IV"));
        Assertions.assertEquals(5, fromRoman("V"));
        Assertions.assertEquals(6, fromRoman("VI"));
        Assertions.assertEquals(7, fromRoman("VII"));
        Assertions.assertEquals(8, fromRoman("VIII"));
        Assertions.assertEquals(9, fromRoman("IX"));
        Assertions.assertEquals(10, fromRoman("X"));
        Assertions.assertEquals(40, fromRoman("XL"));
        Assertions.assertEquals(50, fromRoman("L"));
        Assertions.assertEquals(90, fromRoman("XC"));
        Assertions.assertEquals(100, fromRoman("C"));
        Assertions.assertEquals(400, fromRoman("CD"));
        Assertions.assertEquals(500, fromRoman("D"));
        Assertions.assertEquals(900, fromRoman("CM"));
        Assertions.assertEquals(1000, fromRoman("M"));
        Assertions.assertEquals(125, fromRoman("CXXV"));
        Assertions.assertEquals(2025, fromRoman("MMXXV"));
        Assertions.assertEquals(650, fromRoman("DCL"));
        Assertions.assertEquals(5431, fromRoman("MMMMMCDXXXI"));
        Assertions.assertEquals(67913, fromRoman("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMCMXIII"));
        Assertions.assertEquals(-1, fromRoman("A"));
        Assertions.assertEquals(-1, fromRoman(""));
        Assertions.assertEquals(-1, fromRoman(" "));
        Assertions.assertEquals(-1, fromRoman("C X X V"));
        Assertions.assertEquals(-1, fromRoman("阿欣馬拉哈利莫維奇"));

        //Dropped tests
        Assertions.assertEquals(-1, fromRoman("IVXLCDM"));
        Assertions.assertEquals(-1, fromRoman("CDDDDM"));

        // Ultra corner case for overflow
//        StringBuilder roman = new StringBuilder();
//        for (int i = 0; i <= Integer.MAX_VALUE / 1000; i++) {
//            roman.append("M");
//        }
//        Assertions.assertEquals(-1, fromRoman(roman.toString()));
    }
}
