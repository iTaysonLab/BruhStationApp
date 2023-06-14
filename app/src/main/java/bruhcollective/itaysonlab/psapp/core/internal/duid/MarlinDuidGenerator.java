package bruhcollective.itaysonlab.psapp.core.internal.duid;

import android.content.Context;
import android.drm.DrmManagerClient;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MarlinDuidGenerator implements DuidGeneratorInterface {
    public static String f5530a;

    static final class Factory implements DuidGeneratorInterface.Factory {
        Factory() {}

        private boolean c(Context context) {
            if (MarlinDuidGenerator.f5530a != null) {
                return true;
            }

            String a2 = MarlinDuidGenerator.b(context);
            if (TextUtils.isEmpty(a2)) {
                return false;
            }

            MarlinDuidGenerator.f5530a = a2;
            return true;
        }

        @Override // c.h.b.a.a.a.b.b.b.a
        public boolean a(Context context) {
            return c(context);
        }

        @Override // c.h.b.a.a.a.b.b.b.a
        public String[] a() {
            return null;
        }

        @Override // c.h.b.a.a.a.b.b.b.a
        public DuidGeneratorInterface b(Context context) {
            return new MarlinDuidGenerator(context);
        }

        @Override // c.h.b.a.a.a.b.b.b.a
        public boolean isAvailable(Context context) {
            return c(context);
        }
    }

    public MarlinDuidGenerator(Context context) {
    }

    public static String b(Context context) {
        for (String str : new DrmManagerClient(context).getAvailableDrmEngines()) {
            Matcher matcher = Pattern.compile("^Marlin BB plug-in/([0-9a-fA-F]+)(/([0-2]+))*$").matcher(str);
            if (matcher.matches() && matcher.groupCount() >= 1) {
                return matcher.group(1);
            }
        }
        return null;
    }

    @Override
    public String a() {
        return f5530a;
    }
}