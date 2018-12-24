package com.eshel.ourvisa.util;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5相关工具类,用来把字符串加密转换成32位的16进制,及MD5码
 */

public class MD5Utils {

	//签名内容 = 3d4d18461ebd93e20f8d73c888b94ee9
	//验证签名 = true
	public static void main(String args[]){
		String text = "age=30&amount=600.2&name=Mike";
		String key = "123456";
		text+=key;
		System.out.println(encode(text));
	}

	public static String encode(String text){
		return encode(text, "UTF-8");
	}

	public static String encode(String text, String charsetName){
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			//加密转换
			byte[] digest = md.digest(text.getBytes(charsetName));
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
		} catch (UnsupportedEncodingException e) {
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
