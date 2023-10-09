package com.rmf.imagecollection.presentation.favorite_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class FavoriteListViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
): ViewModel() {

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos = _photos.asStateFlow()
    val event = MutableSharedFlow<FavoriteEvent>()

    init {
        getPhotos()
    }

    fun onEvent(event: FavoriteUiEvent){
        when(event){
            is FavoriteUiEvent.OnClickPhoto -> {
                navigateToPhotoDetail(photo = event.photo)
            }
        }.exhaustive
    }

    private fun navigateToPhotoDetail(photo: Photo){
        viewModelScope.launch {
            event.emit(FavoriteEvent.NavigatePhotoDetail(photo = photo))
        }
    }

    private fun getPhotos(){
        viewModelScope.launch {
            unsplashRepository.getFavoritePhotos().collect{ result ->
                _photos.value = result
            }
        }
    }
}