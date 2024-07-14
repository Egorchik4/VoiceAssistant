package com.example.voiceassistant.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voiceassistant.R

@Composable
fun Error(
	text: String,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	Column(
		modifier = modifier
			.padding(horizontal = 38.dp)
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		Text(
			text = stringResource(id = R.string.error),
			textAlign = TextAlign.Center,
			color = MaterialTheme.colorScheme.error
		)

		Spacer(modifier = Modifier.height(4.dp))

		Text(
			text = text,
			textAlign = TextAlign.Center,
			color = MaterialTheme.colorScheme.onSurfaceVariant,
		)

		Spacer(modifier = Modifier.height(20.dp))

		Button(text = stringResource(id = R.string.retry)) {
			onClick()
		}
	}
}

@Preview
@Composable
fun PreviewError() {
	Error(
		text = "Error exception",
		onClick = {}
	)
}