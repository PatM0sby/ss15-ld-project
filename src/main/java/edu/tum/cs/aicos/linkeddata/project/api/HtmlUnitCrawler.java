package edu.tum.cs.aicos.linkeddata.project.api;

/**
 * Created by Pat on 03.06.2015.
 */
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


import java.io.IOException;

public class HtmlUnitCrawler {
    private String request;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public HtmlUnitCrawler() {
    }

    public HtmlUnitCrawler(String request) {
        this.request = request;
    }

    public String getLatestMovies() throws InterruptedException {

        WebClient browser=new WebClient();
        try {
            HtmlPage page=browser.getPage("http://www.mrmovietimes.com/movies/");
            String pagesource=page.asText();

            return pagesource;
        } catch (IOException e) {
            e.printStackTrace();
        }



        return "fail";}

    public static void main(String[] args) throws InterruptedException {
        String answer;
        HtmlUnitCrawler crawler=new HtmlUnitCrawler();
        answer= crawler.getLatestMovies();
        System.out.println(answer);


    }
}