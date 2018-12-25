package com.eshel.ourvisa.mvp.modles;

import android.content.Context;
import android.content.SharedPreferences;

import com.eshel.ourvisa.VisaApp;
import com.eshel.ourvisa.database.DatabaseHelper;
import com.eshel.ourvisa.mvp.base.DefaultModleCallback;
import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.base.ModleCallback;
import com.eshel.ourvisa.net.api.BaseApi;
import com.eshel.ourvisa.ui.jump.Jump;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import retrofit2.Retrofit;

public class ConfigModle extends Modle<ModleCallback> {

    private volatile Retrofit mRetrofit;
    private volatile BaseApi mApi;
    private volatile Gson mGson;
    private volatile DatabaseHelper mDBHelper;
    private Jump mJump;

    public ConfigModle(ModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {
        mDBHelper = null;
        mRetrofit = null;
        mApi = null;
        mGson = null;
        mJump = null;
    }

    public Context getContext() {
        return VisaApp.getContext();
    }

    public Jump getJump() {
        return mJump;
    }

    public void setJump(Jump jump) {
        mJump = jump;
    }

    public SharedPreferences getUserConfig(){
        return getContext().getSharedPreferences("User",Context.MODE_PRIVATE);
    }

    public DatabaseHelper getDatabaseHelper() {
        if(mDBHelper == null){
            synchronized (DatabaseHelper.class){
                if(mDBHelper == null)
                    mDBHelper = DatabaseHelper.createDatabaseHelper(getContext());
            }
        }
        return mDBHelper;
    }

    public void setDatabaseHelper(DatabaseHelper databaseHelper) {
        mDBHelper = databaseHelper;
    }

    public Gson getGson() {
        if(mGson == null){
            mGson = new Gson();
        }
        return mGson;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public BaseApi getApi() {
        return mApi;
    }

    public void setApi(BaseApi api) {
        mApi = api;
    }

    public Interceptor getHeaderInterceptor(){
        return null;
        /*return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                LoginBean userInfo = UserInfo.getUserInfo();
                Request.Builder builder = chain.request().newBuilder();
                if(UserInfo.checkUserInfo(userInfo))
                    builder.addHeader("token",userInfo.user.token);
                Request request = builder.build();
                return chain.proceed(request);
            }
        };*/
    }

    public VisaApp getApplication(){
        return VisaApp.getContext();
    }

    public long getBaseLoadingTime(){
        return 150;
    }
}
