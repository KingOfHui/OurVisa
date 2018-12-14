package com.eshel.ourvisa.ui.home.fragments.visa;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.VisaApp;
import com.eshel.ourvisa.bean.local.VisaCategoryV;
import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.IVisaModle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IVisaModleCallback;
import com.eshel.ourvisa.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

public class VisaModle extends Modle<IVisaModleCallback> implements IVisaModle {

    List<VisaCategoryV> categoryDatas;
    public VisaModle(IVisaModleCallback callback) {
        super(callback);
    }

    @Override
    public void loadBannerData() {
        ThreadUtil.postMainDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> urls = new ArrayList<>(3);
                urls.add("http://i.imgur.com/LbUZ3Dt.gif");
                urls.add("http://i.imgur.com/HgOyJED.png");
                urls.add("http://img-blog.csdn.net/20180316202828255?watermark/2/text/Ly9ibG9nLmNzZG4ubmV0L3FxXzI3MDcwMTE3/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70");
                urls.add("http://raw.githubusercontent.com/EshelGuo/Chess/master/images/image2.png");
                mCallback.callbackBannerList(urls);
            }
        }, VisaApp.getContext().getConfig().getBaseLoadingTime());
    }

    @Override
    public void loadCategoryData() {
        if(categoryDatas == null){
            categoryDatas = new ArrayList<>(8);
            categoryDatas.add(new VisaCategoryV(VisaCategoryV.ID_SING_VISA, R.drawable.ic_explore, R.string.sign_visa));
            categoryDatas.add(new VisaCategoryV(VisaCategoryV.ID_DOUBLE_VISA, R.drawable.ic_explore, R.string.double_visa));
            categoryDatas.add(new VisaCategoryV(VisaCategoryV.ID_DOUBLE_PARTICULARLY_VISA, R.drawable.ic_explore, R.string.double_visa_particularly));
            categoryDatas.add(new VisaCategoryV(VisaCategoryV.ID_HALF_VISA, R.drawable.ic_explore, R.string.visa_half));

            categoryDatas.add(new VisaCategoryV(VisaCategoryV.ID_VISA_DOC, R.drawable.ic_explore, R.string.visa_doc));
            categoryDatas.add(new VisaCategoryV(VisaCategoryV.ID_SUCCESS_DEMO, R.drawable.ic_explore, R.string.visa_success_demo));
            categoryDatas.add(new VisaCategoryV(VisaCategoryV.ID_HELP_CENTER, R.drawable.ic_explore, R.string.help_center));
            categoryDatas.add(new VisaCategoryV(VisaCategoryV.ID_ABOUT, R.drawable.ic_explore, R.string.about));
        }
        mCallback.callbackCategoryData(categoryDatas);
    }

    @Override
    protected void onClose() {
        categoryDatas = null;
    }
}

