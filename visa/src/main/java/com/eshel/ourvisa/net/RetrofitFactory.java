package com.eshel.ourvisa.net;

import com.eshel.ourvisa.BaseApplication;
import com.eshel.ourvisa.VisaApp;
import com.eshel.ourvisa.mvp.modles.ConfigModle;
import com.eshel.ourvisa.net.api.BaseApi;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitFactory {

	public static Retrofit getRetrofit(){
		ConfigModle config = VisaApp.getContext().getConfig();
		if(config.getRetrofit() == null){
			synchronized (RetrofitFactory.class){
				if(config.getRetrofit() == null){
					OkHttpClient client;

					OkHttpClient.Builder builder = new OkHttpClient.Builder()
							.connectTimeout(10000L, TimeUnit.MILLISECONDS)       //设置连接超时
							.readTimeout(10000L, TimeUnit.MILLISECONDS)          //设置读取超时
							.writeTimeout(10000L, TimeUnit.MILLISECONDS)         //设置写入超时
							.cache(new Cache(BaseApplication.getContext().getCacheDir(), 10 * 1024 * 1024))   //设置缓存目录和10M缓存
							.addInterceptor(new LoggingInterceptor());

					Interceptor headerInterceptor = config.getHeaderInterceptor();
					if(headerInterceptor != null)
						builder.addInterceptor(headerInterceptor);

					client = builder.build();

					Retrofit retrofit = new Retrofit.Builder()
						.client(client)
						.baseUrl(Url.baseUrl)
						.build();

					config.setRetrofit(retrofit);
				}
			}
		}
		return config.getRetrofit();
	}

	public static BaseApi getApi(){
		ConfigModle config = VisaApp.getContext().getConfig();
		if(config.getApi() == null) {
			BaseApi api = getRetrofit().create(BaseApi.class);
			config.setApi(api);
		}
		return config.getApi();
	}
}
