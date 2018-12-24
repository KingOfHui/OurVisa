package com.eshel.ourvisa.net.api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BaseApi {
    @POST("/m/p/stream.do")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<ResponseBody> base(@Body RequestBody body);
}
