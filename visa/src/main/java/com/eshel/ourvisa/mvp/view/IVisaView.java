package com.eshel.ourvisa.mvp.view;

import com.eshel.ourvisa.mvp.base.IView;

import java.util.List;

public interface IVisaView extends IView {
    void loadBannerImage(List<? extends Object> datas);
    void initDataSuccess();
}

