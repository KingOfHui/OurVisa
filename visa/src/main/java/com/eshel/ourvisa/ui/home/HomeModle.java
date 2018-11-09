package com.eshel.ourvisa.ui.home;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IHomeModleCallback;

public class HomeModle extends Modle<IHomeModleCallback> {

    public HomeModle(IHomeModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {

    }
}

