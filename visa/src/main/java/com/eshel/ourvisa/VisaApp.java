package com.eshel.ourvisa;

import com.eshel.ourvisa.mvp.base.factory.ModleFactory;
import com.eshel.ourvisa.mvp.modles.ConfigModle;
import com.eshel.ourvisa.ui.welcome.WelcomeModle;
import com.eshel.ourvisa.ui.welcome.WelcomePresenter;
import com.eshel.ourvisa.util.ObjUtil;

public class VisaApp extends BaseApplication{

    private ConfigModle config;

    @Override
    public void onCreate() {
        WelcomeModle.prepareCountingTime();
        super.onCreate();
        config = ModleFactory.getModle(ConfigModle.class);
    }

    public static VisaApp getContext(){
        return ObjUtil.cast(BaseApplication.getContext());
    }

    public static boolean isDebug(){
        return BuildConfig.DEBUG;
    }
}
