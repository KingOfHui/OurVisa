package com.eshel.ourvisa.mvp.base;

public abstract class Modle<Callback extends ModleCallback> implements IModle{
    protected Callback mCallback;

    /*
     * 继承 Modle, 必须重写该构造方法
     */
    public Modle(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void close() {
        mCallback = null;
        onClose();
    }

    protected abstract void onClose();
}
