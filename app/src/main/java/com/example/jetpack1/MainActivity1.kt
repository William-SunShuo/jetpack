package com.example.jetpack1
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.method.ArrowKeyMovementMethod
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.tv_text)
        textView.isSelected = true
        textView.movementMethod = ArrowKeyMovementMethod.getInstance();
//        model.nameEvent.observe(this, {
//            textView.text = it
//        })
//
//        textView.setOnClickListener {
//            model.nameEvent.value = "haha"
//        }

        NetWorkLiveData(this, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)).observe(this){
            Log.i("TAG","network changed")
        }
    }
}