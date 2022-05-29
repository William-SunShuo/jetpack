package com.example.camera
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.core.AspectRatio.RATIO_4_3
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import com.example.camera.databinding.ActivitySecondBinding
import java.io.File
import java.util.concurrent.Executors


class CameraActivity : AppCompatActivity() {

    val l by lazy { intent.getStringExtra("hah") ?: "l" }

    private val binding by lazy { ActivitySecondBinding.inflate(layoutInflater) }

    private val cameraExecutor = Executors.newSingleThreadExecutor()

    private val preview by lazy {
        Preview.Builder().setTargetAspectRatio(RATIO_4_3)
            .setTargetRotation(binding.surfacePreview.display.rotation).build()
            .also { it.setSurfaceProvider(binding.surfacePreview.surfaceProvider) }
    }

    private val imageCapture by lazy {
        ImageCapture.Builder().setTargetAspectRatio(RATIO_4_3)
            .setTargetRotation(binding.surfacePreview.display.rotation).build()
    }

    private val imageAnalyzer by lazy {
        ImageAnalysis.Builder().setTargetAspectRatio(RATIO_4_3).build().also { imageAnalysis ->
            imageAnalysis.setAnalyzer(cameraExecutor) {
                Log.d("TAG", "rotationDegrees:${it.imageInfo.rotationDegrees}")
                it.close()
            }
        }
    }

    private val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private var cameraProvider: ProcessCameraProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deleteInvalidateFiles(File("${externalCacheDir?.absolutePath}"))
        setContentView(binding.root)
        binding.takePhoto.setOnClickListener {
            val imagePath =
                "${externalCacheDir?.absolutePath}/pic_${System.currentTimeMillis()}.jpg"
            val file = File(imagePath)

            imageCapture.takePicture(
                ImageCapture.OutputFileOptions.Builder(file).build(),
                cameraExecutor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        Log.d("TAG", "file:${imagePath}")
                        getImageDegree(imagePath)
                        startActivity(
                            Intent(
                                this@CameraActivity,
                                ImageShowActivity::class.java
                            ).apply { putExtra("imagePath", imagePath) })
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.d("TAG", "${exception.message}")
                    }
                })
        }
        startCamera()
    }

    private fun getImageDegree(path: String) {
        val mat = Matrix()
        val bitmap: Bitmap = BitmapFactory.decodeFile(path, BitmapFactory.Options())
        val ei = ExifInterface(path)
        val orientation: Int =
            ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> mat.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> mat.postRotate(180f)
        }
        Log.i("TAG", "orientation:$orientation")
    }

    private fun deleteInvalidateFiles(file: File) {
        val files = file.listFiles()
        files?.apply {
            val iterator = iterator()
            while (iterator.hasNext()) {
                val f = iterator.next()
                if (f.isFile) {
                    if (System.currentTimeMillis() - f.lastModified() > 30 * 60 * 1000) f.delete()
                } else {
                    deleteInvalidateFiles(file)
                }
            }
        }
    }

    private fun startCamera() {
        if (cameraProvider == null) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
            cameraProviderFuture.addListener({
                cameraProvider = cameraProviderFuture.get()
                startPreview()
            }, ContextCompat.getMainExecutor(this))
        } else {
            startPreview()
        }
    }

    private fun hasPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10 && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            startPreview()
        }
    }

    private fun startPreview() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasPermission()) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 10)
            return
        }
        try {
            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(this, cameraSelector, imageCapture, preview, imageAnalyzer).also {
                Log.d("TAG","sensorRotation:${it?.cameraInfo?.sensorRotationDegrees}")
                it?.cameraInfo?.cameraState?.observe(this) { cameraState ->
                    run {
                        when (cameraState.type) {
                            CameraState.Type.PENDING_OPEN -> {
                                // Ask the user to close other camera apps
                                Toast.makeText(
                                    this@CameraActivity,
                                    "CameraState: Pending Open",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            CameraState.Type.OPENING -> {
                                // Show the Camera UI
                                Toast.makeText(
                                    this@CameraActivity,
                                    "CameraState: Opening",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            CameraState.Type.OPEN -> {
                                // Setup Camera resources and begin processing
                                Toast.makeText(
                                    this@CameraActivity,
                                    "CameraState: Open",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            CameraState.Type.CLOSING -> {
                                // Close camera UI
                                Toast.makeText(
                                    this@CameraActivity,
                                    "CameraState: Closing",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            CameraState.Type.CLOSED -> {
                                // Free camera resources
                                Toast.makeText(
                                    this@CameraActivity,
                                    "CameraState: Closed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    cameraState.error?.let { error ->
                        when (error.code) {
                            // Open errors
                            CameraState.ERROR_STREAM_CONFIG -> {
                                // Make sure to setup the use cases properly
                                Toast.makeText(
                                    this@CameraActivity,
                                    "Stream config error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            // Opening errors
                            CameraState.ERROR_CAMERA_IN_USE -> {
                                // Close the camera or ask user to close another camera app that's using the
                                // camera
                                Toast.makeText(
                                    this@CameraActivity,
                                    "Camera in use",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            CameraState.ERROR_MAX_CAMERAS_IN_USE -> {
                                // Close another open camera in the app, or ask the user to close another
                                // camera app that's using the camera
                                Toast.makeText(
                                    this@CameraActivity,
                                    "Max cameras in use",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            CameraState.ERROR_OTHER_RECOVERABLE_ERROR -> {
                                Toast.makeText(
                                    this@CameraActivity,
                                    "Other recoverable error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            // Closing errors
                            CameraState.ERROR_CAMERA_DISABLED -> {
                                // Ask the user to enable the device's cameras
                                Toast.makeText(
                                    this@CameraActivity,
                                    "Camera disabled",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            CameraState.ERROR_CAMERA_FATAL_ERROR -> {
                                // Ask the user to reboot the device to restore camera function
                                Toast.makeText(
                                    this@CameraActivity,
                                    "Fatal error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            // Closed errors
                            CameraState.ERROR_DO_NOT_DISTURB_MODE_ENABLED -> {
                                // Ask the user to disable the "Do Not Disturb" mode, then reopen the camera
                                Toast.makeText(
                                    this@CameraActivity,
                                    "Do not disturb mode enabled",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("TAG", "${e.message}")
        }
    }
}