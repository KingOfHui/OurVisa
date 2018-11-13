package com.eshel.ourvisa.ui.home.fragments.my;
import com.eshel.ourvisa.base.State;
import com.eshel.ourvisa.mvp.base.MVPFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.eshel.ourvisa.titles.DefaultTitleHolder;
import com.eshel.ourvisa.mvp.view.IMyView;
import com.eshel.ourvisa.util.ThreadUtil;
import com.eshel.ourvisa.util.UIUtil;

public class MyFragment extends MVPFragment<DefaultTitleHolder, MyPresenter> implements IMyView {

    boolean frist;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frist = true;
    }

    @Override
    public View loadSuccess() {
        return UIUtil.getDefaultFragmentLayout(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(frist){
                frist = false;
                ThreadUtil.postMainDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setState(State.STATE_SUCCESS);
                    }
                }, 150);
            }
        }
    }
}

