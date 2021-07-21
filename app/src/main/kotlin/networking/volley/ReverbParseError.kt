package networking.volley

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import org.koin.core.component.KoinApiExtension

/**
 * Class for wrapping a Volley [ParseError] so we can add more specific logging and
 * always expect [ReverbApiError] types from our requests.
 */
@KoinApiExtension
class ReverbParseError(parseError : ParseError) : ReverbApiError(parseError) {

  constructor(response : NetworkResponse) : this(ParseError(response))
  constructor(throwable : Throwable) : this(ParseError(throwable))

  override val message : String = "Error parsing network error"
}