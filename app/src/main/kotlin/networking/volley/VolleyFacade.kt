package networking.volley

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.RetryPolicy
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.reverb.app.core.api.volley.ContinuationVolleyResponseListener
import com.reverb.app.core.api.volley.Response
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Provides a simplified, singleton interface to Volley.
 */
class VolleyFacade(context : Context) {

  private val retryPolicy : RetryPolicy get() = DefaultRetryPolicy(10000, 1, 1.0f)
  private var requestQueue : RequestQueue = RequestQueue(
    DiskBasedCache(context.cacheDir), BasicNetwork(HurlStack())
  ).apply { start() }

  suspend fun <T : Any> makeRequestSuspended(
    createRequest : (ContinuationVolleyResponseListener<T>) -> Request<T>
  ) : Response<T> {
    return suspendCancellableCoroutine { continuation ->
      val request = createRequest(ContinuationVolleyResponseListener(continuation))
      request.retryPolicy = retryPolicy
      requestQueue.add(request)

      continuation.invokeOnCancellation { request.cancel() }
    }
  }
}