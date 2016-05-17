HTML Unit Scraper
=================

This project uses HTML Unit to scrape data from websites and allow sending relevant portions of the scraped data via multiple notification channels. 

Scrapers 
-------------

#### SBI Exchange Rate Scraper

This scraper extracts the currency exchange rates from the SBI UK site.

To use it pass the JVM arg -Dscraper=scraperImplSBI (case-sensitive)

#### Bin Day Scraper

This scraper extracts the bin info from the relevant gov website.

To use it pass the JVM arg -Dscraper=scraperImplBins (case-sensitive)


Configuration 
-------------

#### Proxy
In `application.properties` to enable and configure the proxy use the following properties:

    proxy.enabled=true
    http-proxy.hostname=proxy-host
    http-proxy.port=proxy-port

This proxy will be used both by HTML unit as well as for slack notifications.

Notification Channels 
-------------

#### E-mail Notifications
In `application.properties` to enable and configure email notifications use the following properties:

    gmail.username=a-gmail-id
    gmail.password=gmail-password
    mail.notification.recipients=comma-separated-recipient-list

#### Prowl Notifications
In `application.properties` to enable and configure prowl notifications use the following properties:

    prowl.api.key=prowl-api-key (See below)
    prowl.notification.launch.url=The URL that is launched when the push notification is opened

#### Slack Notifications
In `application.properties` to enable and configure prowl notifications use the following properties:

    slack.notifications.enabled=true/false
    slack.bot.token=slack bot token
    slack.notificaiton.channel=notifications

See https://api.slack.com/bot-users to setup a bot (and get the token)

Usage 
-------------

Build the project:
`mvn clean install`
 
 Use the executable jar like so:
 `java -cp "full-path-to/sbi.automation-1.0-SNAPSHOT-executable.jar"  com.ash.automation.Main -Dscraper=scraperImplSBI`


##### Footnotes
  
 [Prowl](http://prowlapp.com/) is the Growl client for iOS.
   Push to your iPhone, iPod touch, or iPad notifications from a Mac or
   Windows computer, or from a multitude of apps and services.
   
 [HtmlUnit](http://htmlunit.sourceforge.net/) is a "GUI-Less browser for Java programs". 
    It models HTML documents and provides an API that allows you to invoke pages, fill out forms, 
    click links, etc... just like you do in your "normal" browser
