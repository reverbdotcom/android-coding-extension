package com.reverb.android.onsite

import android.app.Application
import networking.volley.VolleyFacade
import networking.GraphQLWrapper
import networking.ImageLoader
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@MyApplication)

      modules(
        module {
          single { VolleyFacade(androidContext()) }
          single { GraphQLWrapper(get(), androidContext()) }
          single { ImageLoader() }
        }
      )
    }
  }
}