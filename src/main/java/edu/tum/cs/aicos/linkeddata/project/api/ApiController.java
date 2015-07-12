package edu.tum.cs.aicos.linkeddata.project.api;

import com.hp.hpl.jena.query.* ;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;

@RestController
@RequestMapping("/api/")
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping(value = "/movie")
    public Movie LoadMovie(@RequestParam(value = "titel", defaultValue = "") String titel) {
        logger.debug("Loading movie from Linkedmdb...");

        String model = "http://data.linkedmdb.org/sparql";

        String queryString =
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                        "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                        "\n" +
                        "SELECT ?uri ?name ?runtime ?publicationDate ?genre\n" +
                        "WHERE {\n" +
                        "?uri rdfs:label \"" + titel + "\".\n" +
                        "?uri rdfs:label ?name.\n" +
                        "?uri dc:date ?publicationDate.\n" +
                        "Optional {?uri movie:genre ?linkgenre.\n" +
                        "?linkgenre movie:film_genre_name ?genre.}\n" +
                        "?uri movie:runtime ?runtime.\n" +
                        "}\n" +
                        "LIMIT 1";

        Movie movie = new Movie();

        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(model, queryString)) {
            if(qexec!=null){
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.nextSolution();

                //RDFNode x = soln.get("varName");       // Get a result variable by name.
                //Resource r = soln.getResource("VarR"); // Get a result variable - must be a resource
                Literal l = soln.getLiteral("name");   // Get a result variable - must be a literal
                movie.setLabel(l.getLexicalForm());
                Resource x = soln.getResource("uri");   // Get a result variable - must be a literal
                movie.setUri(x.getURI());
                Literal a = soln.getLiteral("publicationDate");   // Get a result variable - must be a literal
                movie.setPublicationDate(a.getString());
                /*try {
                    Literal b = soln.getLiteral("genre");   // Get a result variable - must be a literal
                    movie.setGenre(b.getString());
                }catch (Exception e) {
                    movie.setGenre("");
                }*/
                Literal c = soln.getLiteral("runtime");   // Get a result variable - must be a literal
                movie.setRuntime(c.getString());
            }}
        }

        return movie;
    }

    @RequestMapping(value = "/actor")
    public Actor LoadActor(@RequestParam(value = "name", defaultValue = "") String name) {
        logger.debug("Loading actor from Linkedmdb...");

        String model = "http://data.linkedmdb.org/sparql";

        String queryString =
                "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                        "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                        "\n" +
                        "Select ?actor ?actorname ?movie ?moviename\n" +
                        "WHERE {\n" +
                        "?actor movie:actor_name \"" + name + "\".\n" +
                        "?actor movie:actor_name ?actorname.\n" +
                        "?movie movie:actor ?actor.\n" +
                        "?movie dc:title ?moviename.\n" +
                        "}";

        Actor actor = new Actor();
        Movies movies = new Movies();

        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(model, queryString)) {
            if(qexec!=null){
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.nextSolution();

                Literal l = soln.getLiteral("actorname");   // Get a result variable - must be a literal
                actor.setLabel(l.getLexicalForm());
                Resource x = soln.getResource("actor");   // Get a result variable - must be a literal
                actor.setUri(x.getURI());

                Movie z=new Movie();
                Resource f = soln.getResource("movie");   // Get a result variable - must be a literal
                z.setUri(f.getURI());
                Literal a = soln.getLiteral("moviename");   // Get a result variable - must be a literal
                z.setLabel(a.getLexicalForm());
                movies.add(z);
            }}
        }
        actor.setMovies(movies);

        return actor;
    }

    @RequestMapping(value = "/song")
    public Song LoadSong(@RequestParam(value = "name", defaultValue = "") String name) {
        logger.debug("Loading song from Linkedmdb...");
        String model = "http://data.linkedmdb.org/sparql";

        String queryString =
                "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                        "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                        "\n" +
                        "Select ?song ?songname ?movie ?moviename ?interpretname\n" +
                        "WHERE {\n" +
                        "?song movie:film_featured_song_name \"" + name + "\".\n" +
                        "?song movie:film_featured_song_name ?songname.\n" +
                        "?song movie:film_featured_song_performed_by ?interpretname.\n" +
                        "?movie movie:film_featured_song ?song.\n" +
                        "?movie dc:title ?moviename.\n" +
                        "}";

        Song song = new Song();
        Movies movies = new Movies();

        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(model, queryString)) {
            if(qexec!=null){
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.nextSolution();

                Literal l = soln.getLiteral("songname");   // Get a result variable - must be a literal
                song.setLabel(l.getLexicalForm());
                Literal j = soln.getLiteral("interpretname");   // Get a result variable - must be a literal
                song.setInterpretName(j.getLexicalForm());
                Resource x = soln.getResource("song");   // Get a result variable - must be a literal
                song.setUri(x.getURI());

                Movie z=new Movie();

                Resource f = soln.getResource("movie");   // Get a result variable - must be a literal
                z.setUri(f.getURI());
                Literal a = soln.getLiteral("moviename");   // Get a result variable - must be a literal
                z.setLabel(a.getLexicalForm());
                movies.add(z);
            }}
        }
        song.setMovies(movies);

        return song;
    }


    @RequestMapping(value = "/latestSelenium")
    public String LoadLatestMoviesSelenium() {
        Webcrawler crawler = new Webcrawler();
        try {
            return crawler.getLatestMovies();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "APIFehler";

        }

    }
    @RequestMapping(value = "/latest")
    public NewsString LoadLatestMovies() {
       HtmlUnitCrawler crawler = new HtmlUnitCrawler();
        try {
            return crawler.getLatestMovies();
        } catch (InterruptedException e) {
            e.printStackTrace();
            NewsString newsString=new NewsString();
            newsString.setEntries("failed");
            return newsString;
            //return "APIFehler";

        }

    }

    @RequestMapping(value = "/youtube")
    public YoutubeVideo LoadYoutubeID(@RequestParam(value = "watch", defaultValue = "disraeli") String name) throws InterruptedException {

        HtmlUnitCrawler crawler = new HtmlUnitCrawler();
        String id= crawler.getYoutubeVideo(name);
        YoutubeVideo vid=new YoutubeVideo(id);

        return vid;

    }

    @RequestMapping(value = "/personpic")
    public Bild LoadActorPic(@RequestParam(value = "name", defaultValue = "Megan Fox") String name) throws InterruptedException {
        HtmlUnitCrawler crawler = new HtmlUnitCrawler();
        Bild pic=new Bild(crawler.getPersonPic(name));
        pic.setInfos(crawler.getPersonInformation(name));
        return pic;

    }



    @RequestMapping(value = "/moviecover")
    public Bild LoadMovieCover(@RequestParam(value = "name", defaultValue = "Sucker Punch") String name) throws InterruptedException {

        HtmlUnitCrawler crawler = new HtmlUnitCrawler();
        Bild pic=new Bild(crawler.getMovieCover(name));

        return pic;

    }

    @RequestMapping(value = "/imdb")
    public NewsString LoadIMDB(@RequestParam(value = "name", defaultValue = "Sucker Punch") String name) throws InterruptedException {

        HtmlUnitCrawler crawler = new HtmlUnitCrawler();
        NewsString n = new NewsString();
        n.setEntries(crawler.getMovieRatingIMDB(name));

        return n;
    }

    @RequestMapping(value = "/tomato")
    public NewsString LoadTomato(@RequestParam(value = "name", defaultValue = "Sucker Punch") String name) throws InterruptedException {

        HtmlUnitCrawler crawler = new HtmlUnitCrawler();
        NewsString n = new NewsString();
        n.setEntries(crawler.getMovieRatingTomato(name));

        return n;
    }

}