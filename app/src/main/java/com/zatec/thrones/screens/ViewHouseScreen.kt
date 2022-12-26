package com.zatec.thrones.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zatec.thrones.R
import com.zatec.thrones.ui.theme.BASE_URL
import com.zatec.thrones.ui.theme.BKG_IMAGE_DESCRIPTION
import com.zatec.thrones.ui.theme.Error_DESCRIPTION
import com.zatec.thrones.viewModel.ViewHouseVM

@Composable
fun ViewHouseScreen(vm: ViewHouseVM = ViewHouseVM(), houseId: String){
    LaunchedEffect(key1 = Unit, block = {
        vm.getHouse("$BASE_URL/houses/$houseId")
    })


    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(5.dp)) {

        Image(
            painter = painterResource(id = R.drawable.got4),
            contentDescription = BKG_IMAGE_DESCRIPTION,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondaryContainer ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
                .fillMaxHeight()
        )

        if(vm.errorMessage.isEmpty()){
            Column {
                Box (
                    Modifier
                        .fillMaxHeight(.2f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = vm.name,
                        fontSize = 25.sp,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(10.dp)
                            .align(Alignment.Center)
                    )
                }

                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()) {

                    RowCard(title = "Region", value = vm.region)
                    RowCard(title = "Coat of Arms", value = vm.coatOfArms)
                    RowCard(title = "Slogan", value = vm.words)
                    RowCard(title = "Current Lord", value = vm.currentLord)
                    RowCard(title = "Heir", value = vm.heir)
                    RowCard(title = "Overlord", value = vm.overlord)

                    val titles: List<String> = vm.titles.removeSurrounding("[","]").split(",")
                    RowCard(title = "Titles", value = titles)
                    val seats: List<String> = vm.seats.removeSurrounding("[","]").split(",")
                    RowCard(title = "Seats", value = seats)
                }

            }
        }else{
            ErrorCard(message = vm.errorMessage)
        }




    }
}

@Composable
fun RowCard(title: String, value: String = ""){
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(5.dp, 5.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth(.3f)
                .padding(10.dp, 5.dp),

        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 5.dp),
            maxLines = 3
        )

    }
}

@Composable
fun RowCard(title: String, value: List<String>){
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(20.dp)
                .padding(10.dp),

            )
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(10.dp, 10.dp)) {
        items(value){ payload ->
            Text(
                text = payload,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(5.dp),
                maxLines = 3
            )

        }

    }
}

@Composable
private fun ErrorCard(message: String){
    Box(
        modifier = Modifier
            .wrapContentHeight(Alignment.Top)
            .fillMaxWidth()
            .padding(10.dp, 10.dp)
            .background(color = MaterialTheme.colorScheme.error),
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
                colorFilter  =  ColorFilter.tint(MaterialTheme.colorScheme.onError),
                modifier = Modifier
                    .height(70.dp)
                    .width(70.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = message,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onError,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}