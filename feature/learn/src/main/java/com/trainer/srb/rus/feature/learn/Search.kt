package com.trainer.srb.rus.feature.learn

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.ui.CustomTextField
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun Search(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .border(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, MainTheme.colors.Border)
            )
            .background(
                color = MainTheme.colors.White,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(
                shape = RoundedCornerShape(10.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = DesignRes.drawable.search),
            contentDescription = null,
        )
        CustomTextField(
            modifier = Modifier
                .padding(start = 10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            placeholder = {
                Text(
                    text = "Поиск слов",
                    style = MainTheme.typography.displayMedium.copy(
                        baselineShift = BaselineShift(-0.2f)
                    ),
                    color = MainTheme.colors.Tips,
                )
            },
            textStyle = MainTheme.typography.displayMedium.copy(
                baselineShift = BaselineShift(-0.2f)
            ),
            value = value,
            onValueChange = onValueChange
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun SearchPreview() {
    Search(
        value = "",
        onValueChange = {}
    )
}