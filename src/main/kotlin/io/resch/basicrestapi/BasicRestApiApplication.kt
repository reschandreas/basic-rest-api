package io.resch.basicrestapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BasicRestApiApplication

fun main(args: Array<String>) {
    runApplication<BasicRestApiApplication>(*args)
}
