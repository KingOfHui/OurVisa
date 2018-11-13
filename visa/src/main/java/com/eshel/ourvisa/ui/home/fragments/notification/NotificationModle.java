package com.eshel.ourvisa.ui.home.fragments.notification;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.modlecallback.INotificationModleCallback;

public class NotificationModle extends Modle<INotificationModleCallback> {

    public NotificationModle(INotificationModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {

    }
}

