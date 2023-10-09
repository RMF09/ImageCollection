package com.rmf.imagecollection.presentation.photo_list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.rmf.imagecollection.R
import com.rmf.imagecollection.presentation.NavGraphs
import com.rmf.imagecollection.presentation.destinations.PhotoDetailScreenDestination
import com.rmf.imagecollection.ui.composable.MyTextField
import com.rmf.imagecollection.util.exhaustive
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun PhotoListScreen(
    navigator: DestinationsNavigator,
    viewModel: PhotoListViewModel = hiltViewModel()
) {

    val photos = viewModel.photos.collectAsLazyPagingItems()
    val searchQuery = viewModel.state.searchQuery
    val lazyState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val focusRequest = LocalFocusManager.current

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is PhotoListEvent.NavigateToPhotoDetailScreen ->
                    navigator.navigate(PhotoDetailScreenDestination(photo = event.photo)) {
                        launchSingleTop = true
                    }
            }.exhaustive
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    MyTextField(
                        modifier = Modifier.padding(end = 16.dp),
                        placeholderText = "Search Something..",
                        value = searchQuery,
                        onValueChange = { value ->
                            viewModel.onEvent(PhotoListUiEvent.OnSearchQueryChange(value))
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        lazyState.animateScrollToItem(INDEX_FIRST)
                                    }
                                    viewModel.onEvent(PhotoListUiEvent.OnClickSearch)
                                    focusRequest.clearFocus()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            scope.launch {
                                lazyState.animateScrollToItem(INDEX_FIRST)
                            }
                            viewModel.onEvent(PhotoListUiEvent.OnClickSearch)
                            focusRequest.clearFocus()
                        })
                    )
                })
        }
    ) {
        LazyColumn(
            state = lazyState,
            contentPadding = PaddingValues(vertical = 18.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            items(photos.itemCount) { index ->
                val photo = photos[index]!!
                PhotoItem(
                    photo = photo,
                    modifier = Modifier.clickable {
                        viewModel.onEvent(PhotoListUiEvent.OnClickPhoto(photo = photo))
                    })
            }

            photos.apply {
                when {
                    loadState.source.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.source.refresh is LoadState.NotLoading && photos.itemCount < 1 -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(id = R.string.text_photos_empty),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    loadState.source.refresh is LoadState.Error -> {
                        item {
                            Column(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(id = R.string.text_cant_load_data_request),
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                TextButton(onClick = { photos.retry() }) {
                                    Text(
                                        text = stringResource(id = R.string.text_retry)
                                    )
                                }
                            }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        Log.e("TAG", "PhotoListScreen: append Loading")
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        item {
                            Column(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(id = R.string.text_cant_load_data_request),
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                TextButton(onClick = { photos.retry() }) {
                                    Text(
                                        text = stringResource(id = R.string.text_retry)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

const val INDEX_FIRST = 0