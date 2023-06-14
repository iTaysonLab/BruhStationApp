package bruhcollective.itaysonlab.psapp.core.internal.duid;

public class ByteUtils {
    private static final char[] f5619a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String a(byte[] bArr) {
        return a(bArr, 0, bArr.length, f5619a);
    }

    public static String a(byte[] bArr, int i, int i2, char[] cArr) {
        if (i <= i2) {
            int length = bArr.length;
            if (i < 0 || i > length) {
                throw new ArrayIndexOutOfBoundsException();
            } else if (cArr.length == 16) {
                if (i2 > length) {
                    i2 = length;
                }
                StringBuilder sb = new StringBuilder((i2 - i) * 2);
                while (i < i2) {
                    byte b2 = (byte) (bArr[i] & 255);
                    sb.append(cArr[b2 >>> 4]);
                    sb.append(cArr[b2 & 15]);
                    i++;
                }
                return sb.toString();
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}