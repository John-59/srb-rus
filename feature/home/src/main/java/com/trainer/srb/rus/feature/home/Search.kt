package com.trainer.srb.rus.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.R
import com.trainer.srb.rus.core.ui.CustomTextField

@Composable
fun Search(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(20.dp)
            .border(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, MainTheme.colors.Border)
            ),
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
            )
            CustomTextField(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth(),
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
                value = "",
                onValueChange = {}
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun SearchPreview() {
    Search()
}