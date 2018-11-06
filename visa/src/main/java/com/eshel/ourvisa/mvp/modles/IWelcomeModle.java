package com.eshel.ourvisa.mvp.modles;

public interface IWelcomeModle {

    /**
     * 在首次进入 App 后在 Application 中调用, 计算冷屏时间, 非首次不用调用
     */
//   static void prepareCountingTime();
    /**
     * 开始计时, 在进入 WelcomeActivity 时
     */
    void startCountingTime();

    /**
     * 获取闪屏页面停留总时间
     */
    long getMaxTime();
}
