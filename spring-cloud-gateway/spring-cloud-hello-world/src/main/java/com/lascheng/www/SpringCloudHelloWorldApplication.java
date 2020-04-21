package com.lascheng.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringCloudHelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHelloWorldApplication.class, args);
    }
    @RequestMapping("/")
    public String home() {
        return "Hello world";
    }


    @RequestMapping("/test")
    public String test() {
        return "Hello testsssssssssssssssssssssssss!!!!";
    }
}
