package com.eshel.ourvisa.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5相关工具类,用来把字符串加密转换成32位的16进制,及MD5码
 */

public class MD5Utils {
	public static String encode(String text){
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			//加密转换
			byte[] digest = md.digest(text.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				int a = b & 0xff;//取低八位, 取正
				String hexString = Integer.toHexString(a);
				if(hexString.length() == 1){
//					hexString = "0"+hexString;
					sb.append("0");
				}
				sb.append(hexString);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return  "00000000000000000000000000000000";
	}

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}
}
