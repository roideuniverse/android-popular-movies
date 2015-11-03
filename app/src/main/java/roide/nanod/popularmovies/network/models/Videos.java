package roide.nanod.popularmovies.network.models;

import java.util.List;

/**
 * Created by roide on 11/1/15.
 */
public class Videos {
    private String id;
    private List<TrailerDetails> results;

    public class TrailerDetails {
        private String id;
        private String iso_639_1;
        private String key;
        private String name;
        private String site;
        private String size;
        private String type;

        public String getId() {
            return id;
        }

        public String getIso_639_1() {
            return iso_639_1;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getSite() {
            return site;
        }

        public String getSize() {
            return size;
        }

        public String getType() {
            return type;
        }
    }

    public List<TrailerDetails> getResults() {
        return results;
    }
}
