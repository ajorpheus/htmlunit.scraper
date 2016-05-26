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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ash.automation.Scraper;
import com.ash.automation.bins.ScraperImplBins;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Created by az on 26/05/2016.
 */
@Component
public class ScraperImplAmazon implements Scraper {
	public static final Logger logger = LoggerFactory.getLogger(ScraperImplBins.class);
	private List<AmazonProduct> amazonProducts = new ArrayList<AmazonProduct>();

	@PostConstruct
	public void initProductList() {
		amazonProducts.add(
				new AmazonProduct("EVO 500",
			"https://www.amazon.co.uk/Samsung-inch-Solid-State-Drive/dp/B00P73B1E4", 85));
		amazonProducts.add(
				new AmazonProduct("EVO 1TB",
						"https://www.amazon.co.uk/Samsung-inch-Solid-State-Drive/dp/B00P738MUU", 180));
	}

	public String scrapeForData(WebClient webClient) throws Exception {
		String data = "";

		for (AmazonProduct amazonProduct : amazonProducts) {
			String amazonProductUrl = amazonProduct.getUrl();
			logger.info("Navigating to {} ", amazonProductUrl);

			double currentPrice = getCurrentPrice(webClient, amazonProductUrl);

			if (Double.compare(currentPrice, amazonProduct.getExpectedPrice()) < 0){
				data += String.format("Price of *%s* has dropped to *%s*\n", amazonProduct.getName(), currentPrice);
				System.out.println(data);
			}
		}

		return data;
	}

	private double getCurrentPrice(WebClient webClient, String amazonProductUrl) throws java.io.IOException,
			InterruptedException {
		webClient.setJavaScriptEnabled(true);
		final HtmlPage page = webClient.getPage(amazonProductUrl);

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


	class AmazonProduct {
		private String name;
		private String url;

		private double expectedPrice;

		public AmazonProduct(String name, String url, double expectedPrice) {
			this.name = name;
			this.url = url;
			this.expectedPrice = expectedPrice;
		}

		public String getName() {
			return name;
		}

		public String getUrl() {
			return url;
		}

		public double getExpectedPrice() {
			return expectedPrice;
		}

	}
}
