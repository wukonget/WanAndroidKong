package com.pengc.wanandroidkong.http.service

import com.pengc.wanandroidkong.bean.TodoResp
import com.pengc.wanandroidkong.bean.TodoRespSection
import com.pengc.wanandroidkong.http.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface TodoService {

    @POST("/lg/todo/listnotdo/{type}/json/{pageNum}")
    fun getUndoneData(@Path("type") type:String="0",@Path("pageNum") pageNum:Int=0): Observable<BaseResponse<TodoRespSection>>


    @POST("/lg/todo/listdone/{type}/json/{pageNum}")
    fun getDoneData(@Path("type") type:String="0",@Path("pageNum") pageNum:Int=0): Observable<BaseResponse<TodoRespSection>>

    @FormUrlEncoded
    @POST("/lg/todo/v2/list/{pageNum}/json")
    fun getTodoData(
        @Path("pageNum") pageNum:Int=0,//页码
        @Field("status") status :Int? = null,//状态， 1-完成；0未完成; 默认全部展示；
        @Field("type") type :Int? = null,//创建时传入的类型, 默认全部展示
        @Field("priority") priority :Int? = null,//创建时传入的优先级；默认全部展示
        @Field("orderby") orderby :Int = 4//1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；
    ): Observable<BaseResponse<TodoResp>>

    @FormUrlEncoded
    @POST("/lg/todo/add/json")
    fun addTodoData(
        @Field("title") title :String,
        @Field("content") content :String,
        @Field("date") date :String,
        @Field("type") type :Int = 0,
        @Field("priority") priority :Int = 0
    ): Observable<BaseResponse<String>>

    @FormUrlEncoded
    @POST("/lg/todo/update/{id}/json")
    fun updateTodoData(
        @Path("id") id :Long,
        @Field("title") title :String,
        @Field("content") content :String,
        @Field("date") date :String,
        @Field("type") type :Int = 0,
        @Field("priority") priority :Int = 0
    ): Observable<BaseResponse<String>>

    @FormUrlEncoded
    @POST("/lg/todo/done/{id}/json")
    fun changeStatus(
        @Path("id") id :Long,
        @Field("status") status :Int
    ): Observable<BaseResponse<String>>

    @POST("/lg/todo/delete/{id}/json")
    fun deleteTodoData(
        @Path("id") id :Long
    ): Observable<BaseResponse<String>>

}