package com.example.voiceassistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voiceassistant.MainActivity.Companion.EMPTY
import com.example.voiceassistant.MainActivity.Companion.RATE
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.SpeechStreamService
import java.io.IOException

class MainViewModel : ViewModel(), RecognitionListener {

	private var model: Model? = null
	private var speechService: SpeechService? = null
	private var speechStreamService: SpeechStreamService? = null

	private val _uiState = MutableStateFlow<State>(State.Initial)
	val uiState = _uiState.asStateFlow()

	private val _uiEvent = MutableSharedFlow<Event>()
	val uiEvent = _uiEvent.asSharedFlow()

	fun applyIntent(intent: Intent) {
		when (intent) {
			is Intent.LoadModel   -> {
				handleLoadModel()
			}

			is Intent.SetModel    -> {
				handleSetModel(intent.model)
			}

			is Intent.ClickButton -> {
				handleClickButton(intent.enable)
			}

			is Intent.SetError    -> {
				handleError(intent.message)
			}

		}
	}

	private fun handleLoadModel() {
		_uiState.value = State.Loading
		viewModelScope.launch {
			_uiEvent.emit(Event.LoadModel)
		}
	}

	private fun handleSetModel(model: Model) {
		this.model = model
		_uiState.value = State.Content(text = EMPTY)
	}

	private fun handleClickButton(enable: Boolean) {
		if (enable && _uiState.value is State.Content) {
			startListening()
		} else {
			stopListening()
		}
	}

	private fun handleError(message: String) {
		_uiState.value = State.Error(message = message)
	}

	private fun startListening() {
		if (speechService != null) {
			speechService!!.stop()
			speechService = null
		} else {
			try {
				val rec = Recognizer(model, RATE)
				speechService = SpeechService(rec, RATE)
				speechService!!.startListening(this)
			} catch (e: IOException) {
				_uiState.value = State.Error(message = e.message.toString())
			}
		}
	}

	private fun stopListening() {
		if (speechService != null) {
			speechService!!.stop()
			speechService = null
		}
	}

	override fun onResult(hypothesis: String) {
		val state = _uiState.value as? State.Content ?: return
		_uiState.value = State.Content(text = state.text + hypothesis.setText())
	}

	override fun onFinalResult(hypothesis: String) {
		if (speechStreamService != null) {
			speechStreamService = null
		}
	}

	override fun onPartialResult(hypothesis: String) {}

	override fun onError(e: Exception) {
		_uiState.value = State.Error(message = e.message.toString())
	}

	override fun onTimeout() {}

	override fun onCleared() {
		super.onCleared()
		if (speechService != null) {
			speechService!!.stop()
			speechService!!.shutdown()
		}

		if (speechStreamService != null) {
			speechStreamService!!.stop()
		}
	}

	private fun String.setText(): String {
		val text = this.substringAfter(':').substringAfter('"').substringBeforeLast('"')
		return if (text.isNotEmpty()) {
			text + "\n"
		} else {
			text
		}
	}
}