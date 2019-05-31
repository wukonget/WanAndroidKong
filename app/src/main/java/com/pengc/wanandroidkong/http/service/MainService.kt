package com.pengc.wanandroidkong.http.service

import com.pengc.wanandroidkong.bean.BannerBean
import com.pengc.wanandroidkong.bean.HotSearchKey
import com.pengc.wanandroidkong.bean.MainListData
import com.pengc.wanandroidkong.bean.TixiBean
import com.pengc.wanandroidkong.http.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainService {

    @GET("/banner/json")
    fun getBanners(): Observable<BaseResponse<List<BannerBean>>>

    @GET("/article/list/{pageNum}/json")
    fun getMainList(@Path("pageNum") pageNum:Int=0):Observable<BaseResponse<MainListData>>

    @GET("/hotkey/json")
    fun getSearchKeys():Observable<BaseResponse<List<HotSearchKey>>>

    @GET("/tree/json")
    fun getTixi():Observable<BaseResponse<List<TixiBean>>>

    @GET("/article/list/{pageNum}/json")
    fun getTixiDataList(@Path("pageNum") pageNum:Int=0,@Query("cid") cid:Int = -1):Observable<BaseResponse<MainListData>>

}