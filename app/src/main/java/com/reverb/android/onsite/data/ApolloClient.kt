package com.reverb.android.onsite.data

import com.apollographql.apollo.ApolloClient

fun createApolloClient(
  headerInterceptor: HeaderInterceptor,
) : ApolloClient {
  return ApolloClient.Builder()
    .serverUrl("https://gql.reverb.com/graphql")
    .addHttpInterceptor(headerInterceptor)
    .build()
}