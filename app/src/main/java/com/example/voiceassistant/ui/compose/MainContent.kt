package com.example.voiceassistant.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voiceassistant.R
import com.example.voiceassistant.State

@Composable
fun MainContent(
	uiState: State.Content,
	onClick: (Boolean) -> Unit,
) {
	Box(
		modifier = Modifier.fillMaxSize(),
	) {
		TextField(
			modifier = Modifier
				.fillMaxSize(),
			readOnly = true,
			value = uiState.text.ifEmpty {
				stringResource(id = R.string.ready)
			},
			onValueChange = {},
		)
		ToggleButton(
			onClick = onClick,
			modifier = Modifier
				.size(70.dp)
				.align(Alignment.BottomCenter),
			color = Color.Blue,
		)
		Spacer(modifier = Modifier.height(4.dp))
	}
}

@Preview
@Composable
fun PreviewMainContent() {
	MainContent(
		uiState = State.Content(
			text = "Hello"
		),
		onClick = {}
	)
}