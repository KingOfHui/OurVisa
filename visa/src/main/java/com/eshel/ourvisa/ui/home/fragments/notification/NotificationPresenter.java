package com.eshel.ourvisa.ui.home.fragments.notification;

import com.eshel.ourvisa.mvp.base.Presenter;
import com.eshel.ourvisa.mvp.presenters.INotificationPresenter;
import com.eshel.ourvisa.mvp.view.INotificationView;

public class NotificationPresenter extends Presenter<INotificationView,NotificationModle> implements INotificationPresenter {

    @Override
    protected void onClose() {

    }
}

