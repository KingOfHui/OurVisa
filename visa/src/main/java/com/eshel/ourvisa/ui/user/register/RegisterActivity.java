package com.eshel.ourvisa.ui.user.register;
import com.eshel.ourvisa.R;
import com.eshel.ourvisa.mvp.base.MVPActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eshel.ourvisa.mvp.view.IRegisterView;
import com.eshel.ourvisa.titles.BackTitleHolder;

public class RegisterActivity extends MVPActivity<BackTitleHolder, RegisterPresenter> implements IRegisterView {

    @Override
    public BackTitleHolder initTitleHolder() {
        return new BackTitleHolder(this).setTitle(R.string.register);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}

