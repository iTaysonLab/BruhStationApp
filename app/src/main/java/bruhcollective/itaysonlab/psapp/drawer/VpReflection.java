package bruhcollective.itaysonlab.psapp.drawer;

import android.widget.Scroller;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class VpReflection {
    public static void installSmoothScroll(ViewPager pager) {
        try {
            Field f = ViewPager.class.getDeclaredField("mScroller");
            f.setAccessible(true);
            f.set(pager, new Scroller(pager.getContext(), input -> {
                input -= 1;
                return (float) (Math.pow(input, 5) + 1f);
            }) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, (int) (duration * 1.5f));
                }
            });
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
