package com.eshel.ourvisa.ui.user.login;

import com.eshel.ourvisa.mvp.base.Presenter;
import com.eshel.ourvisa.mvp.base.factory.ModleFactory;
import com.eshel.ourvisa.mvp.presenters.ILoginPresenter;
import com.eshel.ourvisa.mvp.view.ILoginView;
import com.eshel.ourvisa.ui.user.UserModle;

public class LoginPresenter extends Presenter<ILoginView, UserModle> implements ILoginPresenter {

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
