package com.eshel.ourvisa.ui.user.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.mvp.base.MVPActivity;
import com.eshel.ourvisa.mvp.view.ILoginView;
import com.eshel.ourvisa.titles.BackTitleHolder;
import com.eshel.ourvisa.ui.home.HomeActivity;
import com.eshel.ourvisa.ui.user.forgetpsw.ForgetPswActivity;
import com.eshel.ourvisa.ui.user.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends MVPActivity<BackTitleHolder, LoginPresenter> implements ILoginView {

    @BindView(R.id.icon_clear)
    ImageView iconClear;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.forget_psw)
    TextView forgetPsw;
    @BindView(R.id.cb_auto_login)
    CheckBox cbAutoLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.sign_up)
    Button signUp;
    @BindView(R.id.cb_hint)
    CheckBox cbHint;

    @Override
    public BackTitleHolder initTitleHolder() {
        return new BackTitleHolder(this).setTitle(R.string.login);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.forget_psw, R.id.btn_login, R.id.sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_psw:
                startActivity(ForgetPswActivity.class);
                break;
            case R.id.btn_login:
                break;
            case R.id.sign_up:
                startActivity(RegisterActivity.class);
                break;
        }
    }
}
