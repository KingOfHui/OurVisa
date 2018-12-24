package com.eshel.ourvisa.net.api;

import com.eshel.ourvisa.Constant;
import com.eshel.ourvisa.VisaApp;
import com.eshel.ourvisa.bean.parames.RegisterParams;
import com.eshel.ourvisa.bean.parames.SendSmsCodeParams;
import com.eshel.ourvisa.net.RetrofitFactory;
import com.eshel.ourvisa.bean.parames.LoginParams;
import com.eshel.ourvisa.bean.parames.Params;
import com.eshel.ourvisa.util.Log;
import com.eshel.ourvisa.util.sign.MD5;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ApiWrap {

    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String SEND_SMS_CODE = "getRegSmsCode";
    public static final String REGISTER = "saveRegister";

    /**
     * 登录接口
     */
    public static Call<ResponseBody> login(LoginParams params){
        return RetrofitFactory.getApi().base(buildRequestBody(new Params<>(LOGIN, params)));
    }

    /**
     * 自动登录接口
     */
    public static Call<ResponseBody> autoLogin(String token){
        return RetrofitFactory.getApi().base(buildRequestBody(new Params<>(LOGIN, token)));
    }

    /**
     * 注销接口
     */
    public static Call<ResponseBody> logout(String token){
        return RetrofitFactory.getApi().base(buildRequestBody(new Params<>(LOGOUT, token)));
    }

    /**
     * 发送短信验证码
     */
    public static Call<ResponseBody> sendSmsCode(SendSmsCodeParams params){
        return RetrofitFactory.getApi().base(buildRequestBody(new Params<>(SEND_SMS_CODE, params)));
    }

    /**
     * 注册帐号
     */
    public static Call<ResponseBody> register(RegisterParams params){
        return RetrofitFactory.getApi().base(buildRequestBody(new Params<>(REGISTER, params)));
    }

    private static RequestBody buildRequestBody(Params bean) {
        Gson gson = VisaApp.getContext().getConfig().getGson();
        bean.setSign(MD5.signJson(bean, Constant.SIGN_KEY));
        String json = gson.toJson(bean, Params.class);
        Log.log(json);
        return RequestBody.create(MediaType.parse(Constant.MEDIA_TYPE_JSON), json);
    }
}
