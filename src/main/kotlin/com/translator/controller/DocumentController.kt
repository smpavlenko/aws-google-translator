package com.translator.controller

import com.translator.service.DocumentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * @author Sergii Pavlenko
 * @since
 */
@RestController
@RequestMapping("/v1/document")
class DocumentController {


    @GetMapping("/{from}/{to}/{text}")
    fun currencyConverter(@Valid @PathVariable from: String, @Valid @PathVariable to: String, @Valid @PathVariable text: String) {
        //TODO
    }
}