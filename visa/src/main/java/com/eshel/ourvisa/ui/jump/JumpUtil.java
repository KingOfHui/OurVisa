package com.eshel.ourvisa.ui.jump;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.eshel.ourvisa.VisaApp;
import com.eshel.ourvisa.mvp.modles.ConfigModle;
import com.eshel.ourvisa.ui.jump.anno.IntentParser;
import com.eshel.ourvisa.util.ObjUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

public class JumpUtil {

    public static Jump getJump(){
        ConfigModle config = VisaApp.getContext().getConfig();
        if(config.getJump() == null){
            Jump jump = create(Jump.class);
            config.setJump(jump);
        }
        return config.getJump();
    }

    public static<T> T create(Class<T> clazz){
        checkNull(clazz);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new JumpInvokeHandler());
    }

    public static void parseIntent(Object target, @NonNull Intent intent){
        parseIntent(0, target, intent);
    }

    public static void parseIntent(int id, Object target, @NonNull Intent intent){
        checkNull(target);
        Class<?> clazz = target.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        Method targetMethod = null;
        IntentParser targetAnno = null;
        for (Method method : methods) {
//            if(!Modifier.isPublic(method))
            IntentParser annotation = method.getAnnotation(IntentParser.class);
            if(annotation == null)
                continue;
            if(id != annotation.id())
                continue;
            targetMethod = method;
            targetAnno = annotation;
            break;
        }

        if(targetMethod == null){
            new Exception(JumpException.JumpExpType.NoneIntentParser.message).printStackTrace();
            return;
        }

        if(!Modifier.isPublic(targetMethod.getModifiers()))
            targetMethod.setAccessible(true);

        if(targetAnno.intentType() == IntentType.Intent){
            if(intent == null){
                new JumpException(JumpException.JumpExpType.InvokeParseIntentIsNull).printStackTrace();
                return;
            }
            // TODO: 2018/12/25 0025
        }else if(targetAnno.intentType() == IntentType.MemoryIntent){
            MemoryIntent intentM = MemoryIntent.getIntent((Class<? extends Activity>) target.getClass());
            // TODO: 2018/12/25 0025  
        }
    }

    public static Activity getActivity(Context context) {
        // Gross way of unwrapping the Activity so we can get the FragmentManager
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    private static void checkNull(Object obj){
        if(isNull(obj)){
            throw new NullPointerException("object is null!!!");
        }
    }

    private static boolean isNull(Object obj){
        return obj == null;
    }
}
