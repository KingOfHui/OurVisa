package com.eshel.ourvisa.mvp.base;

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

    private M initModle(){
        //获取泛型 Class
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Class<M> tClass = (Class<M>)type;

        Type typeModleCallback = ((ParameterizedType) tClass.getGenericSuperclass()).getActualTypeArguments()[0];
        Class<M> typeModleClass = (Class<M>)typeModleCallback;

        try {
            Constructor<M> constructor = tClass.getConstructor(typeModleClass);
            ModleCallback callback = null;
            if(this instanceof ModleCallback)
                callback = (ModleCallback) this;
            return constructor.newInstance(callback);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Throwable e){
            e.printStackTrace();
        }
        return null;
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
