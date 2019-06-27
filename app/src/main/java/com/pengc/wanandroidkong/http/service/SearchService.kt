package com.pengc.wanandroidkong.http.service

import com.pengc.wanandroidkong.bean.MainListData
import com.pengc.wanandroidkong.http.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface SearchService {

    @FormUrlEncoded
    @POST("/article/query/{pageNum}/json")
    fun querySearch(@Path("pageNum") pageNum:Int=0,@Field("k") searchKey:String = ""):Observable<BaseResponse<MainListData>>

}