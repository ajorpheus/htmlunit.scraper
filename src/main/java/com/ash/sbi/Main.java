package com.ash.sbi;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by az on 30/11/2014.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-config.xml");
        SearchForExchangeRate searchForExchangeRate = (SearchForExchangeRate) applicationContext.getBean("searchForExchangeRate");

        searchForExchangeRate.performMagic();
    }
}
