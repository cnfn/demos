package com.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author zhixiao.mzx
 * @date 2017/06/28
 */
@Component
public class TestBean1 {
    @Autowired
    private TestBean2 testBean2;

    public TestBean1() {
        System.out.println("--------- TestBean1 constructor");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("+++++++++ TestBean1 PostConstruct" );
    }

    @EventListener({ApplicationEvent.class})
    public void contextRefreshedEventListener(ApplicationEvent event) {
        System.out.println("=========== TestBean1 event: " + event.toString());
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("+++++++++ TestBean1 preDestroy");
    }
}
