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

}