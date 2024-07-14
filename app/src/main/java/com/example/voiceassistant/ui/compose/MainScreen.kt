package com.example.voiceassistant.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.voiceassistant.Intent
import com.example.voiceassistant.State
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(
	state: StateFlow<State>,
	applyIntent: (Intent) -> Unit
) {
	Scaffold { paddingValues ->
		val uiState = state.collectAsState()
		Box(
			modifier = Modifier.padding(paddingValues),
		) {
			when (val state = uiState.value) {
				is State.Initial -> {
					applyIntent(Intent.LoadModel)
				}

				is State.Loading -> {
					Loading()
				}

				is State.Content -> {
					MainContent(
						uiState = state
					) {
						applyIntent(Intent.ClickButton(it))
					}
				}

				is State.Error   -> {
					Error(
						text = state.message
					) {
						applyIntent(Intent.LoadModel)
					}
				}
			}
		}
	}
}