package com.example.scroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinate)
        findViewById<RecyclerView>(R.id.recycle).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): RecyclerView.ViewHolder {
                    val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.item,parent,false)
                    return object : RecyclerView.ViewHolder(view){}
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                    (holder.itemView as TextView).text = "hahah"
                    Log.i("TAG","onBindView:$position")
                }

                override fun getItemCount(): Int {
                    return 100
                }
            }
        }
    }
}