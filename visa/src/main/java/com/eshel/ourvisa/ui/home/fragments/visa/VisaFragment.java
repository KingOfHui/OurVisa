package com.eshel.ourvisa.ui.home.fragments.visa;
import com.eshel.ourvisa.base.State;
import com.eshel.ourvisa.mvp.base.MVPFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.eshel.ourvisa.titles.DefaultTitleHolder;
import com.eshel.ourvisa.mvp.view.IVisaView;
import com.eshel.ourvisa.util.ThreadUtil;
import com.eshel.ourvisa.util.UIUtil;

public class VisaFragment extends MVPFragment<DefaultTitleHolder, VisaPresenter> implements IVisaView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThreadUtil.postMainDelayed(new Runnable() {
            @Override
            public void run() {
                setState(State.STATE_SUCCESS);
            }
        }, 150);
    }

    @Override
    public View loadSuccess() {
        return UIUtil.getDefaultFragmentLayout(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}

