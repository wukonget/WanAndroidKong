package com.pengc.wanandroidkong.http.service

import com.pengc.wanandroidkong.bean.NavTypeBean
import com.pengc.wanandroidkong.http.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface NavService {

    @GET("/navi/json")
    fun getNavData(): Observable<BaseResponse<List<NavTypeBean>>>

}