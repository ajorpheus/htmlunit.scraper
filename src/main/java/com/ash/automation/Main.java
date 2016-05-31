package com.ash.automation;

import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by az on 30/11/2014.
 */
public class Main {

    public static void turnOffHtmlUnitLogging(){
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "OFF");
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.StrictErrorReporter").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.host.ActiveXObject").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.html.HtmlScript").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.host.WindowProxy").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache").setLevel(Level.OFF);
    }


    public static void main(String[] args) throws Exception {
        turnOffHtmlUnitLogging();

        String scraperName = System.getProperty("scraper");
        scraperName = StringUtils.defaultIfEmpty(scraperName, "scraperImplBins");
        System.out.println(scraperName);

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        ScrapeForData scrapeForData = (ScrapeForData) applicationContext.getBean("scrapeForData");
        scrapeForData.setScraper( (Scraper) applicationContext.getBean(scraperName));
        scrapeForData.performMagic();
    }
}
