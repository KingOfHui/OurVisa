package com.eshel.ourvisa.ui.home.fragments.visa;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IVisaModleCallback;

public class VisaModle extends Modle<IVisaModleCallback> {

    public VisaModle(IVisaModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {

    }
}

