package com.example;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("LocalDate", LocalDate.now());
        map.put("LocalDateTime", LocalDateTime.now());

        map.put("date", new Date());
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));

        return map;
    }
}
