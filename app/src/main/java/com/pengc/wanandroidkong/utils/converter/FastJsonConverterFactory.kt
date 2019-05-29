package com.pengc.wanandroidkong.utils.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class FastJsonConverterFactory : Converter.Factory() {

    companion object {
        fun create(): FastJsonConverterFactory {
            return FastJsonConverterFactory()
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return FastJsonResponseConverter<JvmType.Object>(type)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return FastJsonRequestConverter<JvmType.Object>()
    }
}