package com.zatec.thrones.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zatec.thrones.R
import com.zatec.thrones.ui.theme.BKG_IMAGE_DESCRIPTION

@Composable
fun ViewHouseScreen(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(5.dp)) {

        Image(
            painter = painterResource(id = R.drawable.got3),
            contentDescription = BKG_IMAGE_DESCRIPTION,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondaryContainer, ),
            modifier = Modifier.align(Alignment.BottomEnd)
        )

    }
}