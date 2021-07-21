package networking.volley

import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

/**
 * Based on the author's blog post https://blog.dev-area.net/2015/08/13/android-4-1-enable-tls-1-1-and-tls-1-2/
 *
 *
 * Android API 16-19 supports TLS 1.2 (and 1.1) but is disabled by default. This is the best way to enable TLS 1.2 in
 * our network stack.
 */
class TLSSocketFactory @Throws(KeyManagementException::class, NoSuchAlgorithmException::class)
constructor() : SSLSocketFactory() {

  private val internalSSLSocketFactory : SSLSocketFactory

  init {
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, null, null)
    internalSSLSocketFactory = sslContext.socketFactory
  }

  override fun getDefaultCipherSuites() : Array<String> = internalSSLSocketFactory.defaultCipherSuites

  override fun getSupportedCipherSuites() : Array<String> = internalSSLSocketFactory.supportedCipherSuites

  @Throws(IOException::class)
  override fun createSocket() : Socket? = enableTLSOnSocket(internalSSLSocketFactory.createSocket())

  @Throws(IOException::class)
  override fun createSocket(s : Socket, host : String, port : Int, autoClose : Boolean) : Socket? {
    return enableTLSOnSocket(internalSSLSocketFactory.createSocket(s, host, port, autoClose))
  }

  @Throws(IOException::class)
  override fun createSocket(host : String, port : Int) : Socket? {
    return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port))
  }

  @Throws(IOException::class)
  override fun createSocket(host : String, port : Int, localHost : InetAddress, localPort : Int) : Socket? {
    return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port, localHost, localPort))
  }

  @Throws(IOException::class)
  override fun createSocket(host : InetAddress, port : Int) : Socket? {
    return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port))
  }

  @Throws(IOException::class)
  override fun createSocket(address : InetAddress, port : Int, localAddress : InetAddress, localPort : Int) : Socket? {
    return enableTLSOnSocket(internalSSLSocketFactory.createSocket(address, port, localAddress, localPort))
  }

  private fun enableTLSOnSocket(socket : Socket?) : Socket? {
    (socket as? SSLSocket)?.let { it.enabledProtocols = arrayOf("TLSv1.1", "TLSv1.2", "TLSv1.3") }
    return socket
  }
}