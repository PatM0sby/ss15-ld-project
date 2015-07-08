package edu.tum.cs.aicos.linkeddata.project.api;

import java.util.List;

/**
 * Created by Pat on 03.06.2015.
 */
public class NewsString{
    private String entries;

    public String getEntries() {
        return entries;
    }

    public void setEntries(String entries) {
        this.entries = entries;
    }

    public NewsString(String entries) {
        this.entries = entries;
    }

    public NewsString() {
    }

}