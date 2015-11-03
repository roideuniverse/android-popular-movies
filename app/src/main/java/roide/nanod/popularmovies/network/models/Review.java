package roide.nanod.popularmovies.network.models;

import java.util.List;

/**
 * Created by roide on 11/2/15.
 */
public class Review {
    private String id;
    private int page;
    private List<ReviewDetails> results;
    private int total_pages;
    private int total_results;

    public String getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public List<ReviewDetails> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public class ReviewDetails
    {
        String id;
        String author;
        String content;
        String url;

        public String getUrl() {
            return url;
        }

        public String getContent() {
            return content;
        }

        public String getAuthor() {
            return author;
        }

        public String getId() {
            return id;
        }
    }
}
