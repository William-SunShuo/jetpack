package com.example.jetpack1
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.method.ArrowKeyMovementMethod
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MainActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nested_scroll)
        val recyclerView = findViewById<RecyclerView>(R.id.rv)

        recyclerView.isNestedScrollingEnabled = true
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = object : Adapter<ViewHolder>(){
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): ViewHolder {
                val view = LayoutInflater.from(this@MainActivity1).inflate(R.layout.layout_textview,parent,false)
                return object : ViewHolder(view) {}

            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                (holder.itemView as TextView).text = "hahaha"
                Log.i(this@MainActivity1::class.java.simpleName,"onBindViewHolder")
            }

            override fun getItemCount(): Int {
                return 100
            }
        }

//        val ns = findViewById<NestedScrollView>(R.id.ns)
//        val linearLayout = findViewById<LinearLayout>(R.id.ll)
//        ns.post {
//            linearLayout.layoutParams.height = ns.measuredHeight
//            ns.requestLayout()
//        }
    }
}
