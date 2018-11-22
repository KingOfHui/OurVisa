package com.eshel.ourvisa.ui.home.fragments.visa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.base.State;
import com.eshel.ourvisa.mvp.base.MVPFragment;
import com.eshel.ourvisa.mvp.view.IVisaView;
import com.eshel.ourvisa.titles.DefaultTitleHolder;
import com.eshel.ourvisa.util.ThreadUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
        View root = View.inflate(getContext(), R.layout.fragment_visa, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @OnClick({R.id.cv_cv_visa_diplomatic_mission, R.id.cv_visa_diplomatic_mission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_cv_visa_diplomatic_mission:
                break;
            case R.id.cv_visa_diplomatic_mission:
                break;
        }
    }
}

