package xyz.alex.utils;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author dumas45
 */
public class Utils {
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static void validState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    public static void validState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void validState(boolean expression, Supplier<String> message) {
        if (!expression) {
            throw new IllegalStateException(message.get());
        }
    }

    public static <T> T checkNotNull(T ref) {
        if (ref == null) {
            throw new NullPointerException();
        }
        return ref;
    }

    public static <T> T checkNotNull(T ref, String msg) {
        if (ref == null) {
            throw new NullPointerException(msg);
        }
        return ref;
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkArgument(boolean expression, Supplier<String> message) {
        if (!expression) {
            throw new IllegalArgumentException(message.get());
        }
    }

    public static int getUnsignedNumberFromBytes(int index, int numberBitCount, byte[] bytes) {
        checkArgument(index >= 0, "index");
        checkArgument(numberBitCount > 0 && numberBitCount < Integer.SIZE - 1, "numberBitCount");

        int number;
        int byteIdx = index * numberBitCount / Byte.SIZE;
        int bitIdx = index * numberBitCount % Byte.SIZE;

        int mask = (~0 << Integer.SIZE - numberBitCount) >>> Integer.SIZE - Byte.SIZE + bitIdx;

        int shift = Byte.SIZE - numberBitCount - bitIdx;
        if (shift > 0) {
            number = (bytes[byteIdx] & mask) >>> shift;
        } else {
            number = (bytes[byteIdx] & mask) << -shift;
        }

        while (shift < 0) {
            numberBitCount = -shift;
            ++byteIdx;
            bitIdx = 0;

            mask = (~0 << Integer.SIZE - numberBitCount) >>> Integer.SIZE - Byte.SIZE;

            shift = Byte.SIZE - numberBitCount - bitIdx;
            if (shift > 0) {
                number = number | ((bytes[byteIdx] & mask) >>> shift);
            } else {
                number = number | ((bytes[byteIdx] & mask) << -shift);
            }
        }

        return number;
    }

    public static void storeUnsignedNumberIntoBytes(int number, int index, int numberBitCount, byte[] bytes) {
        checkArgument(index >= 0, "index");
        checkArgument(numberBitCount > 0 && numberBitCount < Integer.SIZE - 1);

        int byteIdx = index * numberBitCount / Byte.SIZE;
        int bitIdx = index * numberBitCount % Byte.SIZE;

        int zeroMask = ~((~0 << Integer.SIZE - numberBitCount) >>> Integer.SIZE - Byte.SIZE + bitIdx);
        int valueMask = ((~0 << Integer.SIZE - numberBitCount) >>> Integer.SIZE - numberBitCount);

        number = number & valueMask;

        bytes[byteIdx] = (byte) (bytes[byteIdx] & (zeroMask));

        int shift = Byte.SIZE - numberBitCount - bitIdx;
        if (shift > 0) {
            bytes[byteIdx] = (byte) (bytes[byteIdx] | (number << shift));
        } else {
            bytes[byteIdx] = (byte) (bytes[byteIdx] | (number >>> -shift));
        }

        while (shift < 0) {
            numberBitCount = -shift;
            ++byteIdx;
            bitIdx = 0;

            zeroMask = ~((~0 << Integer.SIZE - numberBitCount) >>> Integer.SIZE - Byte.SIZE);
            valueMask = ((~0 << Integer.SIZE - numberBitCount) >>> Integer.SIZE - numberBitCount);

            number = number & valueMask;

            bytes[byteIdx] = (byte) (bytes[byteIdx] & (zeroMask));

            shift = Byte.SIZE - numberBitCount - bitIdx;
            if (shift > 0) {
                bytes[byteIdx] = (byte) (bytes[byteIdx] | (number << shift));
            } else {
                bytes[byteIdx] = (byte) (bytes[byteIdx] | (number >>> -shift));
            }
        }
    }

    public static String timeToString(long millis) {
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d.%d", hour, minute, second, millis % 1000);
    }

    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }
}
