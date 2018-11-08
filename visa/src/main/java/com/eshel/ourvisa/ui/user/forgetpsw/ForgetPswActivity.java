package com.eshel.ourvisa.ui.user.forgetpsw;
import com.eshel.ourvisa.R;
import com.eshel.ourvisa.mvp.base.MVPActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eshel.ourvisa.titles.BackTitleHolder;
import com.eshel.ourvisa.mvp.view.IForgetpswView;

public class ForgetPswActivity extends MVPActivity<BackTitleHolder, ForgetpswPresenter> implements IForgetpswView {

    @Override
    public BackTitleHolder initTitleHolder() {
        return new BackTitleHolder(this).setTitle(R.string.forget_psw);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);
    }
}

