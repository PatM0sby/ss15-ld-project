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
        ResponseEntity<Movies> responseEntity = browser1.getForEntity(
                "http://127.0.0.1:" + port + "/api/movies", Movies.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Movie> data = responseEntity.getBody();
        assertEquals(1, data.size());

        logger.debug("testMovie end");
    }

    @Test
    public void testActor() throws Exception {
        logger.debug("testActor begin");
        RestTemplate browser1 = new TestRestTemplate();
        ResponseEntity<Actors> responseEntity = browser1.getForEntity(
                "http://127.0.0.1:" + port + "/api/actors", Actors.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Actor> data = responseEntity.getBody();
        assertEquals(1, data.size());

        logger.debug("testActor end");
    }

    @Test
    public void testSong() throws Exception {
        logger.debug("testSong begin");
        RestTemplate browser1 = new TestRestTemplate();
        ResponseEntity<Songs> responseEntity = browser1.getForEntity(
                "http://127.0.0.1:" + port + "/api/songs", Songs.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Song> data = responseEntity.getBody();
        assertEquals(1, data.size());

        logger.debug("testSong end");
    }

}
