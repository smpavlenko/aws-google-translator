package com.translator

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.context.annotation.Bean
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * @author Sergii Pavlenko
 * @since
 */
@EnableSwagger2
@SpringBootApplication
open class Application : WebMvcConfigurerAdapter() {
    @Bean
    open fun docket(): Docket {
        return Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(this.javaClass.`package`.name))
                .paths(PathSelectors.any())
                .build()
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

