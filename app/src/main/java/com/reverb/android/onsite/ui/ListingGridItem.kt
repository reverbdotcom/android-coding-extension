package com.reverb.android.onsite.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reverb.android.onsite.Android_Onsite_RecentListingsQuery

@Composable
fun ListingGridItem(
  listing: Android_Onsite_RecentListingsQuery.Data.ListingsSearch.Listing,
  modifier: Modifier = Modifier,
) {
  ElevatedCard(modifier = modifier) {
    Column {
      AsyncImage(
        model = listing.images?.firstOrNull()?.source,
        contentDescription = listing.title,
        modifier = Modifier.aspectRatio(1f)
      )

      Text(
        text = listing.title.orEmpty(),
        modifier = Modifier.padding(12.dp),
        maxLines = 3,
        minLines = 3,
      )

      Text(
        text = listing.pricing?.buyerPrice?.display.orEmpty(),
        modifier = Modifier.padding(12.dp)
      )

    }
  }
}

@Preview
@Composable
private fun ListingGridItemPreview() {
  ListingGridItem(
    Android_Onsite_RecentListingsQuery.Data.ListingsSearch.Listing(
      id = "id",
      title = "title",
      images = listOf(),
      pricing = Android_Onsite_RecentListingsQuery.Data.ListingsSearch.Listing.Pricing(
        Android_Onsite_RecentListingsQuery.Data.ListingsSearch.Listing.Pricing.BuyerPrice(
          display = "$123"
        )
      )
    )
  )
}