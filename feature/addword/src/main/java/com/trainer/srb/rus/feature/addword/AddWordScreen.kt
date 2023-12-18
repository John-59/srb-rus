package com.trainer.srb.rus.feature.addword

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.ui.CustomTextField
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun AddWordScreen(
    modifier: Modifier = Modifier,
    viewModel: AddWordViewModel = hiltViewModel(),
) {
    Column(
        modifier = modifier.padding(20.dp),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Words(
            value = viewModel.srbLatValue,
            onValueChange = viewModel::srbLatinChange,
            placeholder = "Сербский (латиница)",
            iconId = DesignRes.drawable.srblat
        )

        Words(
            value = viewModel.srbCyrValue,
            onValueChange = viewModel::srbCyrillicChange,
            placeholder = "Сербский (кириллица)",
            iconId = DesignRes.drawable.srbcyr,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        Words(
            value = viewModel.rusValue,
            onValueChange = viewModel::rusChange,
            placeholder = "Русский",
            iconId = DesignRes.drawable.rus,
            modifier = Modifier.padding(bottom = 20.dp)
        )


        Button(
            onClick = viewModel::add,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainTheme.colors.Buttons,
                contentColor = MainTheme.colors.White
            )
//            border = BorderStroke(1.dp, MainTheme.colors.Border),
//            modifier = Modifier
//                .border(
//                    shape = RoundedCornerShape(10.dp),
//                    border = BorderStroke(2.dp, MainTheme.colors.Border)
//                )
        ) {
            Image(
                painter = painterResource(id = DesignRes.drawable.plusforbtn),
                contentDescription = null
            )
            Text(
                text = "Добавить в словарь",
                style = MainTheme.typography.displayMedium.copy(
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
private fun Words(
    value: String,
    onValueChange: (String) -> Unit,
    @DrawableRes iconId: Int,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .border(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, MainTheme.colors.Border)
            )
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(iconId),
                contentDescription = null
            )
            CustomTextField(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(),
                textStyle = MainTheme.typography.displayMedium.copy(
                    baselineShift = BaselineShift(-0.2f)
                ),
                value = value,
                placeholder = {
                    Text(
                        text = placeholder,
                        style = MainTheme.typography.displayMedium.copy(
                            baselineShift = BaselineShift(-0.2f)
                        ),
                        color = MainTheme.colors.Tips,
                    )
                },
                onValueChange = onValueChange
            )
        }
    }
}

@Composable
@Preview(apiLevel = 33)
private fun AddWordScreenPreview() {
    AddWordScreen(
        viewModel = AddWordViewModel(
            savedStateHandle = SavedStateHandle()
        )
    )
}