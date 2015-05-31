package edu.tum.cs.aicos.linkeddata.project.api;

public class Song {

    private String uri;

    private String label;

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

    public String getInterpretName() { return interpretName;}

    public void setInterpretName(String interpretName) {this.interpretName = interpretName;}

    public Movies getMovies() {
        return movies;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }
}
