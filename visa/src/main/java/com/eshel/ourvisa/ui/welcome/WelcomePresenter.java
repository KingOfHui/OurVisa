package com.eshel.ourvisa.ui.welcome;

import com.eshel.ourvisa.mvp.base.Presenter;
import com.eshel.ourvisa.mvp.base.factory.ModleFactory;
import com.eshel.ourvisa.mvp.modles.modlecallback.IWelcomeModleCallback;
import com.eshel.ourvisa.mvp.presenters.IWelcomePresenter;
import com.eshel.ourvisa.mvp.view.IWelcomeView;
import com.eshel.ourvisa.ui.user.UserModle;

public class WelcomePresenter extends Presenter<IWelcomeView, WelcomeModle> implements IWelcomePresenter, IWelcomeModleCallback {

    @Override
    protected void onClose() {
    }

    @Override
    public void onAppOpen() {
        mDatas.startCountingTime();
    }

    @Override
    public void endCountingTime() {
        UserModle modle = ModleFactory.getModle(UserModle.class);
        if(modle.getUser() == null)
            mView.enterIntoLogin();
        else
            mView.enterIntoHome();
    }
}
