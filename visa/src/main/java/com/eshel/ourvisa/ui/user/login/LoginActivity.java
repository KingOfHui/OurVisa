package com.eshel.ourvisa.ui.user.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.mvp.base.MVPActivity;
import com.eshel.ourvisa.mvp.view.ILoginView;
import com.eshel.ourvisa.titles.BackTitleHolder;

public class LoginActivity extends MVPActivity<BackTitleHolder, LoginPresenter> implements ILoginView {

    @Override
    public BackTitleHolder initTitleHolder() {
        return new BackTitleHolder(this).setTitle(R.string.login);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
