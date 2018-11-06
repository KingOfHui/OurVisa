package com.eshel.ourvisa.ui.welcome;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.IWelcomeModle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IWelcomeModleCallback;
import com.eshel.ourvisa.util.ThreadUtil;

public class WelcomeModle extends Modle<IWelcomeModleCallback> implements IWelcomeModle {

    private static long sAgo;

    /*
     * 继承 Modle, 必须重写该构造方法
     */
    public WelcomeModle(IWelcomeModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {
        sAgo = 0;
    }

    /**
     * 在首次进入 App 后在 Application 中调用, 计算冷屏时间, 非首次不用调用
     */
    public static void prepareCountingTime() {
        sAgo = System.currentTimeMillis();
    }

    @Override
    public void startCountingTime() {
        long now = System.currentTimeMillis();
        long maxTime = getMaxTime();

        if(sAgo != 0){
            maxTime -= (now - sAgo);
            if(maxTime < 0)
                maxTime = 0;
        }

        ThreadUtil.postMainDelayed(new Runnable() {
            @Override
            public void run() {
                mCallback.endCountingTime();
            }
        }, maxTime);
    }

    @Override
    public long getMaxTime() {
        return 2000;
    }
}
