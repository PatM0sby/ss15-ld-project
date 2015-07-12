package edu.tum.cs.aicos.linkeddata.project.api;

import edu.tum.cs.aicos.linkeddata.project.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApiControllerTest {

    Logger logger = LoggerFactory.getLogger(ApiControllerTest.class);

    @Value("${local.server.port}")
    protected int port;

    @Test
    public void testMovie() throws Exception {
        logger.debug("testMovie begin");
        RestTemplate browser1 = new TestRestTemplate();
        ResponseEntity<Movie> responseEntity = browser1.getForEntity(
                "http://127.0.0.1:" + port + "/api/movie?titel=man of la mancha", Movie.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Movie data = responseEntity.getBody();
        assertEquals("Man of La Mancha", data.getLabel());
        assertEquals("130",data.getRuntime());

        logger.debug("testMovie end");
    }

    @Test
    public void testActor() throws Exception {
        logger.debug("testActor begin");
        RestTemplate browser1 = new TestRestTemplate();
        ResponseEntity<Actor> responseEntity = browser1.getForEntity(
                "http://127.0.0.1:" + port + "/api/actor?name=Peter O'Toole", Actor.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Actor data = responseEntity.getBody();
        assertEquals("Peter O'Toole", data.getLabel());
        assertEquals("Caligula",data.getMovies().get(5).getLabel());
        assertEquals(51, data.getMovies().size());

        logger.debug("testActor end");
    }

    @Test
    public void testSong() throws Exception {
        logger.debug("testSong begin");
        RestTemplate browser1 = new TestRestTemplate();
        ResponseEntity<Song> responseEntity = browser1.getForEntity(
                "http://127.0.0.1:" + port + "/api/song?name=Nobody Does It Better", Song.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Song data = responseEntity.getBody();
        assertEquals("Nobody Does It Better", data.getLabel());
        assertEquals("Carly Simon", data.getInterpretName());


        logger.debug("testSong end");
    }

    @Test
    public void testPersonpic() throws Exception {
        logger.debug("Personpic begin");
        RestTemplate browser1 = new TestRestTemplate();
        ResponseEntity<Bild> responseEntity = browser1.getForEntity(
                "http://127.0.0.1:" + port + "/api/personpic?name=Moritz Bleibtreu", Bild.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Bild data = responseEntity.getBody();
        assertEquals("https://upload.wikimedia.org/wikipedia/commons/thumb/3/38/Flickr_-_Siebbi_-_Moritz_Bleibtreu_%281%29.jpg/220px-Flickr_-_Siebbi_-_Moritz_Bleibtreu_%281%29.jpg", data.getId());

        logger.debug("Personpic end");
    }
    @Test
     public void testNewsLine() throws Exception {
        logger.debug("testNewsLineApi begin");
        RestTemplate browser1 = new TestRestTemplate();
        ResponseEntity<NewsString> responseEntity = browser1.getForEntity(
                "http://127.0.0.1:" + port + "/api/latest", NewsString.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        NewsString latestMovies = responseEntity.getBody();
        assertTrue(latestMovies.getEntries().contains("failed") == false);


        logger.debug("testNewsLineApi end");
    }

    @Test
    public void testMovieCover() throws Exception {
        logger.debug("testMovieCoverApi begin");
        RestTemplate browser1 = new TestRestTemplate();
        ResponseEntity<Bild> responseEntity = browser1.getForEntity(
                "http://127.0.0.1:" + port + "/api/moviecover?name=The+Matrix", Bild.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Bild img = responseEntity.getBody();
        assertEquals("http://ia.media-imdb.com/images/M/MV5BMTkxNDYxOTA4M15BMl5BanBnXkFtZTgwNTk0NzQxMTE@._V1_SX214_AL_.jpg", img.getId());



        logger.debug("testNewsLineApi end");

    }@Test
     public void testYoutube() throws Exception {
        logger.debug("testYoutubeApi begin");
        RestTemplate browser1 = new TestRestTemplate();
        ResponseEntity<YoutubeVideo> responseEntity = browser1.getForEntity(
                "http://127.0.0.1:" + port + "/api/youtube?watch=The+Matrix", YoutubeVideo.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        YoutubeVideo video = responseEntity.getBody();
        assertEquals("https://www.youtube.com/embed/m8e-FF8MsqU?autoplay=0",video.getId());


        logger.debug("testYoutubeApi end");
    }

}
