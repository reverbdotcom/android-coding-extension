package networking

import android.content.Context
import networking.volley.Response
import models.ListingsSearchQueryModel
import networking.volley.GraphQLRequest
import networking.volley.VolleyFacade
import java.io.BufferedReader
import java.io.InputStreamReader

class GraphQLWrapper(private val volley : VolleyFacade, private val context : Context) {

  private val listingsSearchQuery by lazy { getQuery("ListingsIndex.graphql") }

  suspend fun getListingsResponse() : Response<ListingsSearchQueryModel> {
    return volley.makeRequestSuspended {
      GraphQLRequest.createRqlRequest(listingsSearchQuery, it)
    }
  }

  private fun getQuery(assetName : String) : String {
    var reader : BufferedReader? = null
    return try {
      val inputStream = context.assets.open(assetName)
      reader = BufferedReader(InputStreamReader(inputStream))
      var line : String?
      val builder = StringBuilder()
      while (reader.readLine().also { line = it } != null) {
        builder.append(line)
      }

      builder.toString()
    } catch (e : Exception) {
      throw RuntimeException("Failed to load $assetName", e)
    } finally {
      reader?.close()
    }
  }
}
