package com.ash.automation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by az on 30/11/2014.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        ScrapeForData scrapeForData = (ScrapeForData) applicationContext.getBean("scrapeForData");

        scrapeForData.performMagic();
    }
}
