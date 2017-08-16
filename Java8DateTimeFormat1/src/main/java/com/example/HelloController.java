package com.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cnfn
 * @date 2017/08/16
 */
@RestController
public class HelloController {
    @RequestMapping("/")
    public Object index() {
        TestAnnotation testAnnotation = new TestAnnotation();
        testAnnotation.setLocalDate(LocalDate.now());
        testAnnotation.setLocalDateTime(LocalDateTime.now());
        return testAnnotation;
    }
}
