package com.pengc.wanandroidkong.http

import com.pengc.wanandroidkong.utils.StringUtil
import com.pengc.wanandroidkong.utils.converter.FastJsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import android.content.Context.MODE_PRIVATE
import android.R.id.edit
import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.pengc.wanandroidkong.base.WAKApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


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
            val cookieJar = PersistentCookieJar(SetCookieCache(),SharedPrefsCookiePersistor(WAKApplication.getIns()))
            val logInterceptor = HttpLoggingInterceptor(httpLogger)
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClientBuilder = OkHttpClient().newBuilder()
                .addNetworkInterceptor(logInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
//                .addInterceptor(ReceivedCookiesInterceptor())
//                .addInterceptor(AddCookiesInterceptor())
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
//                if (message.startsWith("<-- END HTTP")) {
                    logger.info(mMessage.toString())
//                    logger.info("我是小尾巴")
                    mMessage.setLength(0)
//                }
            }
        }
    }


    class ReceivedCookiesInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalResponse = chain.proceed(chain.request())
            var cookies = HashSet<String>()
            var sp = WAKApplication.getIns().getSharedPreferences("config", MODE_PRIVATE)
            val value = sp.getStringSet("cookie", null)
            if (value != null) {
                cookies.addAll(value as HashSet<String>)
            }

            if (originalResponse.headers("Set-Cookie").isNotEmpty()) {

                for (header in originalResponse.headers("Set-Cookie")) {
                    if (header.contains("loginUserName")) {
                        val name = header.split(";")[0].split("=")[1]
                        if(name.isEmpty() || "\"\"" == name){
                            cookies.clear()
                            break
                        }else {
                            cookies.add(header)
                        }
                    }
                }

                val config =
                    WAKApplication.getIns().getSharedPreferences("config", MODE_PRIVATE)
                        .edit()
                config.putStringSet("cookie", cookies)
                config.apply()
            }

            return originalResponse
        }
    }


    class AddCookiesInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            val preferences: HashSet<String>
            var sp = WAKApplication.getIns().getSharedPreferences("config", MODE_PRIVATE)
            preferences = if (sp == null || sp.getStringSet("cookie", null) == null) {
                HashSet<String>()
            } else {
                sp.getStringSet("cookie", null) as HashSet<String>
            }
            if (preferences != null) {
                for (cookie in preferences) {
                    builder.addHeader("Cookie", cookie)
                    LogUtils.v(
                        "OkHttp",
                        "Adding Header: $cookie"
                    ) // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
                }
            }
            return chain.proceed(builder.build())
        }
    }
}