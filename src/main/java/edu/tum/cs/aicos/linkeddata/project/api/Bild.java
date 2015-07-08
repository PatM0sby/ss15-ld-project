package edu.tum.cs.aicos.linkeddata.project.api;

/**
 * Created by Silvio on 30/06/2015.
 */
public class Bild {
        private String id;
        private String infos;

        public String getId() {
            return id;
        }

        public void setInfos(String id) {
            this.infos = id;
        }
    public String getInfos() {
        return infos;
    }

    public void setId(String id) {
        this.id = id;
    }

        public Bild() {
        }

        public Bild(String id) {
            this.id = id;
        }

}
