/*
package com.eshel.ourvisa.util.sign;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.eshel.ourvisa.util.StringUtils;

*/
/**
* 功能：支付宝MD5签名处理核心文件，不需要修改
* 版本：3.3
* 修改日期：2012-08-17
* 说明：
* 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
* 该代码仅供学习和研究支付宝接口使用，只是提供一个
* *//*


public class MD5 {

    */
/**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     *//*

    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    */
/**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     *//*

    public static boolean verify(String text, String sign, String key, String input_charset) {
    	text = text + key;
    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
    	if(mysign.equals(sign)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    */
/**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     *//*

    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    public static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    public static final String MD5_NAME = "MD5";
    
    */
/**
     * 生成MD5摘要信息，目前该方法微信支付使用
     * @param buffer
     * @return
     *//*

    public final static String getMessageDigest(byte[] buffer) {
		try {
			MessageDigest mdTemp = MessageDigest.getInstance(MD5_NAME);
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
    */
/**
     * 签名json数据
     * @param json 输入数据
     * @param key 给定的key
     * @return 返回签名后的字符串
     *//*

    public static String signJson(JSONObject json, String key){
    	if(json == null){
    		return null;
    	}
    	
    	//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = paraFilter((Map<String, Object>)json);
    	
        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew);
        
    	return MD5.sign(preSignStr, key, StringUtils.DEFAULT_CHARSET_UTF8);
    }
    
    */
/**
     * 验证签名过的json数据
     * @param json 待验证的json
     * @param sign 签名过的数据
     * @param md5Key 给定的key
     * @return 返回结果，true正确，false失败
     *//*

    public static boolean verifyJson(JSONObject json, String sign, String md5Key){
    	if(json == null){
    		return false;
    	}
    	return getMd5SignVeryfy((Map<String, Object>)json, sign, md5Key);
    }
    
	*/
/**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     *//*

	public static boolean getMd5SignVeryfy(Map<String, Object> Params, String sign, String md5Key) {
    	//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = paraFilter(Params);
        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew);
        //获得签名验证结果
        return MD5.verify(preSignStr, sign, md5Key, StringUtils.DEFAULT_CHARSET_UTF8);
    }
	
	*/
/**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     *//*

    private static Map<String, String> paraFilter(Map<String, Object> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        String value = null;
        for (String key : sArray.keySet()) {
            value = sArray.get(key).toString();
            if (StringUtils.isEmpty(value) || key.equalsIgnoreCase(NAME_SIGN_2)
                || key.equalsIgnoreCase(NAME_SIGN_TYPE)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    
    */
/**
     * 把数组所有元素，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要参与字符拼接的参数组
     * @param sorts   是否需要排序 true 或者 false
     * @return 拼接后字符串
     *//*

    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuilder result = new StringBuilder();
        String key = null;
        String value = null;
        for (int i = 0; i < keys.size(); i++) {
            key = keys.get(i);
            value = params.get(key);
            if(i > 0){
            	result.append(StringUtils.CHAR_AND);
            }
            result.append(key).append(StringUtils.CHAR_EQUALS).append(value);
        }
        return result.toString();
    }
    
    public static final String NAME_SIGN_2 = "sign";
    private static final String NAME_SIGN_TYPE = "sign_type";
    
    public static void main(String[] args){
    	String key = "123456";
    	JSONObject input = new JSONObject();
    	input.put("name", "Mike");
    	input.put("age", 30);
    	input.put("amount", 600.2);
    	
    	String signStr = signJson(input, key);
    	System.out.println("签名内容 = " + signStr);
    	System.out.println("验证签名 = " + verifyJson(input, signStr, key));
    }
}*/
