package com.example.jetpack1
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chivox.online.network.RetrofitManager
import com.example.jetpack1.network.entity.Article
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CoroutinesViewModel : ViewModel() {


    var articlesLiveData: MutableLiveData<MutableList<Article>> = MutableLiveData()

    var apiError:MutableLiveData<Throwable> = MutableLiveData()

    fun pass(username:String,password:String){
        val params: MutableMap<String, Any> = HashMap()
        params["username"] = username
        params["password"] = password

        val exception = CoroutineExceptionHandler { _, throwable ->
            apiError.postValue(throwable)
            Log.i("CoroutinesViewModel", throwable.message!!)
        }

        viewModelScope.launch(exception) {
            val gateParams : MutableMap<String,Any> = HashMap()
            gateParams["stuId"] = ""
            gateParams["t"] = System.currentTimeMillis()
            val deferred1 = async {
                App.repository.gateway(gateParams)
            }
            val deferred2 = async {
                App.repository.login(params)
            }
            val deferred3 = async {
                params["clientId"] = "4"
                params["clientSecret"] = "c1DE1Dqj3Xs5vNMeYHLOAL8cQVDBhDmcYeh5udXu"
                App.repository.passportToken(params)
            }
            deferred1.await()
            deferred2.await()
            val passportToken = deferred3.await()
            Log.i("CoroutinesViewModel", "accessToken:${passportToken.data?.accessToken}")
            RetrofitManager.addHeader(
                "Authorization",
                "${passportToken.data?.tokenType} ${passportToken.data?.accessToken}"
            )
        }
    }
}