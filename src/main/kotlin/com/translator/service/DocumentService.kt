package com.translator.service

import org.springframework.stereotype.Service

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
        // TODO
        return ""
    }
}

