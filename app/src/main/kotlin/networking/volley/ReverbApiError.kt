package networking.volley

import android.text.TextUtils
import android.util.Log
import com.android.volley.VolleyError
import com.google.gson.JsonObject
import networking.volley.GraphQLRequest
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

/**
 * Base class for wrapping [VolleyError] with more specific info.
 */
open class ReverbApiError(
  private val wrappedError : VolleyError
  ) : VolleyError(wrappedError.networkResponse) {

  /**
   * Re-declares the [message] property so it's non-null since we expect to always have a valid error from the APIs.
   */
  override val message : String get() = wrappedError.message ?: ""
  override val cause : Throwable? get() = wrappedError.cause
}