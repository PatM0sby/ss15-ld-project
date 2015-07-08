package edu.tum.cs.aicos.linkeddata.project.api;

/**
 * Created by Daniel on 24.06.2015.
 */
public class Everything {
    private String uri;

    private String label;

    private String runtime;

    private String publicationDate; // yyyy-mm-dd

    private String genre;

    private String interpretName;

    private Movies movies;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String publicationDate) {this.genre = genre;}

    public String getInterpretName() { return interpretName;}

    public void setInterpretName(String interpretName) {this.interpretName = interpretName;}

    public Movies getMovies() {
        return movies;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }
}
