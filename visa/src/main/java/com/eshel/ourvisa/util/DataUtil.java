package com.eshel.ourvisa.util;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * createBy Eshel
 * createTime: 2017/10/15 10:32
 * desc: 数据转换工具类
 */

public class DataUtil {
	public static  <K, V> Map.Entry<K, V> getTailByReflection(LinkedHashMap<K, V> map)
			throws NoSuchFieldException, IllegalAccessException {
		Field tail = map.getClass().getDeclaredField("tail");
		tail.setAccessible(true);
		return (Map.Entry<K, V>) tail.get(map);
	}

	/**
	 *
	 * @param d
	 * @param useSeparator false "999999" true "999,999"
	 * @return
	 */
	public static String double2Str(Double d, boolean useSeparator) {
		if (d == null) {
			return "";
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(useSeparator);
		return (nf.format(d));
	}

	/**
	 * 保留小数点
	 * @param value 小数
	 * @param format 小数点位数
	 * @return
	 */
	public static float saveD(float value, int format){
		return (float) (Math.round(value* Math.pow(format,format))/ Math.pow(format,format));
	}

	/**
	 * 获取整数位数
	 * @param number
	 * @return
	 */
	public static int getIntLength(int number){
		int count = 1;
		if(number < 0){
			count++;
			number = -number;
		}
		int i = 10;
		for (int j = 1; j <= 10; j++) {
			if(number / i > 0){
				count++;
			}else {
				return count;
			}
			i*=10;
		}
		return count;
	}

	public static boolean isEmpty(Collection collection){
		if(collection == null || collection.size() == 0)
			return true;
		return false;
	}

	public static int getListSize(Collection collection){
		if(collection == null)
			return 0;
		return collection.size();
	}

	public static boolean isNotEmpty(Collection collection){
		return !isEmpty(collection);
	}

	public static double getDoubleFromMap(Map map, Object key){
		return getNumberFromMap(map,key,double.class);
	}

	public static float getFloatFromMap(Map map, Object key){
		return getNumberFromMap(map,key,float.class);
	}

	public static int getIntFromMap(Map map, Object key){
		return getNumberFromMap(map,key,int.class);
	}

	public static<T extends Number> T getNumberFromMap(Map map, Object key, Class<T> clazz){
		if(clazz == null)
			return null;
		if(map == null)
			return (T)caseZero(clazz);
		Object o = map.get(key);
		if(o == null)
			return (T)caseZero(clazz);
		String value = o.toString();
		try {
			if(clazz == Integer.class || clazz == int.class){
				return (T) Integer.valueOf(value);
			}
			if(clazz == Float.class || clazz == float.class)
				return (T) Float.valueOf(value);
			if(clazz == Double.class || clazz == double.class)
				return (T) Double.valueOf(value);
		}catch (Exception e){
			e.printStackTrace();
		}
		return (T)caseZero(clazz);
	}

	private static Number caseZero(Class clazz){
		if(clazz == Integer.class || clazz == int.class){
			return Integer.valueOf(0);
		}
		if(clazz == Float.class || clazz == float.class)
			return Float.valueOf(0);
		if(clazz == Double.class || clazz == double.class)
			return Double.valueOf(0);
		return 0;
	}

	public static boolean isZero(Number number){
		if(number == null)
			return true;
		if(number instanceof Float){
			return number.floatValue() == 0;
		}

		if(number instanceof Double){
			return number.doubleValue() == 0;
		}

		if(number instanceof Integer){
			return number.intValue() == 0;
		}

		if(number instanceof Byte){
			return number.byteValue() == 0;
		}

		if(number instanceof Short){
			return number.shortValue() == 0;
		}

		if(number instanceof Long){
			return number.longValue() == 0;
		}
		return false;
	}

	public static boolean notZero(Number number){
		return !isZero(number);
	}

	public static Integer toInt(Float value) {
		float temp = 0;
		if(value != null)
			temp = value;
		int result = (int) temp;
		return result;
	}
}
