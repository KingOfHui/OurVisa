package com.eshel.ourvisa.mvp.view;

import com.eshel.ourvisa.mvp.base.IView;

public interface IWelcomeView extends IView {
    /**
     * 在达到指定时间后进入主界面
     */
    void enterIntoHome();

    /**
     * 进入登录界面
     */
    void enterIntoLogin();
}
