package com.reverb.android.onsite

import android.app.Application
import networking.volley.VolleyFacade
import networking.ReverbApi
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
          single { ReverbApi(get(), androidContext()) }
          single { ImageLoader() }
        }
      )
    }
  }
}