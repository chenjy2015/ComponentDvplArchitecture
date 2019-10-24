package com.example.baselib.http

import java.math.BigInteger
import java.security.KeyStore
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.security.interfaces.RSAPublicKey
import javax.net.ssl.*

class SSLSocketFactoryUtils {
    companion object {
        @Throws(Exception::class)
        fun getSSLSocketFactory(): SSLSocketFactory{
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate>?, authType: String?) {
                        requireNotNull(chain) { "checkServerTrusted:x509Certificate array isnull" }

                        require(chain.isNotEmpty()) { "checkServerTrusted: X509Certificate is empty" }

                        if (!(null != authType && authType.equals("RSA", ignoreCase = true))) {
                            throw CertificateException("checkServerTrusted: AuthType is not RSA")
                        }
                        try {
                            val tmf = TrustManagerFactory.getInstance("X509")
                            tmf.init(null as KeyStore?)
                            for (trustManager in tmf.trustManagers) {
                                (trustManager as X509TrustManager).checkServerTrusted(chain, authType)
                            }
                        } catch (e: Exception) {
                            throw CertificateException(e)
                        }

                        val pubKey = chain[0].publicKey as RSAPublicKey

                        val encoded = BigInteger(1, pubKey.encoded).toString(16)
                        val expected = HttpConstant.PUB_KEY.equals(encoded, ignoreCase = true)

                        if (!expected) {
                            throw CertificateException(
                                "checkServerTrusted: Expected public key: "
                                        + HttpConstant.PUB_KEY + ", got public key:" + encoded
                            )
                        }
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?> {
                        return arrayOfNulls(0)
                    }
                })
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                return sslContext.socketFactory
        }
    }
}