package com.reverb.android.onsite.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.HttpEngine
import com.apollographql.ktor.http.KtorHttpEngine
import com.reverb.android.onsite.data.HeaderInterceptor
import com.reverb.android.onsite.data.createApolloClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.reverb.android.onsite")
class RootKoinModule {

  @Single
  fun providesApolloClient(
    headerInterceptor: HeaderInterceptor,
  ): ApolloClient {
    return createApolloClient(headerInterceptor)
  }
}