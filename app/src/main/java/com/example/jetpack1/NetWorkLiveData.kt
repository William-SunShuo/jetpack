package com.example.jetpack1
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData

class NetWorkLiveData(
    private var context: Context,
    var intentFilter: IntentFilter
) : LiveData<NetworkInfo>() {

    private var netWorkReceiver = NetworkBroadcastReceiver()

    override fun onActive() {
        super.onActive()
        context.registerReceiver(netWorkReceiver, intentFilter)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(netWorkReceiver)
    }


    inner class NetworkBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { i ->
                if (i.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                    context?.let {
                        val connectivityManager =
                            it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                        this@NetWorkLiveData.value = connectivityManager.activeNetworkInfo
                    }
                }
            }
        }

    }


}