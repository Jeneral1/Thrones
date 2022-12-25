package com.zatec.thrones.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zatec.thrones.R
import com.zatec.thrones.model.House
import com.zatec.thrones.ui.theme.BKG_IMAGE_DESCRIPTION
import com.zatec.thrones.ui.theme.Error_DESCRIPTION
import com.zatec.thrones.ui.theme.QUOTE_DESCRIPTION
import com.zatec.thrones.ui.theme.SWORD_DESCRIPTION
import com.zatec.thrones.viewModel.HousesViewModel
import kotlinx.coroutines.flow.Flow
import java.io.IOException

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GetHousesScreen(viewModel: HousesViewModel, onItemClick: () -> Unit) {
    val refreshing by viewModel.isRefreshing.collectAsState()

    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { viewModel.refresh() })
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(5.dp)) {

        Image(
            painter = painterResource(id = R.drawable.got3),
            contentDescription = BKG_IMAGE_DESCRIPTION,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondaryContainer ),
            modifier = Modifier.align(BottomEnd)
        )

        val houseList: Flow<PagingData<House>> = viewModel.house
        val houseListItems: LazyPagingItems<House> = houseList.collectAsLazyPagingItems()

        Box (Modifier.pullRefresh(pullRefreshState)){
            LazyColumn {
                items(houseListItems) { item ->
                    item?.let {
                        HouseItemCard(house = it, onItemClick = onItemClick)
                    }
                }

                houseListItems.apply {
                    when {
                        //loadState.prepend is LoadState.NotLoading -> Unit
                        loadState.prepend is LoadState.Loading -> {
                            item { Loading() }
                        }
                        loadState.prepend is LoadState.Error -> {
                            item {
                                ErrorCard(
                                    message = if ((loadState.refresh as LoadState.Error).error is IOException)
                                        "Unable to connect. Check connection" else "Ops! Something went wrong"
                                )
                            }
                        }
                        //loadState.refresh is LoadState.NotLoading -> Unit
                        loadState.refresh is LoadState.Loading -> {
                            item { Loading() }
                        }
                        loadState.refresh is LoadState.Error -> {
                            item {
                                ErrorCard(
                                    message = if ((loadState.refresh as LoadState.Error).error is IOException)
                                        "Unable to connect. Check connection" else "Ops! Something went wrong"
                                )
                            }
                        }

                        //loadState.prepend is LoadState.NotLoading -> {}
                        loadState.append is LoadState.Loading -> {
                            item { Loading() }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                modifier = Modifier.align(TopCenter)
            )
        }
    }

}

@Composable
fun HouseItemCard(
    modifier: Modifier = Modifier,
    house: House,
    onItemClick: () -> Unit
){
    Card(
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
                fontSize = 20.sp,
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
                        colorFilter = ColorFilter.tint(
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    )
                    Text(
                        text = house.region,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (house.words.isNotEmpty()) {
                    Row(modifier = modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.quotes),
                            contentDescription = QUOTE_DESCRIPTION,
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondaryContainer)
                        )
                        Text(
                            text = house.words,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = modifier.fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

            }
        }

    }
}

@Composable
private fun ErrorCard(message: String){
    Box(
        modifier = Modifier
            .wrapContentHeight(Top)
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

@Composable
private fun Loading(modifier: Modifier = Modifier){
    Box(modifier = modifier
        .height(50.dp)
        .fillMaxWidth()
        .padding(10.dp),
        TopCenter
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onTertiary
        )
    }

}
