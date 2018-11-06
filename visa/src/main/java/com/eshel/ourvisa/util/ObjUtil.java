package com.eshel.ourvisa.util;

public class ObjUtil {

    public static void checkNull(Object obj){
        if(isNull(obj)){
            throw new NullPointerException("object is null!!!");
        }
    }

    public static boolean notNull(Object value){
        return value != null;
    }

    public static boolean isNull(Object obj){
        return obj == null;
    }

    /**
     * 全部非空
     */
    public static boolean notAllNull(Object... objects){
        if(objects != null){
            for (Object object : objects) {
                if(object == null)
                    return false;
            }
        }
        return true;
    }

    /**
     * 全部为空
     */
    public static boolean isAllNull(Object... objects){
        if(objects!=null){
            for (Object object : objects) {
                if(object != null)
                    return false;
            }
        }
        return true;
    }

    /**
     * 一个为空则为 false
     */
    public static boolean isNull(Object... objects){
        if(objects != null){
            for (Object object : objects) {
                if(object == null)
                    return true;
            }
            return false;
        }
        return true;
    }

    /**
     * 只要 obj 是 clazz 中的任一类型就返回true
     */
    public static boolean instanceOfOne(Object obj, Class... clazz){
        if(obj == null)
            return false;
        if(clazz == null)
            throw new NullPointerException("instanceOf(obj,clazz) clazz can't null !!");
        for (Class c : clazz) {
            if(c != null && c.isInstance(obj)){
                return true;
            }
        }
        return false;
    }
    /**
     * obj 必须是 clazz 中的所有类型才返回true
     * 如 instanceOfAll(activity, Activity.class, Context.class) is true
     */
    public static boolean instanceOfAll(Object obj, Class... clazz){
        if(obj == null)
            return false;
        if(clazz == null)
            throw new NullPointerException("instanceOf(obj,clazz) clazz can't null !!");
        for (Class c : clazz) {
            if(c != null && !c.isInstance(obj)){
                return false;
            }
        }
        return true;
    }

    public static <T> T cast(Object obj){
        return (T) obj;
    }
}
