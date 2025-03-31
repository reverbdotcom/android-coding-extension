package com.reverb.android.onsite

import android.app.Application
import com.reverb.android.onsite.di.RootKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MyApplication: Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@MyApplication)
      modules(RootKoinModule().module)
    }
  }
}