package com.larscheng.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringCloudHelloHangzhouApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHelloHangzhouApplication.class, args);
    }
    @RequestMapping("/")
    public String home() {
        return "Hello hangzhou";
    }
}
