package com.zatec.thrones.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zatec.thrones.R
import com.zatec.thrones.ui.theme.BASE_URL
import com.zatec.thrones.ui.theme.BKG_IMAGE_DESCRIPTION
import com.zatec.thrones.viewModel.ViewHouseVM

/**
 * The main Composable for the viewing all the Houses
 *
 * @param viewModel An instance of the [ViewHouseVM]which is used to encapsulate the data for the for this screen
 * and used to keep the immutable state of some components on this screen
 * @param houseId the houseId which is needed to load the information on this screen
 * */
@Composable
fun ViewHouseScreen(viewModel: ViewHouseVM = ViewHouseVM(), houseId: String){
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getHouse("$BASE_URL/houses/$houseId")
    })


    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(5.dp)
        .semantics { contentDescription = "View House Screen" }
    ) {

        Image(
            painter = painterResource(id = R.drawable.got4),
            contentDescription = BKG_IMAGE_DESCRIPTION,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondaryContainer ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
                .fillMaxHeight()
        )

        if(viewModel.errorMessage.isEmpty()){
            Column {
                Box (
                    Modifier
                        .fillMaxHeight(.2f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = viewModel.name,
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

                    RowCard(title = "Region", value = viewModel.region)
                    RowCard(title = "Coat of Arms", value = viewModel.coatOfArms)
                    RowCard(title = "Slogan", value = viewModel.words)
                    RowCard(title = "Current Lord", value = viewModel.currentLord)
                    RowCard(title = "Heir", value = viewModel.heir)
                    RowCard(title = "Overlord", value = viewModel.overlord)

                    val titles: List<String> = viewModel.titles.removeSurrounding("[","]").split(",")
                    RowCard(title = "Titles", value = titles)
                    val seats: List<String> = viewModel.seats.removeSurrounding("[","]").split(",")
                    RowCard(title = "Seats", value = seats)
                }

            }
        }else{
            ErrorCard(message = viewModel.errorMessage)
        }




    }
}

/**
 * Composable function to show a single [Row] of the information provided
 *
 * @param title the title of the text provided
 * @param value a String that is provided from the House data
 * */
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

/**
 * Composable function to show a [Column] of the information provided
 *
 * @param title the title of the text provided
 * @param value a list of [String] that is provided from the House data
 * */
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

