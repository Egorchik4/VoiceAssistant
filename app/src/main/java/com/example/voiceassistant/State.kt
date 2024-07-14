package com.example.voiceassistant

sealed interface State {
	data object Initial : State

	data object Loading : State

	data class Content(val text: String) : State

	data class Error(val message: String) : State
}