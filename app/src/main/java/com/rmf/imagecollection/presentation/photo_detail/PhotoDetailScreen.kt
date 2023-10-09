package com.rmf.imagecollection.presentation.photo_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.rmf.imagecollection.R
import com.rmf.imagecollection.domain.model.Photo
import com.rmf.imagecollection.ui.theme.Love

@Destination
@Composable
fun PhotoDetailScreen(
    photo: Photo,
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    val isFavorited = viewModel.isFavorited

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = photo.urls.regular,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 2.8f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            Text(
                text = photo.user.username,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
            IconButton(onClick = { viewModel.onEvent(PhotoDetailUiEvent.OnClickLoveIcon) }) {
                val icon = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                Icon(imageVector = icon, contentDescription = null, tint = Love)
            }
        }
        Text(
            text = photo.description ?: stringResource(id = R.string.text_no_description),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.78f),
            lineHeight = 18.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Attribution : ${photo.user.attributionUrl}",
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.70f),
            lineHeight = 16.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )
    }
}