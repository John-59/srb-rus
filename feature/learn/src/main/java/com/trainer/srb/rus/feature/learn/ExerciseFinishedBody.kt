package com.trainer.srb.rus.feature.learn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun ExerciseFinishedBody(
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(2.0f)
        ) {
            Image(
                painter = painterResource(id = DesignRes.drawable.finish),
                contentDescription = null,
            )
        }
        Button(
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainTheme.colors.Buttons,
                contentColor = MainTheme.colors.White,
            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = onNext
        ) {
            Text(
                text = "Упражнение закончено",
                style = MainTheme.typography.displayMedium
            )
        }
    }

}

@Preview(apiLevel = 33)
@Composable
fun ExerciseFinishedBodyPreview() {
    ExerciseFinishedBody(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        onNext = {}
    )
}