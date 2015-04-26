package com.ff55lab.chart;

import android.content.Context;

public final class Common {

    public static float dipToPixel(Context context, float dip) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (dip * scale + 0.5f);
        } catch (Exception e) {
            return dip;
        }
    }

}
