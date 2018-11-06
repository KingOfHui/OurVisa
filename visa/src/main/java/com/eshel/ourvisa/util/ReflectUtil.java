package com.eshel.ourvisa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by guoshiwen on 2017/10/18.
 */

public class ReflectUtil {

	public static int getPublicStaticInt(Class clazz, String fieldName){
		try {
			Field field = clazz.getField(fieldName);
			return field.getInt(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 根据泛型获取 Class
	 * @param clazz 调用地方的 class 对象
	 */
	@SuppressWarnings("unchecked")
	public static<T> Class<T> getClassByT(Class clazz){
		Type type = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[1];
		return (Class<T>) type;
	}

	/**
	 * 使用反射重新设置字段值 例如我要修改 class A 中 field 字段的值:
	 * class A{
	 *     private String field = "1";
	 * }
	 * A a = new A();
	 * ReflectUtil.reSetField(A.class, a, "field", "2");
	 *
	 * @param clazz
	 * @param target
	 * @param fieldName
	 * @param value
	 */
	public static void reSetField(Class clazz, Object target, String fieldName, Object value){
		if(clazz != null){
			try {
				Field field = clazz.getDeclaredField(fieldName);
				if(!Modifier.isPublic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()))
					field.setAccessible(true);
				field.set(target,value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Object getFieldValue(Class clazz, Object target, String fieldName){
		if(clazz != null){
			try {
				Field field = clazz.getDeclaredField(fieldName);
				if(!Modifier.isPublic(field.getModifiers()))
					field.setAccessible(true);
				return field.get(target);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
