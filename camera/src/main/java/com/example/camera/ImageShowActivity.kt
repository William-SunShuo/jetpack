package com.example.camera
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.camera.databinding.ActivityShowImageBinding

class ImageShowActivity:AppCompatActivity() {

    private val binding by lazy { ActivityShowImageBinding.inflate(layoutInflater) }
    private val imagePath by lazy { intent.getStringExtra("imagePath") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        val bitmap = BitmapFactory.decodeFile(imagePath,options)
        Log.d("TAG","width:${bitmap.width},height:${bitmap.height}")
        binding.iv.setImageBitmap(bitmap)

    }
}