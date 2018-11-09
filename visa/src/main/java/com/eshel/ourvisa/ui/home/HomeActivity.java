package com.eshel.ourvisa.ui.home;
import com.eshel.ourvisa.R;
import com.eshel.ourvisa.mvp.base.MVPActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eshel.ourvisa.mvp.view.IHomeView;
import com.eshel.ourvisa.titles.NormalTitleHolder;

public class HomeActivity extends MVPActivity<NormalTitleHolder, HomePresenter> implements IHomeView {

    @Override
    public NormalTitleHolder initTitleHolder() {
        return new NormalTitleHolder(this).setTitle(R.string.bottom_home);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}

