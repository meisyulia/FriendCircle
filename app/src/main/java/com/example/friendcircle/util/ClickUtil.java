package com.example.friendcircle.util;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class ClickUtil {

    private static final Map<Integer, Long> sLastClickTimes = new HashMap<>();
    private static final long CLICK_INTERVAL = 800; // 限制点击间隔为 800 毫秒

    public static boolean isFastClick(View view) {
        long now = System.currentTimeMillis();
        int viewId = view.getId();
        if (sLastClickTimes.containsKey(viewId)) {
            long lastClickTime = sLastClickTimes.get(viewId);
            if (now - lastClickTime < CLICK_INTERVAL) {
                return true;
            }
        }
        sLastClickTimes.put(viewId, now);
        return false;
    }

}

