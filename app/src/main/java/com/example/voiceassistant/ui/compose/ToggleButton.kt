package com.example.voiceassistant.ui.compose

import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.voiceassistant.R

@Composable
fun ToggleButton(
	onClick: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
	color: Color = Color.Unspecified
) {
	var checked by remember { mutableStateOf(false) }
	IconToggleButton(
		checked = checked,
		onCheckedChange = {
			onClick(it)
			checked = it
		},
		modifier = modifier,
	) {
		Icon(
			painter = painterResource(
				id = if (checked) {
					R.drawable.ic_mic_on
				} else {
					R.drawable.ic_mic_off
				}
			),
			contentDescription = "Play",
			modifier = modifier,
			tint = color,
		)
	}
}

@Preview
@Composable
fun PreviewToggleButton() {
	ToggleButton(
		onClick = { },
		color = Color.Blue,
	)
}