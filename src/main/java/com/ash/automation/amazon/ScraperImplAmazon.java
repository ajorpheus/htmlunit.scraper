/*
 *
 *  * Copyright 2003-2015 Monitise Group Limited. All Rights Reserved.
 *  *
 *  * Save to the extent permitted by law, you may not use, copy, modify,
 *  * distribute or create derivative works of this material or any part
 *  * of it without the prior written consent of Monitise Group Limited.
 *  * Any reproduction of this material must contain this notice.
 *
 */

package com.ash.automation.amazon;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ash.automation.Scraper;
import com.ash.automation.amazon.products.crud.Product;
import com.ash.automation.amazon.products.crud.ProductRepository;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Created by az on 26/05/2016.
 */
@Component
public class ScraperImplAmazon implements Scraper {
	public static final Logger logger = LoggerFactory.getLogger(ScraperImplAmazon.class);

	@Autowired
	ProductRepository productRepository;

	public String scrapeForData(WebClient webClient) throws Exception {
		String data = "";

		List<Product> productList = productRepository.findAll();
		for (Product product : productList) {
			String productUrl = product.getUrl();
			logger.info("Navigating to {} ", productUrl);

			double currentPrice = getCurrentPrice(webClient, productUrl);

			if (Double.compare(currentPrice, Double.parseDouble(product.getExpectedPrice())) < 0){
				data += String.format("Price of *%s* has dropped to *%s*\n", product.getName(), currentPrice);
				System.out.println(data);
			}
		}

		return data;
	}

	private double getCurrentPrice(WebClient webClient, String ProductUrl) throws java.io.IOException,
			InterruptedException {
		webClient.setJavaScriptEnabled(true);
		final HtmlPage page = webClient.getPage(ProductUrl);

		DomText currentPriceDomText = page.getFirstByXPath("//span[@id='priceblock_ourprice']/text()");
		String currentPrice = removeCurrencySymbol(currentPriceDomText.getTextContent());

		logger.info("\nData: \n**************\n" + currentPrice + "\n************");
		return Double.parseDouble(String.valueOf(currentPrice));
	}

	public String getNotificationSubject() {
		return "Price has changed!";
	}

	public static String removeCurrencySymbol(String moneyString){
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
		String currencySymbol = formatter.getCurrency().getSymbol(Locale.UK);
		return moneyString.replaceAll(currencySymbol, "");
	}

}
