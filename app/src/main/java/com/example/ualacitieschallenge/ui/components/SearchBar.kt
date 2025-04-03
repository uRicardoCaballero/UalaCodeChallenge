package com.example.ualacitieschallenge.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ualacitieschallenge.R

@Composable
fun SearchBar(
    onQueryChanged: (String) -> Unit,
    showOnlyFavorites: Boolean,
    onShowOnlyFavoritesChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = "",
            onValueChange = onQueryChanged,
            label = { Text(stringResource(R.string.search_cities)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(R.string.show_only_favorites),
                modifier = Modifier.padding(end = 8.dp)
            )
            Switch(
                checked = showOnlyFavorites,
                onCheckedChange = onShowOnlyFavoritesChanged
            )
        }
    }
}