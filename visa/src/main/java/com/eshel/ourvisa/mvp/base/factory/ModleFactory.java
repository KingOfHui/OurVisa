package com.eshel.ourvisa.mvp.base.factory;

import android.os.Build;
import android.telecom.Call;
import android.util.ArrayMap;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.base.ModleCallback;
import com.eshel.ourvisa.util.ObjUtil;
import com.eshel.ourvisa.util.ReflectUtil;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ModleFactory {

    public static Map<Class, Modle> modles;

    private static Map<Class, Modle> getModles(){

        if(modles == null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                modles = new ArrayMap<>();
            }else {
                modles = new HashMap<>();
            }
        }

        return modles;
    }

    public static<T extends Modle> T createModle(Class<T> clazz, ModleCallback callback){
        Class typeModleClass = ReflectUtil.getClassByT(clazz, 0);

        try {
            Constructor constructor = clazz.getConstructor(typeModleClass);
            return (T) constructor.newInstance(callback);
        } catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Modle> T createModle(Class<T> clazz, Object modleCallback){
        ModleCallback callback = null;
        if(modleCallback instanceof ModleCallback)
            callback = (ModleCallback) modleCallback;
        return createModle(clazz, callback);
    }

    public static<T extends Modle> T getModle(Class<T> clazz){
        ObjUtil.checkNull(clazz);
        Modle modle = getModles().get(clazz);
        if(modle == null) {
            modle = createModle(clazz, null);
            getModles().put(clazz, modle);
        }
        return (T) modle;
    }
}
