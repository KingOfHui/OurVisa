package com.eshel.ourvisa.ui.user.register;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.IRegisterModle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IRegisterModleCallback;

public class RegisterModle extends Modle<IRegisterModleCallback> implements IRegisterModle {

    public RegisterModle(IRegisterModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {

    }
}

