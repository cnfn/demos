package com.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author whilst
 * @date 2017/02/26
 */
@RestController
@EnableAutoConfiguration
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "home";
    }
}
