package com.eshel.ourvisa.ui.home.fragments.my;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IMyModleCallback;

public class MyModle extends Modle<IMyModleCallback> {

    public MyModle(IMyModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {

    }
}

