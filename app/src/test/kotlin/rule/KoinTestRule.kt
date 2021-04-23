package com.reverb.android.onsite.com

import networking.ApolloWrapper
import networking.ImageLoader
import io.mockk.mockk
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

class KoinTestRule(
  overrideModuleDeclaration : ModuleDeclaration = {},
  private val shouldStartKoin : Boolean = false
) : TestRule {

  private val overrideModule = module(override = true, moduleDeclaration = overrideModuleDeclaration)

  override fun apply(base : Statement, description : Description?) : Statement {
    return object : Statement() {
      override fun evaluate() {
        synchronized(GlobalContext) {
          if (shouldStartKoin) startKoin { modules(defaultTestModule) }

          loadKoinModules(overrideModule)
          try {
            base.evaluate()
          } finally {
            unloadKoinModules(overrideModule)
            if (shouldStartKoin) stopKoin()
          }
        }
      }
    }
  }

  companion object {
    val defaultTestModule =  module {
      single { mockk<ApolloWrapper>() }
      single { mockk<ImageLoader>() }
    }
  }
}