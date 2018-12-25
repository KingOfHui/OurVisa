package com.eshel.ourvisa.ui.user.register;

import com.eshel.ourvisa.mvp.base.Presenter;
import com.eshel.ourvisa.mvp.base.factory.ModleFactory;
import com.eshel.ourvisa.mvp.presenters.IRegisterPresenter;
import com.eshel.ourvisa.mvp.view.IRegisterView;
import com.eshel.ourvisa.ui.user.UserModle;

public class RegisterPresenter extends Presenter<IRegisterView,UserModle> implements IRegisterPresenter {

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

