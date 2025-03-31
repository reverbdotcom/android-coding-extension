package com.reverb.android.onsite.data

import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.network.http.HttpInterceptor
import com.apollographql.apollo.network.http.HttpInterceptorChain
import org.koin.core.annotation.Single

@Single
class HeaderInterceptor : HttpInterceptor {
  override suspend fun intercept(
    request: HttpRequest,
    chain: HttpInterceptorChain,
  ): HttpResponse {
    val requestBuilder = request.newBuilder()
    // Headers can change, so don't cache/keep a reference.
    requestBuilder.addHeader("X-Shipping-Region", "US_CON")
    requestBuilder.addHeader("X-Display-Currency", "USD")
    return chain.proceed(requestBuilder.build())
  }
}