package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

@RestController
public class HelloController {
    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @RequestMapping("/go")
    public ListenableFuture<ResponseEntity<String>> go() {
        LOG.info("START");
        return asyncRestTemplate.exchange("http://localhost:8111/get",
                HttpMethod.GET,
                new HttpEntity<String>("params", new HttpHeaders()),
                String.class);
    }
}
