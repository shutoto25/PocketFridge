package com.example.pocketfridge.view.fragment

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.FragmentCameraBinding
import com.example.pocketfridge.view.callback.EventObserver
import com.example.pocketfridge.viewModel.CameraViewModel
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * カメラモード画面.
 */
class CameraFragment : Fragment() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "CameraFragment"

        /** ファイル名フォーマット. */
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    private val args: CameraFragmentArgs by navArgs()

    /** ViewModel. */
    private val cameraViewModel by lazy {
        ViewModelProvider(this)[CameraViewModel::class.java]
    }

    /** Binding. */
    private lateinit var binding: FragmentCameraBinding

    /** cameraProvider. */
    private var cameraProvider: ProcessCameraProvider? = null

    /** preview. */
    private var preview: Preview? = null

    /** ImageCapture. */
    private var imageCapture: ImageCapture? = null

    /** バックカメラ指定. */
    private var lensFacing: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var analysis: ImageAnalysis? = null

    /** barcode. */
    private val workerExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private val scanner: BarcodeScanner = BarcodeScanning.getClient()
    private val analyzer: CodeAnalyzer = CodeAnalyzer(scanner)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        binding.apply {
            viewModel = cameraViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")
        // 画面遷移イベントオブザーバー.
        cameraViewModel.onEvent.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                "TAKE_PIC" -> takePhoto()
                else -> findNavController().navigate(R.id.action_camera_to_tab)
            }
        })
        startCamera()
    }

    /** CameraX初期化. */
    private fun startCamera() {
        Log.d(TAG, "startCamera() called")
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
            ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()

                bindCameraUseCases()
            } catch (e: ExecutionException) {
                Log.e(TAG, e.localizedMessage, e)
            } catch (e: InterruptedException) {
                Log.e(TAG, e.localizedMessage, e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    /** バインド実行. */
    private fun bindCameraUseCases() {
        Log.d(TAG, "bindCameraUseCases() called")
        // CameraProvider
        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        // Preview
        preview = Preview.Builder().build().also {
            it.setSurfaceProvider(binding.cameraViewFinder.surfaceProvider)
        }

        imageCapture = ImageCapture.Builder().build()
        analysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build().also { it.setAnalyzer(workerExecutor, analyzer) }
        val useCase: UseCase =
            if (args.isBarcodeMode) analysis as UseCase else imageCapture as UseCase

        // バインドされているカメラを解除.
        cameraProvider.unbindAll()
        // カメラをライフサイクルにバインド.
        cameraProvider.bindToLifecycle(this, lensFacing, preview, useCase)
    }

    /** 撮影. */
    private fun takePhoto() {
        // imageCaptureがセットされていないときはreturn.
        val imageCapture = imageCapture ?: return

        // ファイル名とMediaStoreを設定.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.JAPAN)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/pocket-fridge")
            }
        }
        // 保存オプションを作成.
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        // 画像のキャプチャ(結果はImageCapture.OnImageSavedCallbackで返ってくる)
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            }
        )
    }
}