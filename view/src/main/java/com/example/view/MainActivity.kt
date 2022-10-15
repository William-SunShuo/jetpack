package com.example.view
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(Constant.TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i(Constant.TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(Constant.TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(Constant.TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(Constant.TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(Constant.TAG, "onDestroy")
    }
}