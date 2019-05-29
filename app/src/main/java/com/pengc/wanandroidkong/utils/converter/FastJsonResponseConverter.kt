package com.pengc.wanandroidkong.utils.converter

import com.alibaba.fastjson.JSON
import okhttp3.ResponseBody
import okio.BufferedSource
import okio.Okio
import retrofit2.Converter
import java.lang.reflect.Type

class FastJsonResponseConverter<T>(var type: Type) : Converter<ResponseBody,T> {
    override fun convert(value: ResponseBody): T {
        val bufferedSource: BufferedSource = Okio.buffer(value.source())
        val tempStr = bufferedSource.readUtf8()
        bufferedSource.close()
        return JSON.parseObject(tempStr,type)
    }
}