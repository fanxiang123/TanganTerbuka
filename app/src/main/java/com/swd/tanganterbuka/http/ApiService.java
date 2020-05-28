package com.swd.tanganterbuka.http;

import com.swd.tanganterbuka.modle.BaseResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface  ApiService {

    /**
     * 获取guid
     * @return
     */
    @POST("papi/device/guid")
    Call<String> getGuId(@Body RequestBody  body);


    /**
     * 上传所有包名
     * @return
     */
    @POST("papi/device/pkglist")
    Call<String> upApps(@Body RequestBody  body);


    /**
     * 便利列表
     * @return
     */
    @POST("papi/offers/getlists")
    Call<String> listApps(@Body RequestBody  body);

    /**
     * 详情
     * @return
     */
    @POST("papi/offers/infos")
    Call<String> appsInfo(@Body RequestBody  body);


    /**
     * 获取验证码
     * @return
     */
    @POST("api/request/user/reg_code_sms")
    Call<String> getCode(@Body RequestBody  body);


    /**
     * 短信登录
     * @return
     */
    @POST("papi/users/smslogin")
    Call<String> login(@Body RequestBody  body);

}
