package com.jn.controller;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *
 */
@RestController
public class MyController {

    @Qualifier("eurekaClient")
    @Autowired
    EurekaClient eurekaClient;


    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    LoadBalancerClient loadBalancerClient;




    @RequestMapping("/hi")
    public String hi() {
        return "OK ......";
    }


    @RequestMapping("/client")
    public String cilent() {

        List<String> services = discoveryClient.getServices();
        for (String s : services
        ) {
            System.out.println(s);
        }
        return "OK ......";
    }


    @RequestMapping("/client2")
    public Object client2() {
        return discoveryClient.getInstances("eureka-client");
    }


    @RequestMapping("/client3")
    public Object client3() {
        List<ServiceInstance> instances = discoveryClient.getInstances("eureka-client");
        for (ServiceInstance s : instances) {
            System.out.println(ToStringBuilder.reflectionToString(s));
        }
        return "OK";
    }


    @RequestMapping("/client4")
    public Object client4() {
        //使用服务名找列表
        List<InstanceInfo> instancesByVipAddress = eurekaClient.getInstancesByVipAddress("eureka-client", false);

        for (InstanceInfo instanceInfo : instancesByVipAddress) {
            System.out.println("InstanceInfo:" + instanceInfo);
        }

        if (instancesByVipAddress.size() > 0) {

            InstanceInfo instanceInfo = instancesByVipAddress.get(0);

            if (instanceInfo.getStatus() == InstanceInfo.InstanceStatus.UP) {

                String url = "http://" + instanceInfo.getHostName() + ":" + instanceInfo.getPort() + "/hi";

                System.out.println("URL:" + url);

                RestTemplate restTemplate = new RestTemplate();

                String forObject = restTemplate.getForObject(url, String.class);

                System.out.println("forObject:" + forObject);
            }

        }

        return "OK";
    }

    @RequestMapping("/client5")
    public Object client5() {

        //ribbon 完成客户端的负载均衡，多滤掉down的节点
        ServiceInstance choose = loadBalancerClient.choose("eureka-client");

        String url = "http://"+choose.getHost()+":"+choose.getPort()+"/hi";

        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);

        System.out.println(forObject);

        return "OK";
    }




}
