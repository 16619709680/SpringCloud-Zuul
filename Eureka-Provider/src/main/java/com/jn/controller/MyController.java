package com.jn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

@RestController
public class MyController {

    @Value("${server.port}")
    String port;

    @Autowired
    HealthStatusCheck healthStatusCheck;

    /**
     * 服务上下线
     *
     * @param status
     * @return
     */
    //http://localhost:91/health?status=true
    @GetMapping("/health")
    public String Health(@RequestParam("status") Boolean status) {

        healthStatusCheck.setStatus(status);

        return healthStatusCheck.getStatus().toString();
    }

    @RequestMapping("/getport")
    public String getPort() {
        return "my Port:" + port;
    }


    @RequestMapping("/getmap")
    public Map<String, String> getMap() {
        Map<String, String> age = Collections.singletonMap("age", "18");
        return age;
    }

    @RequestMapping("/getObj")
    public Object getObj() {
        Person person = new Person("张三",18);
        return person;
    }

    @RequestMapping("/getObj2")
    public Object getObj2(String name) {
        Person person = new Person(name,18);
        return person;
    }

    @PostMapping("/postParam")
    public URI postParam(@RequestBody Person person, HttpServletResponse response) throws URISyntaxException {

        URI uri = new URI("http://www.baidu.com/s?wd=" + person.getName());

        return uri;
    }

}
