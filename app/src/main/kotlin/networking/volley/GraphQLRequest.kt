package networking.volley

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


/**
 * Request type for interacting with GraphQL endpoints.
 * @param <ResponseType> Type expected to be parsed on a successful response.
</ResponseType> */
@KoinApiExtension
class GraphQLRequest<ResponseType>(
  private val mResponseTypeClass : Class<ResponseType>,
  graphQLQuery : String,
  variables : Any?,
  listener : VolleyResponseListener<ResponseType>,
  graphQlUrl : String,

) : Request<ResponseType>(Method.POST, graphQlUrl, listener), KoinComponent {

  private val listener : VolleyResponseListener<ResponseType>? = errorListener as VolleyResponseListener<ResponseType>?
  private val postObject : JsonObject = buildPostObject(graphQLQuery, variables)
  private var responseStatusCode = 0

  override fun parseNetworkResponse(response : NetworkResponse) : Response<ResponseType> {
    return try {
      responseStatusCode = response.statusCode
      val cacheEntry = HttpHeaderParser.parseCacheHeaders(response)
      val json = String(response.data, Charset.defaultCharset())
//      val json = String(response.data, HttpHeaderParser.parseCharset(response.headers, ENCODING_UTF_8))
      val responseJson : JsonObject = PARSER.fromJson(json, JsonObject::class.java)
      if (responseJson.getAsJsonArray(KEY_ERRORS) != null) {
        Log.d("GraphQLRequest", "Error fetching with body: $postObject")
        Log.d("GraphQLRequest", responseJson.getAsJsonArray(KEY_ERRORS).toString())
        Response.error(VolleyError(response))
      } else {
        // GraphQL always returns a data block with what you asked for, but they could be null on errors
        val data : JsonElement = responseJson.get(KEY_DATA)
        if (!data.isJsonNull) {
          val responseObject : ResponseType = PARSER.fromJson(data, mResponseTypeClass)
          Response.success(responseObject, cacheEntry)
        } else {
          Response.error(ReverbParseError(response))
        }
      }
    } catch (e : UnsupportedEncodingException) {
      Response.error(ReverbParseError(e))
    } catch (e : JsonSyntaxException) {
      Response.error(ReverbParseError(e))
    } catch (e : OutOfMemoryError) {
      Log.d("GraphQLRequest", "OOM parsing data for class [" + mResponseTypeClass.simpleName + "]", e)
      Response.error(ReverbParseError(e))
    }
  }

  override fun deliverResponse(response : ResponseType) {
    listener?.onResponse(response, responseStatusCode)
  }

  override fun getHeaders() : Map<String, String> {
//    val map : MutableMap<String, String> = defaultHeaders
//    map[HttpHeaders.ACCEPT] = MIME_TYPE_APPLICATION_JSON
//    map[HttpHeaders.CONTENT_TYPE] = MIME_TYPE_APPLICATION_JSON
    return defaultHeaders
  }

  override fun getBody() = PARSER.toJson(postObject).toByteArray(charset(ENCODING_UTF_8))

  private fun buildPostObject(graphQLQuery : String, variables : Any?) : JsonObject {
    val bodyJson = JsonObject()
    bodyJson.addProperty(KEY_QUERY, graphQLQuery)
    if (variables != null) {
      bodyJson.addProperty(KEY_VARIABLES, PARSER.toJson(variables))
    }

    // Server wants the "operationName" for easy logging / debugging (which for our purposes is basically always
    // the named query / mutation in the request since we only do one operation per request) so just parse it out
    // of the structured query.
    val operationName = getOperationName(graphQLQuery)
    if (operationName != null && operationName.isNotEmpty()) {
      bodyJson.addProperty(KEY_OPERATION_NAME, operationName)
    }
    return bodyJson
  }

  private fun getOperationName(graphQLQuery : String) : String? {
    // Graph requests are in the form [query|mutation] OperationName[(params)] { ... }
    // So extract the substring from after the space up to the the first `(` or `{`.

    // Operation name should end either at the first open parenthesis if there are parameters
    // or the open curly brace if there aren't
    val openParenthesis = graphQLQuery.indexOf('(')
    val openCurlyBrace = graphQLQuery.indexOf('{')
    val endIndex = if (openParenthesis in 1 until openCurlyBrace) openParenthesis else openCurlyBrace

    // Operation name should start after either of the `query` or `mutation` keywords
    var beginIndex = graphQLQuery.indexOf("query")
    if (beginIndex == -1 || beginIndex > endIndex) {
      beginIndex = graphQLQuery.indexOf("mutation")
    }
    return if (beginIndex == -1 || endIndex == -1) {
      null
    } else {
      // Operation name starts after the space following the operation type keyword
      beginIndex = graphQLQuery.indexOf(' ', beginIndex)
      graphQLQuery.substring(beginIndex, endIndex).trim { it <= ' ' }
    }
  }

  companion object {
    private const val ENCODING_UTF_8 = "UTF-8"
    private const val KEY_QUERY = "query"
    private const val KEY_VARIABLES = "variables"

    const val RQL_URL = "https://rql.reverb.com/graphql"

    private const val HEADER_KEY_ACCEPT_VERSION = "Accept-Version"
    private const val HEADER_KEY_X_SHIPPING_REGION = "X-Shipping-Region"
    private const val HEADER_KEY_ACCEPT = "Accept"

    private val defaultHeaders = mapOf(
      HEADER_KEY_ACCEPT_VERSION to "3.0",
      HEADER_KEY_X_SHIPPING_REGION to "US_CON",
      HEADER_KEY_ACCEPT to "application/hal+json",
    )

    @VisibleForTesting
    val KEY_OPERATION_NAME = "operationName"
    private const val KEY_DATA = "data"
    private const val KEY_ERRORS = "errors"
    val PARSER : Gson = GsonBuilder().run {
      setFieldNamingStrategy(FieldNamingPolicy.IDENTITY)
      setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
      create()
    }

    inline fun <reified ResponseType> createRqlRequest(
      graphQLQuery : String,
      listener : VolleyResponseListener<ResponseType>,
      variables : Any? = null
    ) : GraphQLRequest<ResponseType> {
      return GraphQLRequest(ResponseType::class.java, graphQLQuery, variables, listener, RQL_URL)
    }
  }
}