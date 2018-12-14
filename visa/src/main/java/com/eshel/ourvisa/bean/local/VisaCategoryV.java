package com.eshel.ourvisa.bean.local;

import android.view.View;

/**
 * V 代表View D 代表Data
 */
public class VisaCategoryV {
    public static final int ID_SING_VISA = 1;
    public static final int ID_DOUBLE_VISA = 2;
    public static final int ID_VISA_DOC = 3;
    public static final int ID_DOUBLE_PARTICULARLY_VISA = 6;//特别双认证
    public static final int ID_SUCCESS_DEMO = 7;//成功案例
    public static final int ID_HALF_VISA = 8;//半价优惠认证

    public static final int ID_HELP_CENTER = 4;
    public static final int ID_ABOUT = 5;

    private int id;
    private int iconResId;
    private int titleResId;
    private View.OnClickListener mClickListener;

    public VisaCategoryV(int id, int iconResId, int titleResId) {
        this.id = id;
        this.iconResId = iconResId;
        this.titleResId = titleResId;
    }

    public int getId() {
        return id;
    }

    public int getIconResId() {
        return iconResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public void setTitleResId(int titleResId) {
        this.titleResId = titleResId;
    }

    public View.OnClickListener getClickListener() {
        return mClickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        mClickListener = clickListener;
    }
}
