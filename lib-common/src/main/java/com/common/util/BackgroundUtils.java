package com.common.util;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by 刘金豪 on 2021/1/15
 * desc:  与MyTitleBar搭配使用
 */
public class BackgroundUtils {

    /**
     * @param view
     */
    public static void setRippleBackground(View view) {
        if (view == null)
            return;

        Drawable drawable;

        TypedValue typedValue = new TypedValue();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true);
            int[] attribute = new int[]{android.R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray = view.getContext().getTheme().obtainStyledAttributes(typedValue.resourceId, attribute);
            drawable = typedArray.getDrawable(0);
            typedArray.recycle();
        } else {
            view.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
            int[] attribute = new int[]{android.R.attr.selectableItemBackground};
            TypedArray typedArray = view.getContext().getTheme().obtainStyledAttributes(typedValue.resourceId, attribute);
            drawable = typedArray.getDrawable(0);
            typedArray.recycle();
        }
        if (drawable != null) {
            view.setBackground(drawable);
        }
    }
}