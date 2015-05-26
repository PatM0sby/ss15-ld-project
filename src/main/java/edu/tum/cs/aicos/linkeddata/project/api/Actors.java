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


        /*

        Fancy Sparql Anfrage

         */


        );


        TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
        while (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();

            Actor actor = new Actor();

            Binding actorfilmBinding = bindingSet.getBinding("film");
            Literal actorfilmLiteral = (Literal) actorfilmBinding.getValue();
            String actorfilmString = actorfilmLiteral.stringValue();
            movie.setFilm(actorfilmString);

            this.add(actor);
        }
        tupleQueryResult.close();

        repositoryConnection.close();

        httpRepository.shutDown();
    }
}