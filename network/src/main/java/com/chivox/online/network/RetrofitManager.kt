package com.chivox.online.network
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.Proxy
import java.util.*
import java.util.concurrent.TimeUnit

object RetrofitManager {

    var retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
    private const val TIME_OUT = 30L
    private val mHeaderMap: MutableMap<String, Any> = HashMap()
    private val mUrlMap: MutableMap<String, HttpUrl> = HashMap()

    private const val BASE_URL_NAME = "base_url_name"
    const val BASE_URL_NAME_KEY = BASE_URL_NAME.plus(": ")

    fun addHeader(key: String, value: String) {
        mHeaderMap[key] = value
    }

    fun putBaseUrl(key: String, url: String): RetrofitManager {
        val httpUrl = url.toHttpUrlOrNull()
        httpUrl?.also {
            mUrlMap[key] = it
        }
        return this
    }

    private val mHeaderInterceptor: Interceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            //避免某些服务器配置攻击,请求返回403 forbidden 问题
            addHeader("User-Agent", "Mozilla/5.0 (Android)")
            if (mHeaderMap.isNotEmpty()) {
                for ((key, value) in mHeaderMap.entries) {
                    requestBuilder.addHeader(key, value.toString())
                }
            }
            return chain.proceed(processRequest(chain.request(), requestBuilder))
        }
    }

    init {
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.i("TAG", message)
            }
        })
        val sslParams: SSLUtil.SSLParams = SSLUtil.getSslSocketFactory()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient =
            OkHttpClient.Builder().connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .proxy(Proxy.NO_PROXY)
                .addInterceptor(mHeaderInterceptor)
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager)
                .hostnameVerifier { _, _ -> true }
                .build()
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
    }

    fun processRequest(request: Request,requestBuilder: Request.Builder): Request {
        val httpUrl: HttpUrl? = getHeaderHttpUrl(request)
        httpUrl?.also {
            //解析得到service里的方法名(即@POST或@GET里的内容)--如果@GET 并添加参数 则为方法名+参数拼接
            val method = request.url.toString().replace("https://chivoxapp.com/", "")
            return requestBuilder.url(it.toString() + method).build()
        }
        return requestBuilder.build()
    }

    private fun getHeaderHttpUrl(request: Request?): HttpUrl? {
        request?.apply {
            val keyName = header(BASE_URL_NAME)
            return mUrlMap[keyName]
        }
        return null
    }
}