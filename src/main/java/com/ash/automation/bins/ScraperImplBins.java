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

package com.ash.automation.bins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ash.automation.Scraper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/**
 * Created by az on 16/05/2016.
 */
@Component
public class ScraperImplBins implements Scraper {

	public static final Logger logger = LoggerFactory.getLogger(ScraperImplBins.class);
	private String URL = "https://wastemanagementcalendar.cardiff.gov.uk/AddressSearch.aspx?ScriptManager1=UpdatePanel1%7CbtnSearch&TextBoxWatermarkExtender1_ClientState=&__ASYNCPOST=true&__EVENTARGUMENT=&__EVENTTARGET=&__EVENTVALIDATION=%2FwEdAAVbLqZr3OQRIt3uMGshH%2Bfsmo%2BdHUlcYdaxxI%2FU%2FS9ZXW8rMPcp2uUNKS9mSvt%2BTTCO1N1XNFmfsMXJasjxX85jjtvMmEKuzieXB%2FWRITu4EPd%2BBbX8J81se59eAiB4t6RGGzWlq8UbnyVqy5phken9&__LASTFOCUS=&__PREVIOUSPAGE=vRoET5o8n9C72_frMgxzVi5rRPjjygE2Lf6Mu9XYsXMnVtLKTQ_x0QyfCZC8r-VzGoLnFYxKtlu0v9TV56TSwmTR8EwAhlJYz0NRO2IdUHI1&__VIEWSTATE=%2FwEPDwUJNDg0NTk5NjYwD2QWAgIBD2QWBgIDD2QWAmYPZBYEAgkPDxYCHgtQb3N0QmFja1VybAUOfi9FbmdsaXNoLmFzcHhkZAILD2QWAmYPZBYCAgMPEGRkFgBkAgcPPCsAEQEMFCsAAGQCCQ9kFgJmD2QWAgIBD2QWAmYPZBYCAgEPZBYCAgEPZBYCZg9kFgICAw9kFgICAQ9kFgICAQ9kFgJmD2QWBAIHD2QWAgIDDxBkZBYBZmQCCQ9kFgICAw8QZGQWAWZkGAEFCUdyaWRWaWV3MQ9nZG2B9CXjxCBmF2k0CYKrGHxtaw%2BsKQzpNecn6k12fSD0&__VIEWSTATEGENERATOR=B98B31EF&btnSearch=Search&txtAddress=CF236DN";

	public String scrapeForData(WebClient webClient) throws Exception {
		logger.info("Navigating to {} ", URL);
		webClient.getOptions().setJavaScriptEnabled(true);

		final HtmlPage page = webClient.getPage(URL);
		DomElement addressDropdownElement = page.getElementById("droAddress");

		if (addressDropdownElement instanceof HtmlSelect){
			HtmlSelect addressDropDown = (HtmlSelect) addressDropdownElement;

			HtmlOption option = addressDropDown.getOptionByValue("100100044053~~~~~318816~180272");
			addressDropDown.setSelectedAttribute(option, true);
		}

		synchronized(page) {
			page.wait(10000);  // How often to check
		}

//		HtmlTable dataTable = page.getFirstByXPath("//div[@id='htmlWaste']//table[@width='100%']");
		HtmlTableRow nextBinDayRow = page.getFirstByXPath("//div[@id='htmlWaste']//table[@class='border']//tr[2]");
		String data = nextBinDayRow.asText().replaceAll("\\t+",", ").replaceAll("\\n", " ");
		logger.info("\nData: \n**************\n" + data + "\n************");

		return data;
	}

	public String getNotificationSubject() {
		return "Next Bin day:";
	}
}
