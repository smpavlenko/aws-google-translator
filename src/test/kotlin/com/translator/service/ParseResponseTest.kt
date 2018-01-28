package com.translator.service

import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class ParseResponseTest {

    @Test
    fun `null`() {
        assertThat(parseResponse(null), equalTo(""))
    }

    @Test
    fun empty() {
        assertThat(parseResponse(""), equalTo(""))
    }

    @Test
    fun `one result`() {
        assertThat(parseResponse("[[[\"Sektion\",\"Section\",null,null,1]],null,\"en\"]"), equalTo("Sektion"))
    }

    @Test
    fun `many results`() {
        assertThat(parseResponse("[[[\"Sektion. \",\"Section.\",null,null,1],[\"Kann. \",\"Can.\",null,null,1],[\"Bedienung. \",\"Service.\",null,null,1],[\"Desktop. \",\"Desktop.\",null,null,3],[\"Genaue Art\",\"Exact type\",null,null,3]],null,\"en\"]"), equalTo("Sektion. Kann. Bedienung. Desktop. Genaue Art"))
    }
}