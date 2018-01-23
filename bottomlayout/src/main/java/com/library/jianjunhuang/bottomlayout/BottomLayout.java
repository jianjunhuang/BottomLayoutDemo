package com.library.jianjunhuang.bottomlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by jianjunhuang on 18-1-23.
 */

public class BottomLayout extends LinearLayout {
    public BottomLayout(Context context) {
        this(context, null);
    }

    public BottomLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BottomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
