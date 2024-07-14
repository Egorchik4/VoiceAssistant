package com.example.voiceassistant.ui.compose

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Button(
	text: String,
	modifier: Modifier = Modifier,
	onClick: () -> Unit,
) {
	Button(
		onClick = onClick,
		modifier = modifier,
	) {
		Text(text = text)
	}
}

@Preview
@Composable
fun PreviewButton() {
	Button(
		text = "Hello",
		onClick = {},
	)
}