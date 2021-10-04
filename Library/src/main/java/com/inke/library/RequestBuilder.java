package com.inke.library;

import androidx.annotation.Nullable;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 请求构建类
 */
public class RequestBuilder {

    //方法的请求方式（"GET", "POST"）
    private final String method;
    //接口请求地址
    private final HttpUrl baseUrl;
    //方法的注解的值("/ip/ipNew")
    private String relativeUrl;
    //请求url构建者(构建完整请求url)
    private HttpUrl.Builder urlBuilder;
    //Form表单构建者
    private FormBody.Builder formBuilder;
    //构建完整请求（包含url、method、body）
    private final Request.Builder requestBuilder;

    RequestBuilder(String method, HttpUrl baseUrl, @Nullable String relativeUrl, boolean hasBody) {
        this.method = method;
        this.baseUrl = baseUrl;
        this.relativeUrl = relativeUrl;

        //初始化请求
        requestBuilder = new Request.Builder();
        //根据是否有请求体实例化Form表单构建者
        if(hasBody) formBuilder = new FormBody.Builder();
    }

    //拼接Query参数
    void addQueryParam(String name, String value) {
        if(relativeUrl != null) {
            //baseUrl + 方法注解中url
            urlBuilder = baseUrl.newBuilder(relativeUrl);
            if(urlBuilder == null) {
                throw new IllegalArgumentException(
                        "Malformed URL. Base: " + baseUrl + ", Relative: " + relativeUrl);
            }
            //每次请求都实例化了一次，重置
            relativeUrl = null;
        }
        urlBuilder.addQueryParameter(name, value);
    }

    //拼接Field参数
    void addFormField(String name, String value) { formBuilder.add(name, value);}

    Request build() {
        //定义局部变量。1、保证每次值不一样。2、易回收
        HttpUrl url;
        if(urlBuilder != null) {
            url = urlBuilder.build();
        } else {
            url = baseUrl.resolve(relativeUrl);
            if(url == null) {
                throw new IllegalArgumentException("Malformed URL. Base: " + baseUrl + ", Relative: " + relativeUrl);
            }
        }

        //如果有请求体，构造方法中会初始化Form变淡构建者，然后再实例化请求体
        RequestBody body = null;
        if(formBuilder != null) {
            body = formBuilder.build();
        }

        //构建完整请求
        return requestBuilder
                .url(url)
                .method(method, body)
                .build();
    }


}
