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

package com.ash.automation;

import com.gargoylesoftware.htmlunit.WebClient;

/**
 * Created by az on 16/05/2016.
 */
public interface Scraper {
	String scrapeForData(WebClient webClient) throws Exception;
	String getNotificationSubject();
}
