package com.reverb.app.core.api.volley

import networking.volley.ReverbApiError
import networking.volley.VolleyResponseListener
import javax.net.ssl.HttpsURLConnection
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

/**
 * [VolleyResponseListener] For using within a suspend function that resumes with either a [Success] or [Failure].
 */
class ContinuationVolleyResponseListener<T : Any>(
  private val continuation : Continuation<Response<T>>
) : VolleyResponseListener<T>() {

  @SuppressWarnings("ExpressionBodySyntax")
  override fun onResponse(response : T, responseStatusCode : Int) {
    continuation.resume(Response.Success(response, responseStatusCode))
  }

  override fun onError(error : ReverbApiError) = continuation.resume(Response.Failure(error))

}

sealed class Response<out T> {
  data class Success<out T>(val data : T, val statusCode : Int = HttpsURLConnection.HTTP_OK) : Response<T>()
  data class Failure(val error : ReverbApiError) : Response<Nothing>()
}

