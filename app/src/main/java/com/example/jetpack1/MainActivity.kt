package com.example.jetpack1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(CoroutinesViewModel::class.java) }
    var ll:LinearLayout? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setContentView(R.layout.activity_main)
        ll = findViewById(R.id.ll)
        lifecycle.addObserver(MyLifecycleObserver1())
        viewModel.articlesLiveData.observe(this, { list ->
            list.forEach {
                Log.i("TAG","$it")
            }
        })
//        val textView = findViewById<TextView>(R.id.tv_text)
//        val spannableString = SpannableString("hahaha")
//        spannableString.setSpan(BorderSpan(0xffff0000.toInt()),2,5,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
//        var htmlStr = "<span style=\"color:red;margin:0 auto\">这是一行<span>文</span>本</span><span style =\"color:red;margin:0 auto;font-size:0\">第二行文本</span>"
//        htmlStr = htmlStr.replace("<p>","")
//        textView.text = Html.fromHtml(htmlStr)
//        User().also { binding.user = it }


//        val user = User(ObservableField("william"), ObservableField("sun"))
//        binding.user = user
//        binding.setClickListener {
//            user.firstName.set("san")
//            user.lastName.set("zhang")
//            viewModel.pass("ysldj","123")
//        }

        runBlocking {
//            doWorld()
//            Log.i("haha", "Done")
            launch {
                delay(200L)
                Log.i("haha", "Task from runBlocking")
            }

            coroutineScope { // 创建一个协程作用域
                launch {
                    delay(500L)
                    Log.i("haha", "Task from nested launch")
                }

                delay(100L)
                Log.i("haha", "Task from coroutine scope")// 这一行会在内嵌 launch 之前输出
            }

            Log.i("haha", "Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
        }
        viewModel.pass("12344334545","123456")
    }


    private suspend fun doWorld() = coroutineScope {
        launch {
            delay(2000L)
            Log.i("haha", "world2")
        }
        launch {
            delay(1000L)
            Log.i("haha", "world1")
        }
        Log.i("haha", "hello")
    }


    private fun launchFromGlobalScope(textView: TextView) {

        GlobalScope.launch(Dispatchers.IO) {
            textView.text = Thread.currentThread().name
//            val defer = async {
//                delay(3000)
//                "等了3s"
//            }
//            val defer1 = async {
//                delay(5000)
//                "等了5s"
//            }

//            withContext(Dispatchers.Main) {
//                textView.text = Thread.currentThread().name.plus("拿到结果")
//            }
        }
    }

    override fun onResume() {
        super.onResume()
//        val textView = findViewById<TextView>(R.id.tv_text)
//        launchFromGlobalScope(textView)
        Log.i("MyLinearLayout", "onResume")
        ll?.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED))
        Log.i("MyLinearLayout", "MainActivity:${ll?.measuredHeight}")
    }

}


