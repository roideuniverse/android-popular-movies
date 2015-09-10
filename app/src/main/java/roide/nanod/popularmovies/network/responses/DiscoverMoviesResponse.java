package roide.nanod.popularmovies.network.responses;

import java.util.List;

import roide.nanod.popularmovies.network.models.Movie;

/**
 * Created by roide on 9/9/15.
 */
public class DiscoverMoviesResponse
{
    private int page;
    private int total_pages;
    private int total_results;
    private List<Movie> results;

    public int getPage()
    {
        return page;
    }

    public int getTotal_pages()
    {
        return total_pages;
    }

    public int getTotal_results()
    {
        return total_results;
    }

    public List<Movie> getResults()
    {
        return results;
    }
}
