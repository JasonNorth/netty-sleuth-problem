package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;

@Configuration
@EnableAutoConfiguration
public class AsyncRestTemplateConfiguration {
    @Autowired
    private AsyncClientHttpRequestFactory asyncClientHttpRequestFactory;

    @Autowired
    private MyInterceptor myInterceptor;


    @Bean
    public AsyncRestTemplate myAsyncRestTemplate() {
        // this does not give the correct traceid in interceptor
        AsyncRestTemplate asyncRestTemplate = problem();

        // if above is swapped for below then this does give the correct traceid in interceptor
//        AsyncRestTemplate asyncRestTemplate = working();

        asyncRestTemplate.getInterceptors().add(myInterceptor);
        return asyncRestTemplate;
    }

    private AsyncRestTemplate problem() {
        return new AsyncRestTemplate(asyncClientHttpRequestFactory);
    }

    private AsyncRestTemplate working() {
        return new AsyncRestTemplate();
    }
}
