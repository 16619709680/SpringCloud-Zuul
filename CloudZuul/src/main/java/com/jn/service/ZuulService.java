package com.jn.service;

import org.springframework.stereotype.Service;

@Service
public class ZuulService {


    public String queryZuul() {
        System.out.println("网关请求成功！");
        return "网关请求成功";
    }
}
