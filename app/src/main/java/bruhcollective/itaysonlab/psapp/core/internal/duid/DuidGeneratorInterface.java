package bruhcollective.itaysonlab.psapp.core.internal.duid;

import android.content.Context;

public interface DuidGeneratorInterface {
    interface Factory {
        boolean a(Context context);

        String[] a();

        DuidGeneratorInterface b(Context context);

        boolean isAvailable(Context context);
    }

    String a();
}
