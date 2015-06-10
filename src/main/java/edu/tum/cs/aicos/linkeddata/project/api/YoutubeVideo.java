package edu.tum.cs.aicos.linkeddata.project.api;

/**
 * Created by Pat on 09.06.2015.
 */
public class YoutubeVideo {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public YoutubeVideo() {
    }

    public YoutubeVideo(String id) {
        this.id = id;
    }
}
