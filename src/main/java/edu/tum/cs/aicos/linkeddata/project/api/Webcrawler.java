package edu.tum.cs.aicos.linkeddata.project.api;

/**
 * Created by Pat on 01.06.2015.
 */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.*;
import java.util.concurrent.TimeUnit;

public class Webcrawler {
    private String request;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Webcrawler() {
    }

    public Webcrawler(String request){
        this.request = request;
    }

    public String getLatestMovies() throws InterruptedException {


        WebDriver browser = new FirefoxDriver();

        try {

            browser.navigate().to("http://www.mrmovietimes.com/movies/");
            browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            int i=1;
            String result="";
            while(i<=20){
            WebElement line =browser.findElement(By.xpath("//*[@id=\"movie-summary-module\"]/div[1]/div["+i+"]/div/h4/a"));
            String answer=line.getText();
                System.out.println(answer);
                result=result+answer+", ";
                i++;

            }
            //*[@id="movie-summary-module"]/div[1]/div[1]/div/h4/a
            //*[@id="movie-summary-module"]/div[1]/div[2]/div/h4/a
            System.out.println(result);
            return result;
        } finally {
            Thread.sleep(2000);
            browser.quit();
            Thread.sleep(2000);
        }

    }

    public static void main(String[] args){

        Webcrawler crawler=new Webcrawler();
        try {
            System.out.println(crawler.getLatestMovies());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }






    public String getYoutubeTrailerLink(String moviename){
    //ToDo returns Link to be embedded into the result site. Sprint3 stuff.

    return moviename;}
}
