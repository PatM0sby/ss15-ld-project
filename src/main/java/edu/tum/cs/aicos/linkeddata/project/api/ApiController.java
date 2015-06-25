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
                //Literal b = soln.getLiteral("genre");   // Get a result variable - must be a literal
                //movie.setGenre(b.getString());
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
                        "Select ?actor ?actorname ?moviename\n" +
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
                Literal a = soln.getLiteral("moviename");   // Get a result variable - must be a literal
                movies.add(LoadMovie(a.getString()));
            }}
        }
        actor.setMovies(movies);

        return actor;
    }

    @RequestMapping(value = "/song")
    public Song LoadSong(@RequestParam(value = "name", defaultValue = "Shout!") String name) {
        logger.debug("Loading song from Linkedmdb...");
        String model = "http://data.linkedmdb.org/sparql";

        String queryString =
                "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                        "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                        "\n" +
                        "Select ?song ?songname ?moviename ?interpretname\n" +
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
                Literal a = soln.getLiteral("moviename");   // Get a result variable - must be a literal
                movies.add(LoadMovie(a.getString()));
            }}
        }
        song.setMovies(movies);

        return song;
    }

    @RequestMapping(value = "/everything")
    public Everything LoadEverything(@RequestParam(value = "titel", defaultValue = "") String titel) {
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
                        ""+
                        "}\n" +
                        "LIMIT 1";

        Everything everything = new Everything();

        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(model, queryString)) {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.nextSolution();

                //RDFNode x = soln.get("varName");       // Get a result variable by name.
                //Resource r = soln.getResource("VarR"); // Get a result variable - must be a resource
                Literal l = soln.getLiteral("name");   // Get a result variable - must be a literal
                everything.setLabel(l.getLexicalForm());

                Resource x = soln.getResource("uri");   // Get a result variable - must be a literal
                everything.setUri(x.getURI());
                Literal a = soln.getLiteral("publicationDate");   // Get a result variable - must be a literal
                everything.setPublicationDate(a.getString());
                //Literal b = soln.getLiteral("genre");   // Get a result variable - must be a literal
                //movie.setGenre(b.getString());
                Literal c = soln.getLiteral("runtime");   // Get a result variable - must be a literal
                everything.setRuntime(c.getString());
            }
        }
        if (everything.getRuntime().isEmpty()||everything.getRuntime().equals("")){

            queryString =
                    "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                            "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                            "\n" +
                            "Select ?song ?songname ?moviename ?interpretname\n" +
                            "WHERE {\n" +
                            "?song movie:film_featured_song_name \"" + titel + "\".\n" +
                            "?song movie:film_featured_song_name ?songname.\n" +
                            "?song movie:film_featured_song_performed_by ?interpretname.\n" +
                            "?movie movie:film_featured_song ?song.\n" +
                            "?movie dc:title ?moviename.\n" +
                            "}";

            everything = new Everything();
            Movies movies = new Movies();

            try (QueryExecution qexec = QueryExecutionFactory.sparqlService(model, queryString)) {
                ResultSet results = qexec.execSelect();
                for (; results.hasNext(); ) {
                    QuerySolution soln = results.nextSolution();

                    Literal l = soln.getLiteral("songname");   // Get a result variable - must be a literal
                    everything.setLabel(l.getLexicalForm());
                    Literal j = soln.getLiteral("interpretname");   // Get a result variable - must be a literal
                    everything.setInterpretName(j.getLexicalForm());
                    Resource x = soln.getResource("song");   // Get a result variable - must be a literal
                    everything.setUri(x.getURI());
                    Literal a = soln.getLiteral("moviename");   // Get a result variable - must be a literal
                    movies.add(LoadMovie(a.getString()));
                }
            }
            everything.setMovies(movies);

            if (everything.getInterpretName().isEmpty()) {

                queryString =
                        "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                                "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                                "\n" +
                                "Select ?actor ?actorname ?moviename\n" +
                                "WHERE {\n" +
                                "?actor movie:actor_name \"" + titel + "\".\n" +
                                "?actor movie:actor_name ?actorname.\n" +
                                "?movie movie:actor ?actor.\n" +
                                "?movie dc:title ?moviename.\n" +
                                "}";

                everything = new Everything();
                movies = new Movies();

                try (QueryExecution qexec = QueryExecutionFactory.sparqlService(model, queryString)) {
                    ResultSet results = qexec.execSelect();
                    for (; results.hasNext(); ) {
                        QuerySolution soln = results.nextSolution();

                        Literal l = soln.getLiteral("actorname");   // Get a result variable - must be a literal
                        everything.setLabel(l.getLexicalForm());
                        Resource x = soln.getResource("actor");   // Get a result variable - must be a literal
                        everything.setUri(x.getURI());
                        Literal a = soln.getLiteral("moviename");   // Get a result variable - must be a literal
                        movies.add(LoadMovie(a.getString()));
                    }
                }
                everything.setMovies(movies);

            }
        }

        return everything;
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
    public String LoadActorPic(@RequestParam(value = "name", defaultValue = "default") String name) throws InterruptedException {
        HtmlUnitCrawler crawler = new HtmlUnitCrawler();
        return crawler.getPersonPic(name);

    }


}