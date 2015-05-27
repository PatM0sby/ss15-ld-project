package edu.tum.cs.aicos.linkeddata.project.api;

public class Movie {
    private String uri;

    private String label;

    private int runtime;

    private String publicationDate; // yyyy-mm-dd

    private String genre;

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

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
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

    public void setGenre(String publicationDate) {
        this.genre = genre;
    }
}
