package com.inke.myretrofit;


import org.junit.Test;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class RetrofitUnitTest {

    private final static String IP = "144.34.161.97";
    private final static String KEY = "aa205eeb45aa76c6afe3c52151b52160";
    private final static String BASE_URl = "http://apis.juhe.cn/";

    //聚合ip查询接口
    interface HOST {
        @GET("/ip/ipNew")
        Call<ResponseBody> get(@Query("ip") String ip, @Query("key") String key);

        @POST("/ip/ipNew")
        @FormUrlEncoded
        Call<ResponseBody> post(@Field("ip") String ip, @Field("key") String key);
    }

    @Test
    public void retrofit2() throws IOException {
        // --------------------------Retrofit2--------------------
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URl)
                // .callFactory(okHttpClient) //自己实现和配置
                .build();
        HOST host = retrofit.create(HOST.class);

        //Retrofit GET同步请求
        {
            Call<ResponseBody> call = host.get(IP, KEY);

            retrofit2.Response<ResponseBody> response = call.execute();
            if(response != null && response.body() != null) {
                System.out.println("Retrofit GET同步请求 >>> " + response.body().string());
            }
        }

        //Retrofit POST同步请求
        {
            Call<ResponseBody> call = host.post(IP, KEY);

            retrofit2.Response<ResponseBody> response = call.execute();
            if(response != null && response.body() != null) {
                System.out.println("Retrofit POST同步请求 >>> " + response.body().string());
            }
        }
    }
}
