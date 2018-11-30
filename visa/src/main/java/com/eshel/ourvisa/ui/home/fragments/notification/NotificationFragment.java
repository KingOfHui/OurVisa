package com.eshel.ourvisa.ui.home.fragments.notification;
import com.eshel.ourvisa.R;
import com.eshel.ourvisa.base.State;
import com.eshel.ourvisa.mvp.base.MVPFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.eshel.ourvisa.titles.DefaultTitleHolder;
import com.eshel.ourvisa.mvp.view.INotificationView;
import com.eshel.ourvisa.titles.NormalTitleHolder;
import com.eshel.ourvisa.util.ThreadUtil;
import com.eshel.ourvisa.util.UIUtil;

public class NotificationFragment extends MVPFragment<NormalTitleHolder, NotificationPresenter> implements INotificationView {

    @Override
    protected NormalTitleHolder initTitleHolder() {
        return new NormalTitleHolder(getContext()).setTitle(R.string.bottom_notification);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View loadSuccess() {
        return UIUtil.getDefaultFragmentLayout(this);
    }

    @Override
    protected void fristResume() {
        super.fristResume();
        ThreadUtil.postMainDelayed(new Runnable() {
            @Override
            public void run() {
                setState(State.STATE_SUCCESS);
            }
        }, mConfig.getBaseLoadingTime());
    }
}

