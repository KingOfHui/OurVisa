package com.eshel.ourvisa.ui.user.login;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.modlecallback.ILoginModleCallback;

public class LoginModle extends Modle<ILoginModleCallback> {

    public LoginModle(ILoginModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {

    }
}
