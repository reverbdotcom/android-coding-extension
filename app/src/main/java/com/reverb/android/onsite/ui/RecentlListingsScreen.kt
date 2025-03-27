package com.reverb.android.onsite.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.reverb.android.onsite.Android_Onsite_RecentListingsQuery
import com.reverb.android.onsite.viewmodels.RecentListingsScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecentListingsScreen(
  modifier: Modifier = Modifier,
  viewModel: RecentListingsScreenViewModel = koinViewModel(),
) {
  val listings = viewModel.listings.collectAsState()

  RecentListingsScreen(listings.value, modifier)
}

@Composable
private fun RecentListingsScreen(
  listings: List<Android_Onsite_RecentListingsQuery.Data.ListingsSearch.Listing>,
  modifier: Modifier = Modifier,
) {
  LazyVerticalGrid(
    modifier = modifier,
    columns = GridCells.Fixed(2),
  ) {
    items(listings.size) {
      ListingGridItem(listings[it], modifier = Modifier.padding(4.dp))
    }
  }
}