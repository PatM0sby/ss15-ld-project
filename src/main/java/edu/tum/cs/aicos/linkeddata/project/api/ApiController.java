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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigInteger;

@RestController
@RequestMapping("/api/")
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping(value = "/songs")
    public Songs loadSongs(@RequestParam(value="titel", defaultValue="0") String titel) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        return new Songs(titel);
    }

    @RequestMapping(value = "/movies")
    public Movies loadMovies(@RequestParam(value="year", defaultValue="") String year) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        return new Movies(year);
    }

    @RequestMapping(value = "/actors")
    public Actors loadActors(@RequestParam(value="film", defaultValue="") String film) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        return new Actors(film);
    }
}

