package com.ash.sbi;

import com.ash.sbi.spec.EmailService;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SearchForExchangeRate {
    public static final String URL = "http://www.sbiuk.com/personal-banking/personal/nri-services";

    Logger logger = LoggerFactory.getLogger(SearchForExchangeRate.class);

    @Autowired
    EmailService emailService;
    @Autowired
    ProwlNotification prowlNotification;

    @Value("${proxy.enabled}")
    private boolean proxyEnabled;
    @Value("${http-proxy.hostname}")
    private String proxyHostName;
    @Value("${http-proxy.port}")
    private int proxyPort;


    @Value("${email.notifications.enabled}")
    private boolean emailNotificationsEnabled;

    @Value("${prowl.notifications.enabled}")
    private boolean prowlNotificationsEnabled;


    private WebClient webClient;

    private static String extractExchangeRate(String exchangeRateDialogueText) throws Exception {
        Pattern regex = Pattern.compile(".*GBP/INR:\\s*(.*)EUR.*", Pattern.DOTALL);
        Matcher regexMatcher = regex.matcher(exchangeRateDialogueText);
        if (regexMatcher.find()) {
            String exchangeRate = regexMatcher.group(1);
            System.out.println(exchangeRate);
            return exchangeRate;
        } else {
            throw new Exception("Exchange Rate not found in " + exchangeRateDialogueText);
        }
    }

    public static String escape(String s) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch (c) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                case '"':
                    builder.append("&quot;");
                    break;
                case '\n':
                    builder.append("<br>");
                    break;
                // We need Tab support here, because we print StackTraces as HTML
                case '\t':
                    builder.append("&nbsp; &nbsp; &nbsp;");
                    break;
                default:
                    if (c < 128) {
                        builder.append(c);
                    } else {
                        builder.append("&#").append((int) c).append(";");
                    }
            }
        }
        return builder.toString();
    }

    private void setupHtmlUnit() {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
        webClient.setCssEnabled(false);
        webClient.setTimeout(35000);
        webClient.setThrowExceptionOnScriptError(false);

        if (proxyEnabled) {
            logger.info("Proxy is enabled. Using ProxyHost:{} , ProxyPort: {}", proxyHostName, proxyPort);
            ProxyConfig proxyConfig = new ProxyConfig(proxyHostName, proxyPort);
            webClient.setProxyConfig(proxyConfig);
        }
    }

    public void performMagic() throws Exception {
        setupHtmlUnit();
        logger.info("Navigating to {} ", URL);

        final HtmlPage page = webClient.getPage(URL);
        HtmlElement exchangeRateDialogue = page.getElementById("dnn_ctr1270_quicklinks_pnlRate");
        String exchangeRateDialogueText = exchangeRateDialogue.asText();
        String gbpInrExchangeRate = extractExchangeRate(exchangeRateDialogueText);

        logger.info("\nDialogue Text\n**************\n" + exchangeRateDialogueText + "\n************\n\n");
        logger.info("\nGBP/INR Rate: \n**************\n" + gbpInrExchangeRate + "\n************");

        sendNotificationIfEarlierDateAvailable(gbpInrExchangeRate, exchangeRateDialogueText);

        tearDownHtmlUnit();
    }

    private void sendNotificationIfEarlierDateAvailable(String exchangeRate, String exchangeRateDialogueText) throws ParseException {
        logger.debug(
                "\n\n********************************************************\n" +
                        "********************************************************\n" +
                        "Exchange Rate Dialogue Text: {}\n" +
                        "GBP/INR Exchange Rate: {}" +
                        "********************************************************\n" +
                        "********************************************************"
                , new Object[]{exchangeRateDialogueText, exchangeRate});
        System.out.println("\u0007");

        if(prowlNotificationsEnabled)
        {
            logger.info("Prowl notifications are enabled.");
            prowlNotification.send("Exchange Rate ", "GBP/INR: " + exchangeRate, String.format("Exchange Rates:%s", exchangeRateDialogueText));
        }

        if(emailNotificationsEnabled)
        {
            logger.info("Email notifications are enabled.");
            String emailBody = escape(exchangeRateDialogueText);
            emailService.sendEmail(emailBody, "Exchange Rate GBP/INR: " + exchangeRate);
        }

    }

    private void tearDownHtmlUnit() {
        webClient.closeAllWindows();
    }


}
