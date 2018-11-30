package com.eshel.ourvisa.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.eshel.ourvisa.base.BaseFragment;
import com.eshel.ourvisa.base.BaseTitleHolder;
import com.eshel.ourvisa.mvp.base.factory.ModleFactory;
import com.eshel.ourvisa.mvp.base.factory.PManager;
import com.eshel.ourvisa.mvp.base.factory.SimPManager;
import com.eshel.ourvisa.mvp.modles.ConfigModle;
import com.eshel.ourvisa.util.ReflectUtil;

public abstract class MVPFragment<TITLEHOLDER extends BaseTitleHolder, PRESENTER extends Presenter> extends BaseFragment<TITLEHOLDER> {
    protected PRESENTER mPresenter;
    protected ConfigModle mConfig;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (this instanceof IView) {
            Class<PRESENTER> clazz = ReflectUtil.getClassByT(getClass(), 1);
            mPresenter = PManager.getPresenter(clazz);
            if(mPresenter != null)
                mPresenter.bind((IView) this);
        }
        super.onCreate(savedInstanceState);

        mConfig = ModleFactory.getModle(ConfigModle.class);
    }

    @Override
    public void onDestroy() {
        if (this instanceof IView) {
            Class<PRESENTER> clazz = ReflectUtil.getClassByT(getClass(), 1);
            SimPManager.unbind(clazz);
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public View loadSuccess() {
        return null;
    }
}
