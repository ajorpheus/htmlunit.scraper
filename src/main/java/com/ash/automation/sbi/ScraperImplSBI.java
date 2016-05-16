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

package com.ash.automation.sbi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ash.automation.Scraper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Created by az on 16/05/2016.
 */
@Component
public class ScraperImplSBI implements Scraper {

	public static final Logger logger = LoggerFactory.getLogger(ScraperImplSBI.class);
	private String URL = "http://www.sbiuk.com/personal-banking/personal/nri-services";

	private String extractExchangeRate(String exchangeRateDialogueText) throws Exception {
		Pattern regex = Pattern.compile(".*GBP/INR Rate:\\s*\\nAmount.*\\n.0.*\\n.*\t(.*)\\n.5,001.*", Pattern.DOTALL);
		Matcher regexMatcher = regex.matcher(exchangeRateDialogueText);
		if (regexMatcher.find()) {
			String exchangeRate = regexMatcher.group(1);
			logger.info(exchangeRate);
			return exchangeRate;
		} else {
			logger.error("Exchange Rate not found in " + exchangeRateDialogueText);
			return exchangeRateDialogueText;
		}
	}

	public String scrapeForData(WebClient webClient) throws Exception {
		logger.info("Navigating to {} ", URL);

		final HtmlPage page = webClient.getPage(URL);
		HtmlElement exchangeRateDialogue = page.getElementById("dnn_ctr1270_quicklinks_pnlRate");
		String exchangeRateDialogueText = exchangeRateDialogue.asText();
		String gbpInrExchangeRate = extractExchangeRate(exchangeRateDialogueText);

		logger.info("\nDialogue Text\n**************\n" + exchangeRateDialogueText + "\n************\n\n");
		logger.info("\nGBP/INR Rate: \n**************\n" + gbpInrExchangeRate + "\n************");

		return gbpInrExchangeRate;
	}
}
