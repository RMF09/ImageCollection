package com.rmf.imagecollection.presentation.photo_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmf.imagecollection.domain.model.Photo
import com.rmf.imagecollection.domain.repository.UnsplashRepository
import com.rmf.imagecollection.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val photo = savedStateHandle.get<Photo>("photo")
    var isFavorited by mutableStateOf(false)

    init {
        checkIsFavorited()
    }

    fun onEvent(event: PhotoDetailUiEvent) {
        when (event) {
            PhotoDetailUiEvent.OnClickLoveIcon -> {
                if (!isFavorited)
                    addToFavorite()
                else
                    removeFromFavorite()

            }
        }.exhaustive
    }

    private fun checkIsFavorited() {
        if (photo == null)
            return

        viewModelScope.launch {
            unsplashRepository.getFavoritePhoto(photo.id).collect { result ->
                isFavorited = result != null
            }
        }
    }

    private fun addToFavorite() {
        if (photo == null)
            return

        viewModelScope.launch {
            unsplashRepository.addToFavorite(photo = photo)
        }
    }

    private fun removeFromFavorite() {
        if (photo == null)
            return
        viewModelScope.launch {
            unsplashRepository.removeFromFavorite(photo = photo)
        }
    }
}