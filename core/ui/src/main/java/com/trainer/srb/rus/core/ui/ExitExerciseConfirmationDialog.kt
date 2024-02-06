package com.trainer.srb.rus.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.trainer.srb.rus.core.design.MainTheme

@Composable
fun ExitExerciseConfirmationDialog(
    onExit: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onCancel) {
        Surface(
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
        ) {
            Column {
                Text(
                    text = "Закончить упражнение?",
                    style = MaterialTheme.typography.titleMedium
                )
                Row {
                    Spacer(modifier = Modifier.weight(2f))
                    Button(
                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            backgroundColor = MaterialTheme.colorScheme // MainTheme.colors.Buttons,
//                            contentColor = MainTheme.colors.White,
//                        ),
                        onClick = onCancel
                    ) {
                        Text(
                            text = "Нет"
                        )
                    }
                    Button(
                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            backgroundColor = MainTheme.colors.Buttons,
//                            contentColor = MainTheme.colors.White,
//                        ),
                        onClick = onExit,
                        modifier = Modifier.padding(start = 20.dp)
                    ) {
                        Text(
                            text = "Да"
                        )
                    }
                }
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun ExitExerciseConfirmationDialogPreview() {
    ExitExerciseConfirmationDialog(
        onExit = {},
        onCancel = {}
    )
}