package networking

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.request.RequestHeaders
import com.reverb.android.onsite.ListingsSearchQuery
import okhttp3.OkHttpClient

class ApolloWrapper {

  @Throws(ApolloException::class)
  suspend fun getListingsResponse() : Response<ListingsSearchQuery.Data> {
    return apolloClient.query(ListingsSearchQuery())
      .requestHeaders(defaultHeaders)
      .toDeferred()
      .await()
  }

  companion object {

    private val defaultHeaders : RequestHeaders by lazy {
      RequestHeaders.Builder()
        .addHeader("X-Shipping-Region", "US_CON")
        .build()
    }

    private val apolloClient : ApolloClient by lazy {
      ApolloClient.builder()
        .serverUrl("https://rql.reverb.com/graphql")
        .okHttpClient(OkHttpClient())
        .build()
    }
  }
}

typealias ListingModel = ListingsSearchQuery.Listing