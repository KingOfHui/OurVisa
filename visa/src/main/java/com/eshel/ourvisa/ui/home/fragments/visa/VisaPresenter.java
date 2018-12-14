package com.eshel.ourvisa.ui.home.fragments.visa;

import android.view.View;

import com.eshel.ourvisa.VisaApp;
import com.eshel.ourvisa.bean.local.VisaCategoryV;
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
        mDatas.loadCategoryData();
    }

    @Override
    public void callbackBannerList(List<String> banners) {
        mView.initDataSuccess();
        mView.loadBannerImage(banners);
    }

    @Override
    public void callbackCategoryData(List<VisaCategoryV> categoryDatas) {
        if(categoryDatas != null){
            View.OnClickListener click = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            };
            for (VisaCategoryV categoryData : categoryDatas) {
                switch (categoryData.getId()){
                    case VisaCategoryV.ID_SING_VISA:
                        break;
                    case VisaCategoryV.ID_DOUBLE_VISA:
                        break;
                    case VisaCategoryV.ID_DOUBLE_PARTICULARLY_VISA:
                        break;
                }
                categoryData.setClickListener(click);
            }
        }

        mView.initDataSuccess();
        mView.refreshCategory(categoryDatas);
    }
}

