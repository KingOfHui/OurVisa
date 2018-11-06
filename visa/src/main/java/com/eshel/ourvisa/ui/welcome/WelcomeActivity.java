package com.eshel.ourvisa.ui.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.mvp.base.MVPActivity;
import com.eshel.ourvisa.mvp.view.IWelcomeView;
import com.eshel.ourvisa.titles.DefaultTitleHolder;
import com.eshel.ourvisa.ui.user.login.LoginActivity;

public class WelcomeActivity extends MVPActivity<DefaultTitleHolder, WelcomePresenter> implements IWelcomeView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.onAppOpen();
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void enterIntoHome() {
        finish();
        startActivity(LoginActivity.class);
    }
}
