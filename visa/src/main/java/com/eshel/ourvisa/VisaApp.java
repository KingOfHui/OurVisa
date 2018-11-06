package com.eshel.ourvisa;

import com.eshel.ourvisa.ui.welcome.WelcomeModle;
import com.eshel.ourvisa.ui.welcome.WelcomePresenter;
import com.eshel.ourvisa.util.ObjUtil;

public class VisaApp extends BaseApplication{

    @Override
    public void onCreate() {
        WelcomeModle.prepareCountingTime();
        super.onCreate();
    }

    public static VisaApp getContext(){
        return ObjUtil.cast(BaseApplication.getContext());
    }

    public static boolean isDebug(){
        return BuildConfig.DEBUG;
    }
}
