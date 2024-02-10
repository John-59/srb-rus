package com.trainer.srb.rus.feature.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons

@Composable
fun WordActions(
    onNext: () -> Unit,
    isNextEnabled: Boolean,
    onAlreadyKnow: () -> Unit,
    onDontWantLearn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = onAlreadyKnow,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                imageVector = SrIcons.Done,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inverseSurface
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Уже знаю это слово",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            onClick = onDontWantLearn,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                imageVector = SrIcons.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Не хочу учить это слово",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNext,
            enabled = isNextEnabled
        ) {
            Text(
                text = "Далее",
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun WordActionsPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        WordActions(
            onNext = {},
            isNextEnabled = true,
            onAlreadyKnow = {},
            onDontWantLearn = {})
    }
}