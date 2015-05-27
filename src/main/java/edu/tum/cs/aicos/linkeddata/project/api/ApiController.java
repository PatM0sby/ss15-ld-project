package edu.tum.cs.aicos.linkeddata.project.api;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.query.*;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.math.BigInteger;

@RestController
@RequestMapping("/api/")
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping(value = "/movie")
    public Movie LoadMovie(@RequestParam(value="titel", defaultValue="Man of La Mancha")String titel) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        logger.debug("Loading movie from Linkedmdb...");

        HTTPRepository httpRepository = new HTTPRepository("http://data.linkedmdb.org/sparql");
        httpRepository.initialize();
        //httpRepository.setPreferredTupleQueryResultFormat(TupleQueryResultFormat.JSON);

        RepositoryConnection repositoryConnection = httpRepository.getConnection();

        String query =
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                "\n" +
                "SELECT ?uri ?name ?runtime ?publicationDate ?genre\n" +
                "WHERE {\n" +
                "?uri rdfs:label \""+titel+"\".\n" +
                "?uri rdfs:label ?name.\n" +
                "?uri dc:date ?runtime.\n" +
                "Optional {?uri movie:genre ?linkgenre.\n" +
                "?linkgenre movie:film_genre_name ?genre.}\n" +
                "?uri movie:runtime ?publicationDate.\n" +
                "}\n" +
                "LIMIT 1";
                //System.out.println(query);

            TupleQuery tupleQuery = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL,query);

            Movie movie = new Movie();


            TupleQueryResult tupleQueryResult = tupleQuery.evaluate();

            BindingSet bindingSet = tupleQueryResult.next();

            Binding uriBinding = bindingSet.getBinding("uri");
            URI uri = (URI) uriBinding.getValue();
            movie.setUri(uri.stringValue());

            Binding labelBinding = bindingSet.getBinding("name");
            Literal labelLiteral = (Literal) labelBinding.getValue();
            String labelString = labelLiteral.stringValue();
            movie.setLabel(labelString);

            Binding publicationBinding = bindingSet.getBinding("publicationDate");
            Literal publicationLiteral = (Literal) publicationBinding.getValue();
            String publicationString = publicationLiteral.stringValue();
            movie.setPublicationDate(publicationString);

            Binding genreBinding = bindingSet.getBinding("genre");
            Literal genreLiteral = (Literal) genreBinding.getValue();
            String genreString = genreLiteral.stringValue();
            movie.setGenre(genreString);

            Binding runtimeBinding = bindingSet.getBinding("runtime");
            Literal runtimeLiteral = (Literal) runtimeBinding.getValue();
            BigInteger populationBigInteger = runtimeLiteral.integerValue();
            int runtimeInt = populationBigInteger.intValue();
            movie.setRuntime(runtimeInt);

        tupleQueryResult.close();

        repositoryConnection.close();

        httpRepository.shutDown();

        logger.debug("Loaded movie from LinkedMDB.");

        return movie;
    }

    @RequestMapping(value = "/actor")
    public Actor LoadActor(@RequestParam(value="name", defaultValue="Peter O'Toole")String name) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        logger.debug("Loading actor from Linkedmdb...");

        HTTPRepository httpRepository = new HTTPRepository("http://data.linkedmdb.org/sparql");
        httpRepository.initialize();

        RepositoryConnection repositoryConnection = httpRepository.getConnection();

        TupleQuery tupleQuery = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL,

                "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                "\n" +
                "Select ?actor ?actorname ?moviename\n" +
                "WHERE {\n" +
                "?actor movie:actor_name \""+name+"\".\n" +
                "?actor movie:actor_name ?actorname.\n" +
                "?movie movie:actor ?actor.\n" +
                "?movie dc:title ?moviename.\n" +
                "}"
        );


        TupleQueryResult tupleQueryResult = tupleQuery.evaluate();

        Movies movies=new Movies();
        Actor actor = new Actor();

        if (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();

            Binding actorBinding = bindingSet.getBinding("actor");
            URI uri = (URI) actorBinding.getValue();
            actor.setUri(uri.stringValue());

            Binding actornameBinding = bindingSet.getBinding("actorname");
            Literal actornameLiteral = (Literal) actornameBinding.getValue();
            String actornameString = actornameLiteral.stringValue();
            actor.setLabel(actornameString);

            Binding movieBinding = bindingSet.getBinding("moviename");
            Literal movieLiteral = (Literal) movieBinding.getValue();
            String movieString = movieLiteral.stringValue();
            movies.add(LoadMovie(movieString));
        }

        while (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();

            Binding movieBinding = bindingSet.getBinding("moviename");
            Literal movieLiteral = (Literal) movieBinding.getValue();
            String movieString = movieLiteral.stringValue();
            movies.add(LoadMovie(movieString));
        }

        actor.setMovies(movies);

        tupleQueryResult.close();

        repositoryConnection.close();

        httpRepository.shutDown();

        logger.debug("Loaded actor from LinkedMDB.");

        return actor;
    }

    @RequestMapping(value = "/song")
    public Song LoadSong(@RequestParam(value="name", defaultValue="Nobody Does It Better")String name) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        logger.debug("Loading song from Linkedmdb...");

        HTTPRepository httpRepository = new HTTPRepository("http://data.linkedmdb.org/sparql");
        httpRepository.initialize();

        RepositoryConnection repositoryConnection = httpRepository.getConnection();

        TupleQuery tupleQuery = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL,

                        "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                        "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                        "\n" +
                        "Select ?song ?songname ?moviename ?interpretname\n" +
                        "WHERE {\n" +
                        "?song movie:film_featured_song_name \""+name+"\".\n" +
                        "?song movie:film_featured_song_name ?songname.\n" +
                        "?song movie:film_featured_song_performed_by ?interpretname.\n" +
                        "?movie movie:film_featured_song ?song.\n" +
                        "?movie dc:title ?moviename.\n" +
                        "}"
        );


        TupleQueryResult tupleQueryResult = tupleQuery.evaluate();

        Movies movies=new Movies();
        Song song = new Song();

        if (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();

            Binding songBinding = bindingSet.getBinding("song");
            URI uri = (URI) songBinding.getValue();
            song.setUri(uri.stringValue());

            Binding songnameBinding = bindingSet.getBinding("songname");
            Literal songnameLiteral = (Literal) songnameBinding.getValue();
            String songnameString = songnameLiteral.stringValue();
            song.setLabel(songnameString);

            Binding interpretnameBinding = bindingSet.getBinding("interpretname");
            Literal interpretnameLiteral = (Literal) interpretnameBinding.getValue();
            String interpretnameString = interpretnameLiteral.stringValue();
            song.setInterpretName(interpretnameString);

            Binding movieBinding = bindingSet.getBinding("moviename");
            Literal movieLiteral = (Literal) movieBinding.getValue();
            String movieString = movieLiteral.stringValue();
            movies.add(LoadMovie(movieString));
        }

        while (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();

            Binding movieBinding = bindingSet.getBinding("moviename");
            Literal movieLiteral = (Literal) movieBinding.getValue();
            String movieString = movieLiteral.stringValue();
            movies.add(LoadMovie(movieString));
        }

        song.setMovies(movies);

        tupleQueryResult.close();

        repositoryConnection.close();

        httpRepository.shutDown();

        logger.debug("Loaded song from LinkedMDB.");

        return song;
    }

}

