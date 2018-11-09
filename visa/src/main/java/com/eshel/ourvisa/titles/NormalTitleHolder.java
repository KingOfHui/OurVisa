package com.eshel.ourvisa.titles;

import android.content.Context;
import android.view.View;

/**
 * 无返回键Title
 */
public class NormalTitleHolder extends BackTitleHolder{

    public NormalTitleHolder(Context context) {
        super(context);
        back.setVisibility(View.GONE);
        textBack.setVisibility(View.GONE);
    }

    @Override
    public NormalTitleHolder setTitle(int titleId) {
        return (NormalTitleHolder) super.setTitle(titleId);
    }

    @Override
    public NormalTitleHolder setTitle(CharSequence title) {
        return (NormalTitleHolder) super.setTitle(title);
    }
}
