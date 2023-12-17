package com.trainer.srb.rus.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun HomeScreen(
    navigateToSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Search(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused)
                        navigateToSearch()
                }
                .clickable {
                    navigateToSearch()
                },
            value = "",
            onValueChange = {}
        )
        Body(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun Body(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.border(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(3.dp, MainTheme.colors.Border)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = DesignRes.drawable.learn),
                contentDescription = null,
                modifier = Modifier.padding(10.dp)
            )
        }
        Text(
            text = "Учить слова",
            style = MainTheme.typography.displayMedium,
            color = MainTheme.colors.Tips
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        navigateToSearch = {}
    )
}