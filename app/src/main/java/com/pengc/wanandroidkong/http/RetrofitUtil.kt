package com.pengc.wanandroidkong.http

import com.pengc.wanandroidkong.utils.StringUtil
import com.pengc.wanandroidkong.utils.converter.FastJsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

class RetrofitUtil {

    companion object {

        val logger: Logger = Logger.getLogger("RetrofitUtil")

        private var httpLogger = MyHttpLogger()
        private var mOkHttpClient: OkHttpClient? = null

        private fun buildRetrofit(baseUrl: String = Api.BASE_URL): Retrofit {
            val builder = Retrofit.Builder()
                .client(getSingleOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            return builder.build()

        }

        fun <T> create(c: Class<T>): T {
            return buildRetrofit().create(c)
        }

        private fun getSingleOkHttpClient(): OkHttpClient {
            if (mOkHttpClient == null) {
                mOkHttpClient = buildClient()
            }
            return mOkHttpClient!!
        }

        private fun buildClient(): OkHttpClient {
            val logInterceptor = HttpLoggingInterceptor(httpLogger)
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClientBuilder = OkHttpClient().newBuilder()
                .addNetworkInterceptor(logInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
            return okHttpClientBuilder.build()
        }

        private class MyHttpLogger : HttpLoggingInterceptor.Logger {
            internal var mMessage = StringBuilder()

            override fun log(msg: String) {
                var message = msg
                // 打印请求或者响应报文中的每一行都会调用此方法，所以用一个StringBuilder把它们串起来

                // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
                val isJsonStr =
                    message.startsWith("{") && message.endsWith("}") || message.startsWith("[") && message.endsWith("]")
                if (isJsonStr) {
                    message = StringUtil.formatJson(message)
                }
                mMessage.append(message + "\n")
                // 响应结束，打印整条日志
                if (message.startsWith("<-- END HTTP")) {
                    logger.info(mMessage.toString())
                    logger.info("我是小尾巴")
                    mMessage.setLength(0)
                }
            }
        }
    }
}