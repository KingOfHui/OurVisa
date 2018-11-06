package com.eshel.ourvisa.util;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;


import com.eshel.ourvisa.BaseApplication;
import com.eshel.ourvisa.R;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public final static String UTF_8 = "utf-8";

	public static String timeFormat(long ago){
		BaseApplication context = BaseApplication.getContext();
		String afterTime = context.getString(R.string.unknown);
		long time = System.currentTimeMillis() - ago;
		time /= 1000;
		if(time < 0){
			return afterTime;
		}
		// xxx 前
		if(time < 60){
			afterTime = time+context.getString(R.string.s_ago);
		}else {
			time /= 60;
			if(time < 60){
				afterTime = time+context.getString(R.string.m_ago);
			}else {
				time /= 60;
				if(time < 24) {
					afterTime = time + context.getString(R.string.h_ago);
				} else{
					time /= 24;
					if(time < 30){
						afterTime = time + context.getString(R.string.day_ago);
					}else {
						time /= 30;
						if(time < 12)
							afterTime = time + context.getString(R.string.month_ago);
						else {
							time /= 12;
							afterTime = time+context.getString(R.string.year_ago);
						}
					}
				}
			}
		}
		return afterTime;
	}

	/** 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false */
	public static boolean isEmpty(String value) {
		if (value != null && !"".equalsIgnoreCase(value.trim())
				&& !"null".equalsIgnoreCase(value.trim())) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isEmpty(CharSequence value) {
		if (value != null && value.length() != 0 && !"".equalsIgnoreCase(value.toString().trim())
				&& !"null".equalsIgnoreCase(value.toString().trim())) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNotEmpty(CharSequence input) {
		return !isEmpty(input);
	}

	/**
	 * 判段是否全部非空
	 * @param value
	 * @return
	 */
	public static boolean isAllNotEmpty(String... value){
		if(value != null){
			for (String v : value) {
				boolean empty = isEmpty(v);
				if(empty)
					return false;
			}
			return true;
		}
		return false;
	}

	/** 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true */
	public static boolean isEquals(String... agrs) {
		String last = null;
		for (int i = 0; i < agrs.length; i++) {
			String str = agrs[i];
			if (isEmpty(str)) {
				return false;
			}
			if (last != null && !str.equalsIgnoreCase(last)) {
				return false;
			}
			last = str;
		}
		return true;
	}

	/**
	 * 返回一个高亮spannable
	 * 
	 * @param content
	 *            文本内容
	 * @param color
	 *            高亮颜色
	 * @param start
	 *            起始位置
	 * @param end
	 *            结束位置
	 * @return 高亮spannable
	 */
	public static CharSequence getHighLightText(String content, int color,
                                                int start, int end) {
		if (TextUtils.isEmpty(content)) {
			return "";
		}
		start = start >= 0 ? start : 0;
		end = end <= content.length() ? end : content.length();
		SpannableString spannable = new SpannableString(content);
		CharacterStyle span = new ForegroundColorSpan(color);
		spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;
	}
	public static CharSequence getColorText(String content, int color){
		return getHighLightText(content,color,0,content.length());
	}

	/**
	 * 获取链接样式的字符串，即字符串下面有下划线
	 * 
	 * @param resId
	 *            文字资源
	 * @return 返回链接样式的字符串
	 */
	public static Spanned getHtmlStyleString(int resId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"\"><u><b>").append(BaseApplication.getContext().getString(resId))
				.append(" </b></u></a>");
		return Html.fromHtml(sb.toString());
	}

	/** 格式化文件大小，不保留末尾的0 */
	public static String formatFileSize(long len) {
		return formatFileSize(len, false);
	}

	/** 格式化文件大小，保留末尾的0，达到长度一致 */
	public static String formatFileSize(long len, boolean keepZero) {
		String size;
		DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
		DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
		if (len < 1024) {
			size = String.valueOf(len + "B");
		} else if (len < 10 * 1024) {
			// [0, 10KB)，保留两位小数
			size = String.valueOf(len * 100 / 1024 / (float) 100) + "KB";
		} else if (len < 100 * 1024) {
			// [10KB, 100KB)，保留一位小数
			size = String.valueOf(len * 10 / 1024 / (float) 10) + "KB";
		} else if (len < 1024 * 1024) {
			// [100KB, 1MB)，个位四舍五入
			size = String.valueOf(len / 1024) + "KB";
		} else if (len < 10 * 1024 * 1024) {
			// [1MB, 10MB)，保留两位小数
			if (keepZero) {
				size = String.valueOf(formatKeepTwoZero.format(len * 100 / 1024
						/ 1024 / (float) 100))
						+ "MB";
			} else {
				size = String.valueOf(len * 100 / 1024 / 1024 / (float) 100)
						+ "MB";
			}
		} else if (len < 100 * 1024 * 1024) {
			// [10MB, 100MB)，保留一位小数
			if (keepZero) {
				size = String.valueOf(formatKeepOneZero.format(len * 10 / 1024
						/ 1024 / (float) 10))
						+ "MB";
			} else {
				size = String.valueOf(len * 10 / 1024 / 1024 / (float) 10)
						+ "MB";
			}
		} else if (len < 1024 * 1024 * 1024) {
			// [100MB, 1GB)，个位四舍五入
			size = String.valueOf(len / 1024 / 1024) + "MB";
		} else {
			// [1GB, ...)，保留两位小数
			size = String.valueOf(len * 100 / 1024 / 1024 / 1024 / (float) 100)
					+ "GB";
		}
		return size;
	}
	/**
	 * 验证邮箱
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email){
		boolean flag = false;
		try{
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}

	/**
	 * 重设字体大小
	 * @param text
	 * @param start
	 * @param end
	 * @param sizeSp
	 * @return
	 */
	public static CharSequence resetTextSize(CharSequence text, int start, int end, int sizeSp){
		SpannableStringBuilder ssb;
		if(text instanceof SpannableString)
			ssb = (SpannableStringBuilder) text;
		else
			ssb = new SpannableStringBuilder(text);
		ssb.setSpan(new AbsoluteSizeSpan(DensityUtil.dp2px(sizeSp)),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ssb;
	}



	public static String priceFormat(float price){
		String priceS = String.valueOf(price);
//		int first = priceS.split(".")[0].length() % 3;
		StringBuilder sb = new StringBuilder(priceS);
		String[] split = priceS.split("\\.");
		for (int i = split[0].length() - 3; i > 0; i-=3) {
			if(i > 0)
				sb.insert(i,',');
		}
		return sb.toString();
	}
}
