package com.reverb.android.onsite

import android.app.Application
import networking.ApolloWrapper
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
          single { ApolloWrapper() }
          single { ImageLoader() }
        }
      )
    }
  }
}