package butterknife.internal;

import android.os.SystemClock;
import android.view.View;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * A {@linkplain View.OnClickListener click listener} that debounces multiple clicks posted in the
 * same frame. A click on one button disables all buttons for that frame.
 */
public abstract class CustomDebouncingOnClickListener implements View.OnClickListener {
    private final long minimumInterval = 300;
    private Map<View, Long> lastClickMap = new WeakHashMap<View, Long>();


    @Override
    public final void onClick(View v) {
        Long previousClickTimestamp = lastClickMap.get(v);
        long currentTimestamp = SystemClock.uptimeMillis();

        lastClickMap.put(v, currentTimestamp);
        if (previousClickTimestamp == null || (currentTimestamp - previousClickTimestamp.longValue() > minimumInterval)) {
            doClick(v);
        }
    }

    public abstract void doClick(View v);
}
