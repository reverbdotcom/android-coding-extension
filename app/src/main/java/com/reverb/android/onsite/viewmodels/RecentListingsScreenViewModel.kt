package com.reverb.android.onsite.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reverb.android.onsite.Android_Onsite_RecentListingsQuery
import com.reverb.android.onsite.data.repository.RecentListingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RecentListingsScreenViewModel(
  private val listingsRepository: RecentListingsRepository,
): ViewModel() {

  init {
    fetchRecentListings()
  }

  val listings = MutableStateFlow<List<Android_Onsite_RecentListingsQuery.Data.ListingsSearch.Listing>>(listOf())

  private fun fetchRecentListings() = viewModelScope.launch {
    val fetchedListings = listingsRepository.fetchRecentListings().orEmpty()
    listings.value = fetchedListings
  }
}