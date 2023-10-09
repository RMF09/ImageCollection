package com.rmf.imagecollection.presentation.photo_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rmf.imagecollection.domain.model.Photo
import com.rmf.imagecollection.domain.repository.UnsplashRepository
import com.rmf.imagecollection.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {

    private val _photos: MutableStateFlow<PagingData<Photo>> = MutableStateFlow(PagingData.empty())
    val photos = _photos.asStateFlow()

    var state by mutableStateOf(PhotoListState())
    val event = MutableSharedFlow<PhotoListEvent>()


    init {
        getPhotos()
    }

    fun onEvent(event: PhotoListUiEvent) {
        when (event) {
            is PhotoListUiEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.value)
            }

            is PhotoListUiEvent.OnClickPhoto -> {
                navigateToPhotoDetail(photo = event.photo)
            }

            PhotoListUiEvent.OnClickSearch -> {
                getPhotos()
            }
        }.exhaustive
    }

    private fun navigateToPhotoDetail(photo: Photo) {
        viewModelScope.launch {
            event.emit(PhotoListEvent.NavigateToPhotoDetailScreen(photo = photo))
        }
    }

    private fun getPhotos() {
        viewModelScope.launch {
            unsplashRepository.getPhotos(searchQuery = state.searchQuery)
                .cachedIn(viewModelScope)
                .collect { result ->
                    _photos.value = result
                }
        }
    }
}