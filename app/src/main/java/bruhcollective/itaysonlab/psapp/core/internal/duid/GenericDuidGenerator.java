package bruhcollective.itaysonlab.psapp.core.internal.duid;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import bruhcollective.itaysonlab.psapp.log.LoggingFacade;

class GenericDuidGenerator implements DuidGeneratorInterface {
    private static final String f5528b = a('0', 30);
    private final Context f5529a;

    static final class Factory implements DuidGeneratorInterface.Factory {
        Factory() {}

        @Override
        public boolean a(Context context) {
            return true;
        }

        @Override
        public String[] a() {
            return null;
        }

        @Override
        public DuidGeneratorInterface b(Context context) {
            return new GenericDuidGenerator(context);
        }

        @Override
        public boolean isAvailable(Context context) {
            return true;
        }
    }

    public GenericDuidGenerator(Context context) {
        this.f5529a = context;
    }

    @Override
    public String a() {
        String a2 = a(Settings.Secure.getString(this.f5529a.getContentResolver(), "android_id"));
        String a3 = a(f5528b + a2, 30);
        byte[] bytes = String.format(":%s:%s", String.format("%10s", Build.MANUFACTURER).substring(0, 10), String.format("%10s", Build.MODEL).substring(0, 10)).getBytes(Charset.defaultCharset());
        byte[] bArr = new byte[16];
        Arrays.fill(bArr, (byte) 0);
        String format = String.format("0000%s%s01a8%s%s", "0007", "0008", a3, ByteUtils.a(ByteBuffer.allocate(bytes.length + bArr.length).put(bytes).put(bArr).array()));
        LoggingFacade.logDebug("Nsadg", "Generic DUID: %s", format);
        return format;
    }

    private static String a(char c2, int i) {
        if (i <= 0) {
            return "";
        }
        char[] cArr = new char[i];
        Arrays.fill(cArr, c2);
        return new String(cArr);
    }

    private static String a(String str, int i) {
        int length = str.length() - i;
        return str.substring(length, i + length);
    }

    private static String a(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (('0' > charAt || charAt > '9') && (('a' > charAt || charAt > 'f') && ('A' > charAt || charAt > 'F'))) {
                LoggingFacade.logDebug("Nsadg", "'%c' was truncated.", charAt);
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }
}