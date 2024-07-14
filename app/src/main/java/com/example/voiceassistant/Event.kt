package com.example.voiceassistant

sealed interface Event {

	data object LoadModel : Event
}