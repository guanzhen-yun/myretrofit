package com.inke.myretrofit;


import com.inke.library.Retrofit;
import com.inke.library.http.Field;
import com.inke.library.http.GET;
import com.inke.library.http.POST;
import com.inke.library.http.Query;

import org.junit.Test;

import okhttp3.Call;
import okhttp3.Response;

public class NewRetrofitUnitTest {

    private final static String IP = "144.34.161.97";
    private final static String KEY = "aa205eeb45aa76c6afe3c52151b52160";
    private final static String BASE_URl = "http://apis.juhe.cn/";

    //聚合ip查询接口
    interface HOST {
        @GET("/ip/ipNew")
        Call get(@Query("ip") String ip, @Query("key") String key);

        @POST("/ip/ipNew")
        Call post(@Field("ip") String ip, @Field("key") String key);
    }

    @Test
    public void testMyRetrofit() throws Exception {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URl).build();
        HOST host = retrofit.create(HOST.class);
        //Retrofit GET同步请求
        {
            Call call = host.get(IP, KEY);

            Response response = call.execute();
            if(response != null && response.body() != null) {
                System.out.println("Retrofit GET同步请求 >>> " + response.body().string());
            }
        }

        //Retrofit POST同步请求
        {
            Call call = host.post(IP, KEY);

            Response response = call.execute();
            if(response != null && response.body() != null) {
                System.out.println("Retrofit POST同步请求 >>> " + response.body().string());
            }
        }
    }
}
