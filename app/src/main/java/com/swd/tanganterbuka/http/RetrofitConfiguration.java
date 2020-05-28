package com.swd.tanganterbuka.http;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfiguration {
    private static final String BASE_URL = "https://papi.tanganterbuka.com/";
    private static ApiService service;
    private static Retrofit retrofit;

    public static ApiService  getService(){

        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor())// 在此处添加拦截器即可，默认日志级别为BASIC
                    //添加统一的请求头
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            // 以拦截到的请求为基础创建一个新的请求对象，然后插入Header
                            Request request = chain.request().newBuilder()
                                    .addHeader("Content-Type", "text/plain")
                                    .build();
                            // 开始请求
                            return chain.proceed(request);
                        }
                    })
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())//解析方法
                    .baseUrl(BASE_URL)//主机地址
                    .build();
        }
        //2.创建访问API的请求
        if (service ==null)
            service = retrofit.create(ApiService.class);
        return service;
    }

}
