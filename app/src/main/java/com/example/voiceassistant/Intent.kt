package com.example.voiceassistant

import org.vosk.Model

sealed interface Intent {
	data object LoadModel : Intent

	data class SetModel(val model: Model) : Intent

	data class SetError(val message: String) : Intent

	data class ClickButton(val enable: Boolean) : Intent
}