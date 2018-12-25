package com.eshel.ourvisa.ui.user.forgetpsw;

import com.eshel.ourvisa.mvp.base.Presenter;
import com.eshel.ourvisa.mvp.base.factory.ModleFactory;
import com.eshel.ourvisa.mvp.presenters.IForgetpswPresenter;
import com.eshel.ourvisa.mvp.view.IForgetpswView;
import com.eshel.ourvisa.ui.user.UserModle;

public class ForgetpswPresenter extends Presenter<IForgetpswView,UserModle> implements IForgetpswPresenter {

    @Override
    protected void onClose() {

    }

    @Override
    protected UserModle initModle() {
        return ModleFactory.getModle(UserModle.class);
    }

    @Override
    protected boolean closeModleData() {
        return false;
    }
}

