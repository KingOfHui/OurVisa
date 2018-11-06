package com.eshel.ourvisa.mvp.base.factory;

import com.eshel.ourvisa.mvp.base.IView;
import com.eshel.ourvisa.mvp.base.Presenter;
import com.eshel.ourvisa.ui.welcome.WelcomePresenter;

public class SimPManager {

    @SuppressWarnings("unchecked")
    public static void bind(Class<? extends Presenter> pClass, IView view){
        PManager.getPresenter(pClass).bind(view);
    }

    public static void unbind(Class<? extends Presenter> pClass){
        PresenterFactory.closePresenter(pClass);
    }

    public static WelcomePresenter getWelcomePresenter(){
        return PManager.getPresenter(WelcomePresenter.class);
    }
/*
    public static HomePresenter getHomePresenter(){
        return PManager.getPresenter(HomePresenter.class);
    }

    public static MarketPresenter getMarketPresenter(){
        return PManager.getPresenter(MarketPresenter.class);
    }*/
}
