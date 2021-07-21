package networking.volley

import com.android.volley.Response
import com.android.volley.VolleyError

/**
 * Convenience for handling Volley success and error responses in one object.
 */
abstract class VolleyResponseListener<T> : Response.Listener<T>, Response.ErrorListener {
  /**
   * Wraps the base [.onErrorResponse] method to provide
   * more specific information about the error that occurred.
   * @param error The error that occurred.
   */
  abstract fun onError(error : ReverbApiError)

  /**
   * @param responseObject Typed instance parsed from the JSON response.
   * @param responseStatusCode HTTP response code returned by the server.
   * TODO: Make abstract when all implementations override this method.
   */
  abstract fun onResponse(responseObject : T, responseStatusCode : Int)

  override fun onResponse(response : T) {
    TODO("Not yet implemented")
  }

  override fun onErrorResponse(error : VolleyError) {
    val apiError : ReverbApiError = if (error is ReverbApiError) {
      error
    } else {
      ReverbApiError(error)
    }

    onError(apiError)
  }

}