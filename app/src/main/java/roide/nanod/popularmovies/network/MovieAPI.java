package roide.nanod.popularmovies.network;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import roide.nanod.popularmovies.network.models.Review;
import roide.nanod.popularmovies.network.models.Videos;
import roide.nanod.popularmovies.network.responses.DiscoverMoviesResponse;

/**
 * Created by roide on 9/8/15.
 */
public interface MovieAPI
{
    String QUERY_API_KEY = "api_key";
    String QUERY_SORT_BY = "sort_by";
    String QUERY_PAGE = "page";

    @GET("/discover/movie")
    void discoverMovies(
            @Query(QUERY_API_KEY) String apiKey,
            @Query(QUERY_PAGE) int page,
            @Query(QUERY_SORT_BY) String sortBy,
            Callback<DiscoverMoviesResponse> callback
    );

    @GET("/movie/{id}/videos")
    void getVideos(
        @Path("id") int id,
        @Query(QUERY_API_KEY) String apiKey,
        Callback<Videos> callback
    );

    @GET("/movie/{id}/reviews")
    void getReviews(
        @Path("id") int id,
        @Query(QUERY_API_KEY) String apiKey,
        Callback<Review> callback
    );
}
