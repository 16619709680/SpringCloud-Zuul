package com.jn.controller;

import com.jn.service.ZuulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloudZuulController {


    @Autowired
    ZuulService zuulService;


    @RequestMapping(value = "/queryZuul",method = RequestMethod.GET)
    public String queryZuul(){
        String  zuul = zuulService.queryZuul();
        return zuul;
    }


    @RequestMapping(value = "/auth",method = RequestMethod.GET)
    public String auth(){
        String  zuul = zuulService.queryZuul();
        return zuul;
    }

}
