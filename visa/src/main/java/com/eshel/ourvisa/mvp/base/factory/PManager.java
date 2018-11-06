package com.eshel.ourvisa.mvp.base.factory;


import com.eshel.ourvisa.mvp.base.IView;
import com.eshel.ourvisa.mvp.base.Presenter;

/**
 * todo 通过 PresenterManager 获取 Presenter  通过注解处理器自动生成
 */
public class PManager {

    @SuppressWarnings("unchecked")
    public static void bind(Class<? extends Presenter> pClass, IView view){
        getPresenter(pClass).bind(view);
    }

    public static void unbind(Class<? extends Presenter> pClass){
        PresenterFactory.closePresenter(pClass);
    }

    public static<P extends Presenter> P getPresenter(Class<P> pClass){
        return PresenterFactory.getPresenter(pClass);
    }
}
