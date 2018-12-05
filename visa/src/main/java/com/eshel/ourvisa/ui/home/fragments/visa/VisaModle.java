package com.eshel.ourvisa.ui.home.fragments.visa;

import com.eshel.ourvisa.VisaApp;
import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.IVisaModle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IVisaModleCallback;
import com.eshel.ourvisa.util.ThreadUtil;

import java.util.ArrayList;

public class VisaModle extends Modle<IVisaModleCallback> implements IVisaModle {

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
    protected void onClose() {

    }
}

