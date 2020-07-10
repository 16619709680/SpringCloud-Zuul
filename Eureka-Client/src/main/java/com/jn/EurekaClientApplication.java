package com.jn;

import com.jn.controller.LoggingClientHeepRequestInterceptor;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }


    /**
     * 负载均衡算法
     * @return
     */
    @Bean
    public IRule myRule(){

        return new RoundRobinRule();
        //return new RandomRule();
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new LoggingClientHeepRequestInterceptor());
        return restTemplate;
    }

}
