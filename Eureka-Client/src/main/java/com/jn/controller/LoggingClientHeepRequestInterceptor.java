package com.jn.controller;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class LoggingClientHeepRequestInterceptor  implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        System.out.println("拦截请求.........");

        System.out.println(httpRequest.getURI());

        ClientHttpResponse execute = clientHttpRequestExecution.execute(httpRequest, bytes);

        System.out.println(execute.getHeaders());

        return execute;
    }
}
