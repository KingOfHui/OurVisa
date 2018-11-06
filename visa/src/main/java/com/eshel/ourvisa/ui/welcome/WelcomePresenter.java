package com.eshel.ourvisa.ui.welcome;

import com.eshel.ourvisa.mvp.base.Presenter;
import com.eshel.ourvisa.mvp.modles.modlecallback.IWelcomeModleCallback;
import com.eshel.ourvisa.mvp.presenters.IWelcomePresenter;
import com.eshel.ourvisa.mvp.view.IWelcomeView;

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
        mView.enterIntoHome();
    }
}
