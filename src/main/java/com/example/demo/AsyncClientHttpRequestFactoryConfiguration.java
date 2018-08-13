package com.example.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;

import javax.net.ssl.SSLException;

@Configuration
@EnableAutoConfiguration
public class AsyncClientHttpRequestFactoryConfiguration {
    @Bean
    public AsyncClientHttpRequestFactory clientHttpRequestFactory() throws SSLException {
        return new Netty4ClientHttpRequestFactory();
    }
}
