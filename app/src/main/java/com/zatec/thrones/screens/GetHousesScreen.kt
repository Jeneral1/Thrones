package com.zatec.thrones.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zatec.thrones.R
import com.zatec.thrones.model.House
import com.zatec.thrones.ui.theme.BKG_IMAGE_DESCRIPTION
import com.zatec.thrones.ui.theme.QUOTE_DESCRIPTION
import com.zatec.thrones.ui.theme.SWORD_DESCRIPTION
import com.zatec.thrones.viewModel.HousesViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun GetHousesScreen(viewModel: HousesViewModel, onItemClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(5.dp)) {

        Image(
            painter = painterResource(id = R.drawable.got3),
            contentDescription = BKG_IMAGE_DESCRIPTION,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondaryContainer, ),
            modifier = Modifier.align(BottomEnd)
        )

        val houseList: Flow<PagingData<House>> = viewModel.house
        val houseListItems: LazyPagingItems<House> = houseList.collectAsLazyPagingItems()

        LazyColumn{
            items (houseListItems){item ->
                item?.let {
                    HouseItemCard(house = it, onItemClick = onItemClick)
                }
            }
            
            houseListItems.apply {
                when {
                    loadState.prepend is LoadState.NotLoading -> Unit
                    loadState.prepend is LoadState.Loading -> {
                        item {
                            Box(modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .align(Center)
                                .padding(10.dp),
                                contentAlignment = Center
                            ) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.onTertiary)
                            }
                        }
                    }
                    loadState.prepend is LoadState.Error -> {
                        item{

                        }
                    }
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .align(Center)
                                .padding(10.dp),
                                contentAlignment = Center
                            ) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.onTertiary)
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun HouseItemCard(
    modifier: Modifier = Modifier,
    house: House,
    onItemClick: () -> Unit
){
    ElevatedCard(
        modifier = modifier
            .wrapContentHeight(Top)
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onItemClick),
    ) {
        Column {
            Text(
                text = house.name,
                fontSize = 22.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
            )
            Row(modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(5.dp, 5.dp)) {
                Row(modifier = modifier.fillMaxWidth(.4f)) {
                    Image(
                        painter = painterResource(id = R.drawable.sword),
                        contentDescription = SWORD_DESCRIPTION,
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondaryContainer)
                    )
                    Text(
                        text = house.region,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.fillMaxWidth()
                    )
                }
                Row(modifier = modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.quotes),
                        contentDescription = QUOTE_DESCRIPTION,
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondaryContainer)
                    )
                    Text(
                        text = house.words,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }
        }

    }
}


