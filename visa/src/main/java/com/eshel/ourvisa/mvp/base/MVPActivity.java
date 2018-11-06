package com.eshel.ourvisa.mvp.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eshel.ourvisa.base.BaseActivity;
import com.eshel.ourvisa.base.BaseTitleHolder;
import com.eshel.ourvisa.mvp.base.factory.PManager;
import com.eshel.ourvisa.mvp.base.factory.SimPManager;
import com.eshel.ourvisa.util.ReflectUtil;

@SuppressLint("Registered")
public class MVPActivity<TITLEHOLDER extends BaseTitleHolder, PRESENTER extends Presenter> extends BaseActivity<TITLEHOLDER> {

    protected PRESENTER mPresenter;
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (this instanceof IView) {
            Class<PRESENTER> clazz = ReflectUtil.getClassByT(getClass());
            mPresenter = PManager.getPresenter(clazz);
            if(mPresenter != null)
                mPresenter.bind((IView) this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (this instanceof IView) {
            Class<PRESENTER> clazz = ReflectUtil.getClassByT(getClass());
            SimPManager.unbind(clazz);
            mPresenter = null;
        }
        super.onDestroy();
    }
}
