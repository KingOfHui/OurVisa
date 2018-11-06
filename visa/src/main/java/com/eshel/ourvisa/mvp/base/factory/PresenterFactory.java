package com.eshel.ourvisa.mvp.base.factory;


import com.eshel.ourvisa.mvp.base.Presenter;
import com.eshel.ourvisa.util.ObjUtil;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class PresenterFactory {
    private static final int capacity = 15;
    private static HashMap<Class<?extends Presenter>,Presenter> foregroundPresenters;

    public static <P extends Presenter> P getPresenter(Class<P> pClass){
        ObjUtil.checkNull(pClass);
        if(foregroundPresenters == null){
            synchronized (PresenterFactory.class){
                if(foregroundPresenters == null){
                    foregroundPresenters = new HashMap<>(capacity);
                }
            }
        }

        Presenter presenter = foregroundPresenters.get(pClass);
        if(presenter == null){
            try {
                presenter = createPresenter(pClass);
                foregroundPresenters.put(pClass, presenter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (P) presenter;
    }

    public static void closePresenter(Class<? extends Presenter> pClass){
        if(foregroundPresenters != null){
            Presenter remove = foregroundPresenters.remove(pClass);
            if(remove != null){
                remove.close();
            }
        }
    }

    public static void clearForegroundPresenters(){
        if(foregroundPresenters != null)
            foregroundPresenters.clear();
        foregroundPresenters = null;
    }

    private static Presenter createPresenter(Class<? extends Presenter> pClass) throws Exception {
        Constructor constructor = pClass.getConstructor();
        Object presenter = constructor.newInstance();
        return (Presenter) presenter;
    }
}
