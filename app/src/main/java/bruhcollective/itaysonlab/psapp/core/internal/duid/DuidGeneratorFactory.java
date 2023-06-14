package bruhcollective.itaysonlab.psapp.core.internal.duid;

import android.content.Context;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DuidGeneratorFactory {
    private static final DuidGeneratorInterface.Factory[] f5526a = {new MarlinDuidGenerator.Factory(), new GenericDuidGenerator.Factory()};
    private static DuidGeneratorInterface f5527b = null;

    public static Set<String> a(Context context) {
        HashSet<String> hashSet = new HashSet<>();
        DuidGeneratorInterface.Factory[] aVarArr = f5526a;

        int i = 0;
        while (i < aVarArr.length) {
            DuidGeneratorInterface.Factory aVar = aVarArr[i];
            if (aVar.a(context)) {
                String[] a2 = aVar.a();
                if (a2 != null) {
                    hashSet.addAll(Arrays.asList(a2));
                }
            } else {
                i++;
            }
        }

        return hashSet;
    }

    public static DuidGeneratorInterface b(Context context) {
        DuidGeneratorInterface bVar = f5527b;
        if (bVar != null) {
            return bVar;
        }
        int i = 0;
        while (true) {
            DuidGeneratorInterface.Factory[] aVarArr = f5526a;
            if (i >= aVarArr.length) {
                return null;
            }
            DuidGeneratorInterface.Factory aVar = aVarArr[i];
            if (aVar.isAvailable(context)) {
                f5527b = aVar.b(context);
                return f5527b;
            }
            i++;
        }
    }

    public static String getDuid(Context context) {
        return b(context).a();
    }
}
