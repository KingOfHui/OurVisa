package com.eshel.ourvisa.ui.home;

import com.eshel.ourvisa.mvp.base.Presenter;
import com.eshel.ourvisa.mvp.presenters.IHomePresenter;
import com.eshel.ourvisa.mvp.view.IHomeView;
import com.eshel.ourvisa.ui.jump.JumpUtil;

public class HomePresenter extends Presenter<IHomeView,HomeModle> implements IHomePresenter {

    @Override
    protected void onClose() {

    }
}

