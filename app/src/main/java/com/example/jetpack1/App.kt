package com.example.jetpack1
import android.app.Application
import com.chivox.online.network.RetrofitManager
import com.example.jetpack1.constant.ApiConstant
import com.example.jetpack1.network.ApiRepository


class App : Application() {

    companion object {
        val repository: ApiRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitManager.putBaseUrl(ApiConstant.KEY_GATE_URL,ApiConstant.GATE_URL)
                .putBaseUrl(ApiConstant.KEY_MOBILE_URL,ApiConstant.M_URL)
                .putBaseUrl(ApiConstant.KEY_TOKEN_URL,ApiConstant.TOKEN_URL)
                .retrofitBuilder
                .baseUrl(ApiConstant.API_URL)
                .build()
                .create(ApiRepository::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

}