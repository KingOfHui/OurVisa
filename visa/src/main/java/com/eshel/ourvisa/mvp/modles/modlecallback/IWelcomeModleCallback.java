package com.eshel.ourvisa.mvp.modles.modlecallback;

import com.eshel.ourvisa.mvp.base.ModleCallback;

public interface IWelcomeModleCallback extends ModleCallback {

    /**
     * 结束计时, 此时进入主页
     */
    void endCountingTime();
}
