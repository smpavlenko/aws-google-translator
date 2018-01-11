package com.translator.service

import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * @author Sergii Pavlenko
 * @since
 */
interface DocumentService {
    fun translate(from: String, to: String, text: String): String
}

/**
 * @author Sergii Pavlenko
 * @since
 */
@Service
object DocumentServiceImpl : DocumentService {

    override fun translate(from: String, to: String, text: String): String {
        val httpPost = HttpPost("https://translate.googleapis.com/translate_a/single")
        val nvps = ArrayList<NameValuePair>()
        nvps.add(BasicNameValuePair("client", "gtx"))
        nvps.add(BasicNameValuePair("sl", "en"))
        nvps.add(BasicNameValuePair("tl", "de"))
        nvps.add(BasicNameValuePair("dt", "t"))
        nvps.add(BasicNameValuePair("q", text))
        httpPost.entity = UrlEncodedFormEntity(nvps)

        val httpClient: HttpClient = HttpClientBuilder.create().build()
        val response: HttpResponse = httpClient.execute(httpPost)

        val out = ByteArrayOutputStream()
        response.entity.writeTo(out)
        val responseString = out.toString()

        return parseResponse(responseString)
    }
}

fun parseResponse(response: String?): String {
    //TODO
    return ""
}