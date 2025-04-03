package com.example.ualacitieschallenge.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.ualacitieschallenge.R

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier // Use Modifier directly here
) {
    IconButton(
        onClick = onToggleFavorite,
        modifier = modifier // Pass the modifier here
    ) {
        Icon(
            painter = painterResource(
                id = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            ),
            contentDescription = stringResource(R.string.toggle_favorite),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}