package com.trainer.srb.rus.core.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.trainer.srb.rus.core.design.MainTheme

@Composable
fun SrOutlinedButton(
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(onClick = { /*TODO*/ }) {
        content()
    }
}

@Preview(apiLevel = 33)
@Composable
fun EnabledSrOutlinedButtonPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        SrOutlinedButton() {
            Text("llkjsdbfjsad")
        }
    }
}