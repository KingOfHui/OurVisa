package com.eshel.ourvisa.mvp.modles.modlecallback;

import com.eshel.ourvisa.mvp.base.ModleCallback;

import java.util.List;

public interface IVisaModleCallback extends ModleCallback {

    void callbackBannerList(List<String> banners);
}

