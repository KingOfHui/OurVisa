package com.eshel.ourvisa.mvp.base;

import com.eshel.ourvisa.mvp.base.factory.ModleFactory;
import com.eshel.ourvisa.util.ReflectUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class Presenter<View extends IView, M extends Modle> implements IPresenter{

    protected View mView;
    protected M mDatas;

    public Presenter() {}

    /**
     * 必须首先被调用.
     * 在使用Presenter之前, 必须使用 {@link com.eshel.ourvisa.mvp.base.factory.SimPManager#bind(Class, IView)} 绑定 V 和 P
     */
    public final void bind(View view){
        this.mView = view;
        mDatas = initModle();
    }

    protected M initModle(){
        //获取泛型 Class

        Class<M> modleClass = ReflectUtil.getClassByT(getClass(), 1);
        return ModleFactory.createModle(modleClass, this);
    }

//    public abstract ModleCallback getModleCallback();

    /**
     * 在 Presenter 不需要调用的时候.
     * 必须使用 {@link com.eshel.ourvisa.mvp.base.factory.SimPManager#unbind(Class)} 解除 Presenter 的引用
     */
    @Override
    public void close(){
        if(mDatas != null) mDatas.close();
        onClose();
        mDatas = null;
        mView = null;
    }

    protected abstract void onClose();

}
