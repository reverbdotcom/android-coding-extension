package com.reverb.android.onsite.data.repository

import com.apollographql.apollo.ApolloClient
import com.reverb.android.onsite.Android_Onsite_RecentListingsQuery
import com.reverb.android.onsite.type.Reverb_search_ListingsSearchRequest_Sort
import org.koin.core.annotation.Single

@Single
class RecentListingsRepository(
  private val apolloClient: ApolloClient,
) {

  suspend fun fetchRecentListings(): List<Android_Onsite_RecentListingsQuery.Data.ListingsSearch.Listing>? {
    return apolloClient
      .query(Android_Onsite_RecentListingsQuery(Reverb_search_ListingsSearchRequest_Sort.CREATED_AT_DESC))
      .execute()
      .data
      ?.listingsSearch
      ?.listings
      ?.filterNotNull()
  }
}