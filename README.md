SBI UK Automation
================

This project extracts the currency exchange rates from the SBI UK site and sends e-mail and **prowl** notifications (highlighting the GBP/INR exchange rate).

This application uses **htmlunit** as the headless browser.

Configuration 
-------------

#### Proxy
In `application.properties` to enable and configure the proxy use the following properties:

    proxy.enabled=true
    http-proxy.hostname=proxy-host
    http-proxy.port=proxy-port


#### E-mail Notifications
In `application.properties` to enable and configure email notifications use the following properties:

    gmail.username=a-gmail-id
    gmail.password=gmail-password
    mail.notification.recipients=comma-separated-recipient-list

#### Prowl Notifications
In `application.properties` to enable and configure prowl notifications use the following properties:

    prowl.api.key=prowl-api-key (See below)
    prowl.notification.launch.url=The URL that is launched when the push notification is opened

Usage 
-------------
`mvn clean install` 


##### Footnotes
  
 [Prowl](http://prowlapp.com/) is the Growl client for iOS.
   Push to your iPhone, iPod touch, or iPad notifications from a Mac or
   Windows computer, or from a multitude of apps and services.
   
 [HtmlUnit](http://htmlunit.sourceforge.net/) is a "GUI-Less browser for Java programs". 
    It models HTML documents and provides an API that allows you to invoke pages, fill out forms, 
    click links, etc... just like you do in your "normal" browser
