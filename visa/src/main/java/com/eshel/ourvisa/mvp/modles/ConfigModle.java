package com.eshel.ourvisa.mvp.modles;

import com.eshel.ourvisa.VisaApp;
import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.base.ModleCallback;

public class ConfigModle extends Modle {

    public ConfigModle(ModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {

    }

    public VisaApp getApplication(){
        return VisaApp.getContext();
    }

    public long getBaseLoadingTime(){
        return 150;
    }
}
