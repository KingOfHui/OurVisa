package com.eshel.ourvisa.ui.jump;

import android.app.Activity;
import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * Intent 的替代品, 参数将存储在内存
 */
public class MemoryIntent {
    private static Map<Class<? extends Activity>, MemoryIntent> intents;
    static {
        intents = new ArrayMap<>();
    }
    public MemoryIntent(){
        mDatas = new ArrayMap<>();
    }
    public static MemoryIntent getIntent(Class<? extends Activity> key){
        return intents.get(key);
    }

    public static void sendIntent(Class<? extends Activity> key, MemoryIntent intent){
        intents.put(key, intent);
    }

    private Map<String,Object> mDatas;

    public void save(String key, Object value){
        if(mDatas != null && key != null){
            mDatas.put(key,value);
        }
    }

    public<T> T load(String key, Class<T> type){
        try {
            if(key != null){
                Object value = mDatas.get(key);
                if(value != null) {
                    return (T) value;
                }
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载并移除
     */
    public <T> T loadOnce(String key, Class<T> type){
        try {
            if(key != null){
                Object value = mDatas.remove(key);
                if(value != null)
                    return (T) value;
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }
}
