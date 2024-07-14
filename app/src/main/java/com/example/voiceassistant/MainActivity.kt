package com.example.voiceassistant

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.coroutineScope
import com.example.voiceassistant.ui.compose.MainScreen
import com.example.voiceassistant.ui.theme.VoiceAssistantTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.vosk.Model
import org.vosk.android.StorageService
import java.io.IOException

class MainActivity : ComponentActivity() {

	private val viewModel: MainViewModel by viewModels()

	companion object {

		const val MODEL_NAME = "model"
		const val MODEL = "vosk-model-small-ru-0.22"
		const val EMPTY = ""
		const val REQUEST_CODE = 0
		const val RATE = 16000.0f
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			VoiceAssistantTheme {
				MainScreen(
					state = viewModel.uiState,
					applyIntent = viewModel::applyIntent
				)
			}
		}

		viewModel.uiEvent.onEach {
			when (it) {
				is Event.LoadModel -> {
					handleLoadModelEvent()
				}
			}
		}.launchIn(lifecycle.coroutineScope)
	}

	private fun handleLoadModelEvent() {
		if (checkPermission()) {
			StorageService.unpack(
				this, MODEL, MODEL_NAME,
				{ model: Model? ->
					if (model != null) {
						viewModel.applyIntent(Intent.SetModel(model))
					}
				},
				{ exception: IOException ->
					viewModel.applyIntent(Intent.SetError(exception.message.toString()))
				}
			)
		} else {
			handleLoadModelEvent()
		}
	}

	private fun checkPermission(): Boolean {
		return if (
			ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO) !=
			PackageManager.PERMISSION_GRANTED
		) {
			ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_CODE)
			false
		} else {
			true
		}
	}
}