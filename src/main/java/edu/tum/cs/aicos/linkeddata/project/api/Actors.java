package edu.tum.cs.aicos.linkeddata.project.api;

import org.openrdf.model.Literal;
import org.openrdf.query.*;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

import java.util.ArrayList;

public class Actors extends ArrayList<Actor> {
    public Actors() {

    }

    public Actors(String film) throws RepositoryException, MalformedQueryException, QueryEvaluationException {


        // Load list of scientists from DBpedia
        HTTPRepository httpRepository = new HTTPRepository("http://data.linkedmdb.org/sparql");
        httpRepository.initialize();

        RepositoryConnection repositoryConnection = httpRepository.getConnection();

        TupleQuery tupleQuery = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL,


                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                        "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                        "\n" +
                        "Select ?uri ?title ?runtime ?publicationDate ?genre\n" +
                        "WHERE {\n" +
                        "?uri movie:runtime ?publicationDate.\n" +
                        "FILTER(?title =" + film + ")\n " +
                        "\n" +
                        "}\n" +
                        "LIMIT 1"


        );


        TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
        while (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();

            Actor actor = new Actor();

            Binding actorfilmBinding = bindingSet.getBinding("film");
            Literal actorfilmLiteral = (Literal) actorfilmBinding.getValue();
            String actorfilmString = actorfilmLiteral.stringValue();
            actor.setFilm(actorfilmString);

            this.add(actor);
        }
        tupleQueryResult.close();

        repositoryConnection.close();

        httpRepository.shutDown();
    }
}