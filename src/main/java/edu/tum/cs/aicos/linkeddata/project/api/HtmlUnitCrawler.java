package edu.tum.cs.aicos.linkeddata.project.api;

/**
 * Created by Pat on 03.06.2015.
 */
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.*;


import java.io.IOException;
import java.util.List;

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

        WebClient browser=new WebClient(BrowserVersion.CHROME);
        try {
            HtmlPage page=browser.getPage("http://www.showcasecinemas.co.uk/films");
            //String liste =(String) page.getByXPath("").toString();
            //DomElement element = page.getFirstByXPath("//*[@id=\"area-titles\"]/div[2]/a[1]");
            //*[@id="area-titles"]/div[2]/a[2]/a
            int i=1;
            String answer="";
            while(i<=5) {
                HtmlAnchor element = page.getFirstByXPath("//*[@id=\"area-titles\"]/div[2]/a["+i+"]");
                final String text = element.asText();
                answer=answer+text+", ";
                //System.out.println(text);
                i=i+1;

            }
            //*[@id="area-titles"]/div[2]/a[1]/a
            //*[@id="area-titles"]/div[2]/a[5]
            //String pagesource=page.asText();

            return answer;
        } catch (IOException e) {
            e.printStackTrace();
        }



        return "fail";}

    public static void main(String[] args) throws InterruptedException {
        String answer;
        HtmlUnitCrawler crawler=new HtmlUnitCrawler();
        answer= crawler.getLatestMovies();
        //System.out.println(answer+"Success");


    }
}