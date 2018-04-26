package xyz.alex.utils;

import org.testng.annotations.Test;

import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author dumas45
 */
public class UtilsTest {

    @Test
    public void isBlank() throws Exception {
        assertTrue(Utils.isBlank(null));
        assertTrue(Utils.isBlank(""));
        assertTrue(Utils.isBlank(" "));
        assertTrue(Utils.isBlank("   "));
        assertTrue(Utils.isBlank(" \t\n"));
    }

    @Test
    public void setUnsignedNumberIntoBytes_getUnsignedNumberFromBytes() {
        for (int numberBitCount = 1; numberBitCount < Integer.SIZE - 1; ++numberBitCount) {
            String testParams = "numberBitCount = " + numberBitCount;
            int maxNumber = (~0 >>> Integer.SIZE - numberBitCount);
            Random rnd = new Random(1488659188446L);
            int size = 100;
            byte[] bytes = new byte[numberBitCount * size / Byte.SIZE + 1];
            int[] expected = new int[size];

            for (int i = 0; i < 1000; ++i) {
                if (rnd.nextInt(20) < 10) {
                    int b = rnd.nextInt(maxNumber + 1);
                    int idx = rnd.nextInt(size);
                    Utils.storeUnsignedNumberIntoBytes(b, idx, numberBitCount, bytes);
                    expected[idx] = b;
                } else {
                    int idx = rnd.nextInt(size);
                    assertEquals(Utils.getUnsignedNumberFromBytes(idx, numberBitCount, bytes), expected[idx], testParams);
                }
            }

            for (int idx = 0; idx < size; ++idx) {
                assertEquals(Utils.getUnsignedNumberFromBytes(idx, numberBitCount, bytes), expected[idx], testParams);
            }
        }
    }
}
