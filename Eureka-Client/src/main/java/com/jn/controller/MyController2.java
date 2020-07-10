package com.jn.controller;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
@RestController
public class MyController2 {


    @Autowired
    EurekaClient eurekaClient;


    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
     RestTemplate restTemplate;

    AtomicInteger atomicInteger = new AtomicInteger(2);

    @RequestMapping("/client6")
    public Object client6() {

        //ribbon 完成客户端的负载均衡，多滤掉down的节点
        ServiceInstance choose = loadBalancerClient.choose("provider");

        String url = "http://" + choose.getHost() + ":" + choose.getPort() + "/getport";

        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);

        System.out.println(forObject);

        return "OK";
    }

    /**
     * 手动负载
     *
     * @return
     */
    @RequestMapping("/client7")
    public Object client7() {

        List<ServiceInstance> instances = discoveryClient.getInstances("provider");

        //自定义轮询算法

        //随机
        int i = new Random().nextInt(instances.size());

        //轮询算法

       /* int andIncrement = atomicInteger.getAndIncrement();

        int i = andIncrement % instances.size();*/

        //权重算法
        /*for (ServiceInstance s:instances) {
            System.out.println(s.getMetadata());//权重算法
        }*/


        ServiceInstance serviceInstance = instances.get(i);

        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/getport";

        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);

        System.out.println(forObject);

        return serviceInstance.getPort();
    }


    @LoadBalanced
    @RequestMapping("/client8")
    public Object client8() {
        //自动处理URL
        String url = "http://provider/getport";
        String forObject = restTemplate.getForObject(url, String.class);
        System.out.println(forObject);
        return forObject;
    }

}
