package com.trainer.srb.rus.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationDialog(
    text: String,
    onExit: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacerModifier = Modifier.size(20.dp)
    AlertDialog(
        onDismissRequest = onCancel,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(spacerModifier)
            Row {
                Spacer(modifier = Modifier.weight(2f))
                Button(
                    onClick = onCancel
                ) {
                    Text(
                        text = "Нет"
                    )
                }
                Spacer(spacerModifier)
                Button(
                    onClick = onExit,
                ) {
                    Text(
                        text = "Да"
                    )
                }
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun ExitExerciseConfirmationDialogPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ConfirmationDialog(
            text = "Закончить упражнение?",
            onExit = {},
            onCancel = {}
        )
    }
}