package networking.volley

import com.android.volley.VolleyError

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