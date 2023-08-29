package com.lgtm.simplesns

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class SimpleSnsApplication

fun main(args: Array<String>) {
    runApplication<SimpleSnsApplication>(*args)
}
