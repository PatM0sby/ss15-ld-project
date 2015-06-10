package edu.tum.cs.aicos.linkeddata.project.html;

import edu.tum.cs.aicos.linkeddata.project.Application;
import edu.tum.cs.aicos.linkeddata.project.api.Actor;
import edu.tum.cs.aicos.linkeddata.project.api.NewsString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class HtmlControllerTest {

    Logger logger = LoggerFactory.getLogger(HtmlControllerTest.class);

    @Value("${local.server.port}")
    protected int port;


    @Test
    public void testHtmlStatus() throws IOException {
        logger.debug("testHtmlStatus begin");

        URL url = new URL("http://127.0.0.1:" + port + "/");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();

        org.junit.Assert.assertEquals(code, 200);

        logger.debug("testHtmlStatus end");
    }
/*
    @Test
    public void testMovieQuery() throws Exception {
        logger.debug("testMovieQuery begin");

        WebDriver browser = new FirefoxDriver();
        try {

            browser.navigate().to("http://127.0.0.1:" + port + "/");
            browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            String input = "the lord of the rings";
            WebElement textbox=browser.findElement(By.id("input1"));
            textbox.sendKeys(input);
            browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebElement button1 = browser.findElement(By.id("button1"));
            button1.click();
            browser.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

            org.junit.Assert.assertEquals(browser.getPageSource().contains("the lord of the rings"), true);

        } finally {
            browser.quit();
        }

        logger.debug("testMovieQuery end");
    }

    @Test
    public void testSongQuery() throws Exception {
        logger.debug("testSongQuery begin");

        WebDriver browser = new FirefoxDriver();
        try {

            browser.navigate().to("http://127.0.0.1:" + port + "/");
            browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            String input = "Dance";
            WebElement textbox=browser.findElement(By.id("input2"));
            textbox.sendKeys(input);
            browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebElement button1 = browser.findElement(By.id("button2"));
            button1.click();
            browser.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

            org.junit.Assert.assertEquals(browser.getPageSource().contains("the lord of the rings"), true);

        } finally {
            browser.quit();
        }

        logger.debug("testSongQuery end");
    }

    @Test
    public void testActorQuery() throws Exception {
        logger.debug("testActorQuery begin");

        WebDriver browser = new FirefoxDriver();
        try {

            browser.navigate().to("http://127.0.0.1:" + port + "/");
            browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            String input = "Sean Bean";
            WebElement textbox=browser.findElement(By.id("input3"));
            textbox.sendKeys(input);
            browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebElement button1 = browser.findElement(By.id("button3"));
            button1.click();
            browser.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

            org.junit.Assert.assertEquals(browser.getPageSource().contains("the lord of the rings"), true);

        } finally {
            browser.quit();
        }

        logger.debug("testActorQuery end");
    }
*/
    @Test
    public void testHtmlUnitCrawler() throws Exception {
        logger.debug("testHtmlUnitCrawler begin");

        WebDriver browser = new FirefoxDriver();
        try {
            RestTemplate browser1 = new TestRestTemplate();
            ResponseEntity<NewsString> responseEntity = browser1.getForEntity(
                    "http://127.0.0.1:" + port + "/api/latest", NewsString.class);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            NewsString data = responseEntity.getBody();
            String compare= data.getEntries().split(",")[0];
            System.out.println(compare);

            browser.navigate().to("http://www.showcasecinemas.co.uk/films");
            browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            String result=browser.findElement(By.xpath("//*[@id=\"area-titles\"]/div[2]/a[1]/a")).getText();


            assertEquals(compare, result);

            /*logger.debug(textbox.toString());
            browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebElement button1 = browser.findElement(By.id("button1"));
            button1.click();
            browser.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

            org.junit.Assert.assertEquals(browser.getPageSource().contains("the lord of the rings"), true);*/

        } finally {
            browser.quit();
        }
    }

}
