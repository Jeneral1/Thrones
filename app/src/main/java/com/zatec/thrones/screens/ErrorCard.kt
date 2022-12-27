package com.zatec.thrones.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zatec.thrones.R
import com.zatec.thrones.ui.theme.Error_DESCRIPTION

/**
 * Composable function to show error message
 *
 * @param message text to be shown
 * */
@Composable
fun ErrorCard(message: String){
    Box(
        modifier = Modifier
            .wrapContentHeight(Alignment.Top)
            .fillMaxWidth()
            .padding(10.dp, 10.dp)
            .background(color = MaterialTheme.colorScheme.errorContainer),
    ) {

        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.error),
                contentDescription = Error_DESCRIPTION,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onErrorContainer),
                modifier = Modifier
                    .height(70.dp)
                    .width(70.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = message,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}