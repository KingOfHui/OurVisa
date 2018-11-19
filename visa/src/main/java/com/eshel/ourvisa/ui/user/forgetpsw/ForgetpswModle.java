package com.eshel.ourvisa.ui.user.forgetpsw;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.IForgetpswModle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IForgetpswModleCallback;

public class ForgetpswModle extends Modle<IForgetpswModleCallback> implements IForgetpswModle {

    public ForgetpswModle(IForgetpswModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {

    }
}

