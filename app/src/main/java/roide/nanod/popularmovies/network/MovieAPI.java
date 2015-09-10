package roide.nanod.popularmovies.network;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import roide.nanod.popularmovies.network.responses.DiscoverMoviesResponse;

/**
 * Created by roide on 9/8/15.
 */
public interface MovieAPI
{
    String QUERY_API_KEY = "api_key";
    String QUERY_SORT_BY = "sort_by";
    String QUERY_PAGE = "page";

    String SORT_HIGHEST_RATED = "vote_average.desc";
    String SORT_MOST_POPULAR = "popularity.desc";

    enum SortOrder
    {
        HIGHEST_RATED("vote_average.desc"),
        MOST_POPULAR("popularity.desc");

        private final String mSortOrder;
        private SortOrder(String sortOrder)
        {
            mSortOrder = sortOrder;
        }

        @Override
        public String toString()
        {
            return mSortOrder;
        }
    }

    @GET("/discover/movie")
    void discoverMovies(
            @Query(QUERY_API_KEY) String apiKey,
            @Query(QUERY_PAGE) int page,
            @Query(QUERY_SORT_BY) String sortBy,
            Callback<DiscoverMoviesResponse> callback
    );
}
