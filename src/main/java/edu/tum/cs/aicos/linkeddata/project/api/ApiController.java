package edu.tum.cs.aicos.linkeddata.project.api;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.query.*;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/")
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping(value = "/movie")
    public Cities loadGermanCities() throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        logger.debug("Loading list of german cities from DBpedia...");

        // Load list of scientists from DBpedia
        HTTPRepository httpRepository = new HTTPRepository("http://data.linkedmdb.org/sparql");
        httpRepository.initialize();

        RepositoryConnection repositoryConnection = httpRepository.getConnection();

        TupleQuery tupleQuery = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL,
                "SELECT ?cityURI ?city ?populationTotal\n " +
                        "WHERE {\n" +
                        "   ?cityURI <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://schema.org/City> .\n" +
                        "   ?cityURI <http://dbpedia.org/ontology/country> <http://dbpedia.org/resource/Germany> .\n" +
                        "\n" +
                        "   ?cityURI <http://dbpedia.org/ontology/populationTotal> ?populationTotal.\n" +
                        "\n" +
                        "   ?cityURI rdfs:label ?city .\n" +
                        "   FILTER (lang(?city)=\"de\")\n" +
                        "}\n" +
                        "ORDER BY DESC (?populationTotal)\n" +
                        "LIMIT 20");

        Cities cities = new Cities();
        TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
        while (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();

            City city = new City();

            Binding uriBinding = bindingSet.getBinding("cityURI");
            URI uri = (URI) uriBinding.getValue();
            city.setUri(uri.stringValue());

            Binding labelBinding = bindingSet.getBinding("city");
            Literal labelLiteral = (Literal) labelBinding.getValue();
            String labelString = labelLiteral.stringValue();
            city.setLabel(labelString);

            Binding populationBinding = bindingSet.getBinding("populationTotal");
            Literal populationLiteral = (Literal) populationBinding.getValue();
            BigInteger populationBigInteger = populationLiteral.integerValue();
            int populationInt = populationBigInteger.intValue();
            city.setPopulation(populationInt);

            cities.add(city);
        }
        tupleQueryResult.close();

        repositoryConnection.close();

        httpRepository.shutDown();

        logger.debug("Loaded list of german cities from DBpedia.");

        return cities;
    }

    @RequestMapping(value = "/songs")
    public Songs loadSongs(@RequestParam(value="titel", defaultValue="0") String titel) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        return new Songs(titel);
    }

    @RequestMapping(value = "/movies")
    public Movies loadMovies(@RequestParam(value="year", defaultValue="0") String year) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        return new Movies(year);
    }

    @RequestMapping(value = "/actors")
    public Actors loadActors(@RequestParam(value="film", defaultValue="0") String film) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        return new Actors(film);
    }
}

