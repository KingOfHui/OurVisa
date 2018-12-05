package com.eshel.ourvisa.ui.home.fragments.visa;

import com.eshel.ourvisa.VisaApp;
import com.eshel.ourvisa.mvp.base.Presenter;
import com.eshel.ourvisa.mvp.modles.modlecallback.IVisaModleCallback;
import com.eshel.ourvisa.mvp.presenters.IVisaPresenter;
import com.eshel.ourvisa.mvp.view.IVisaView;
import com.eshel.ourvisa.ui.home.fragments.my.MyModle;
import com.eshel.ourvisa.util.ThreadUtil;

import java.util.List;

public class VisaPresenter extends Presenter<IVisaView,VisaModle> implements IVisaPresenter, IVisaModleCallback {

    @Override
    protected void onClose() {

    }


    @Override
    public void initVisaDatas() {
        mDatas.loadBannerData();
    }

    @Override
    public void callbackBannerList(List<String> banners) {
        mView.initDataSuccess();
        mView.loadBannerImage(banners);
    }
}

