package com.pengc.wanandroidkong.http.service

import com.pengc.wanandroidkong.bean.LoginInfo
import com.pengc.wanandroidkong.http.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username") userName:String = "",@Field("password") password:String = ""):Observable<BaseResponse<LoginInfo>>

    @FormUrlEncoded
    @POST("/user/register")
    fun register(@Field("username") userName:String = "",@Field("password") password:String = "",@Field("repassword") repassword:String = ""):Observable<BaseResponse<String>>

    @GET("/user/logout/json")
    fun logout():Observable<BaseResponse<String>>

}