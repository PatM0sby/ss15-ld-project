package edu.tum.cs.aicos.linkeddata.project.api;

public class Actor {

    private String uri;

    private String label;

   // private String birth;

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

    //public String getBirth() { return birth;}

   // public void setBirth(String publicationDate) {this.birth = birth;}

    public Movies getMovies() {
        return movies;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }
}
