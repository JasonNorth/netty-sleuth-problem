package com.example.demo;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.AsyncClientHttpRequestExecution;
import org.springframework.http.client.AsyncClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.IOException;

@Component
public class MyInterceptor implements AsyncClientHttpRequestInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(MyInterceptor.class);

    private Tracer tracer;

    public MyInterceptor(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public ListenableFuture<ClientHttpResponse> intercept(HttpRequest request, byte[] body, AsyncClientHttpRequestExecution execution) throws IOException {
        ListenableFuture<ClientHttpResponse> result = execution.executeAsync(request, body);
        result.addCallback(new ListenableFutureCallback() {

            @Override
            public void onSuccess(Object result) {
                try (Tracer.SpanInScope ws = tracer.withSpanInScope(tracer.currentSpan())) {
                    LOG.info("SUCCESS");
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable ex) {
                try (Tracer.SpanInScope ws = tracer.withSpanInScope(tracer.currentSpan())) {
                    LOG.info("FAILURE");
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        return result;
    }
}
