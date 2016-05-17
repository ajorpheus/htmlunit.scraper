package com.ash.automation;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by az on 30/11/2014.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String scraperName = System.getProperty("scraper");
        scraperName = StringUtils.defaultIfEmpty(scraperName, "scraperImplBins");
        System.out.println(scraperName);

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        ScrapeForData scrapeForData = (ScrapeForData) applicationContext.getBean("scrapeForData");
        scrapeForData.setScraper( (Scraper) applicationContext.getBean(scraperName));
        scrapeForData.performMagic();
    }
}
