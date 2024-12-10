package com.example.demo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Configuration
    class WebClientConfig {
        @Value("\${app.webclient.max-in-memory-size}")
        private var maxInMemorySize: Int = 16777216 // 16MB

        /**
         * Creates a WebClient bean with custom exchange strategies.
         *
         * @return the configured WebClient instance
         */
        @Bean
        fun webClient(): WebClient {
            val exchangeStrategies = ExchangeStrategies.builder()
                .codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(maxInMemorySize) }
                .build()

            return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .build()
        }
    }
}