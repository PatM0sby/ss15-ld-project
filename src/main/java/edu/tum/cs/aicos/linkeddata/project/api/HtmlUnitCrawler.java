package edu.tum.cs.aicos.linkeddata.project.api;

/**
 * Created by Pat on 03.06.2015.
 */
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;
import org.w3c.dom.html.HTMLAnchorElement;
import org.w3c.dom.html.HTMLHtmlElement;
import org.w3c.dom.html.HTMLImageElement;


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

    public HtmlUnitCrawler() {//test
    }

    public HtmlUnitCrawler(String request) {
        this.request = request;
    }

    public NewsString getLatestMovies() throws InterruptedException {

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
            //{"uri":"http://data.linkedmdb.org/resource/film_featured_song/15"

            answer=answer.substring(0,answer.length()-2);
            String result="received: {\"entries\":\""+answer+"\"}";
                    System.out.println(result);
            NewsString ns=new NewsString();
            ns.setEntries(answer);
            //return result;
            return ns;
            //return answer;
        } catch (IOException e) {
            e.printStackTrace();
        }

        NewsString newsString=new NewsString();
        newsString.setEntries("failed");
        return newsString;
        //return "failed";
        }



    public String getYoutubeVideo(String name){

        System.out.println("Request: "+name);

        name=name.replace(" ", "+");
        String query="http://www.youtube.com/results?search_query="+name+"+trailer";
        System.out.println("Requesting: " + query);
        WebClient browser=new WebClient(BrowserVersion.CHROME);
        browser.getOptions().setThrowExceptionOnScriptError(false);

        try {
            HtmlPage page=browser.getPage(query);
            //System.out.println(page.asXml());
            //*[@id="section-list-692207"]
             //HtmlAnchor element = page.getFirstByXPath("//*[@class=\"item-section\"]/li[1]/div/div/div[2]/h3/a");
            //HtmlAnchor element = page.getFirstByXPath("//*[@class=\"item-section\"]/li[1]/div/div/div[2]/h3/a");
            /*HtmlAnchor element = page.getFirstByXPath("" +
                    "/html/body[@id='body']/div[@id='body-container']/div[@id='page-container']/div[@id='page']/div[@id='content']" +
                    "/div[@class='branded-page-v2-container branded-page-base-bold-titles branded-page-v2-container-flex-width']" +
                    "/div[@class='branded-page-v2-col-container']/div[@class='branded-page-v2-col-container-inner']" +
                    "/div[@class='branded-page-v2-primary-col']/div[@class='   yt-card  clearfix']" +
                    "/div[@class='branded-page-v2-body branded-page-v2-primary-column-content']/div[@id='results']" +
                    "/ol[@class='section-list']/li/ol[@class='item-section']/li[1]/div" +
                    "[@class='yt-lockup yt-lockup-tile yt-lockup-video vve-check clearfix yt-uix-tile']/div[@class='yt-lockup-dismissable']" +
                    "/div[@class='yt-lockup-content']/h3[@class='yt-lockup-title']" +
                    "/a[@class='yt-uix-tile-link yt-ui-ellipsis yt-ui-ellipsis-2 yt-uix-sessionlink     spf-link ']");
                    */
            //HtmlAnchor element = page.ge
            //final String text = element.asText();
            //System.out.println(text);
            Document doc = Jsoup.parse(page.asXml());
            //System.out.println(doc.toString());
            Elements links = doc.select("a[href]");
            Object[]array = links.toArray();

            //Element link= links.
            //System.out.println(links.toString());
            //System.out.println(array[40].toString());

            //String id= array[40].toString().substring(18,29);

            //Skip static page content
            int i=40;
            String id="";
            while(i<=100) {

                //get first video href
                if(array[i].toString().contains("watch?v=")){

                    //extract video ID
                    String [] split=array[i].toString().split("watch\\?v=");
                    String id_and_more=split[1];
                    id=id_and_more.substring(0,11);



                    break;
                }


                i++;
            }
            //String id= array[40].toString().substring(18,29);
            System.out.println("ID: "+id);

            //generate YoutubeLink

            //String youtubelink="https://www.youtube.com/watch?v="+id;
            String youtubelink="https://www.youtube.com/embed/"+id+"?autoplay=0";
            System.out.println("Returned: "+youtubelink);
            return youtubelink;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //*[@id="item-section-676589"]/li[1]/div/div/div[2]/h3/a

    return "http://jsoup.org/cookbook/extracting-data/attributes-text-html";}


    public String getPersonPic(String name) { //name has to be "Firstname Lastname"
        System.out.println("Request: " + name);

        name = name.replace(" ", "_");
        String query = "https://de.wikipedia.org/wiki/" + name;
        System.out.println("Requesting: " + query);
        WebClient browser = new WebClient(BrowserVersion.CHROME);
        browser.getOptions().setThrowExceptionOnScriptError(false);
        try {
            HtmlPage page = browser.getPage(query);
            HtmlImage a = (HtmlImage) page.getByXPath("//*[@id=\"mw-content-text\"]/div[1]/div/a/img").get(0);

            return "https:"+a.getSrcAttribute();
        } catch (Exception e) {
            //e.printStackTrace();
            return "//speakerslounge.com/static/images/no_img.80eb4243b8d1.jpg";

        }
    }

    public String getPersonInformation(String name) { //name has to be "Firstname Lastname"
        System.out.println("Request: " + name);

        name = name.replace(" ", "_");
        String query = "https://de.wikipedia.org/wiki/" + name;
        System.out.println("Requesting: " + query);
        WebClient browser = new WebClient(BrowserVersion.CHROME);
        browser.getOptions().setThrowExceptionOnScriptError(false);
        try {
            HtmlPage page = browser.getPage(query);
            HtmlParagraph a = (HtmlParagraph) page.getByXPath("//*[@id=\"mw-content-text\"]/p[1]").get(0);

            return a.asText();
        } catch (Exception e) {
            //e.printStackTrace();
            return "leider keine Infos :(";

        }
    }

    public String getMovieCover(String name) { //name has to be "Firstname Lastname"
        System.out.println("Request: " + name);

        name = name.replace(" ", "+");
        String query = "http://www.imdb.com/find?ref_=nv_sr_fn&q="+name+"&s=all#tt";
        System.out.println("Requesting: " + query);
        WebClient browser = new WebClient(BrowserVersion.CHROME);
        browser.getOptions().setThrowExceptionOnScriptError(false);
        try {
            HtmlPage page = browser.getPage(query);
            //HtmlImage a = (HtmlImage) page.getByXPath("//*[@id=\"rg_s\"]/div[1]/a/img").get(0);
            HtmlImage a = (HtmlImage) page.getByXPath("//*[@id=\"main\"]/div/div[2]/table/tbody/tr[1]/td[1]/a/img").get(0);
            String imagelink=a.getSrcAttribute();
            String[] image= imagelink.split("_V1_");

            String result=image[0]+"_V1_SX214_AL_.jpg";





            return result;
        } catch (Exception e) {
            //e.printStackTrace();
            return "leider keine Infos :(";

        }
    }
       public static void main (String [] args){

        HtmlUnitCrawler crawler = new HtmlUnitCrawler();
           crawler.getMovieCover("jurassic park");

    }
}


