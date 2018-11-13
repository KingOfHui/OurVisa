package com.eshel.ourvisa.ui.home.fragments.discover;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IDiscoverModleCallback;

public class DiscoverModle extends Modle<IDiscoverModleCallback> {

    public DiscoverModle(IDiscoverModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {

    }
}

