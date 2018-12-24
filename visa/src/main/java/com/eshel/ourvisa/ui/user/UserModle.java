package com.eshel.ourvisa.ui.user;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.IUserModle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IUserModleCallback;

public class UserModle extends Modle<IUserModleCallback> implements IUserModle {

    public UserModle(IUserModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {

    }
}
