package com.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author zhixiao.mzx
 * @date 2017/06/28
 */
@Component
public class TestBean2 {
    public TestBean2() {
        System.out.println("--------- TestBean2 constructor");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("+++++++++ TestBean2 PostConstruct");
    }

    @EventListener({ApplicationEvent.class})
    public void contextRefreshedEventListener(ApplicationEvent event) {
        System.out.println("=========== TestBean2 event: " + event.toString());
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("+++++++++ TestBean2 preDestroy");
    }
}
