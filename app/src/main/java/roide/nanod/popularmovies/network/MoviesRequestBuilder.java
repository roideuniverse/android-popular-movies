package roide.nanod.popularmovies.network;

import retrofit.RestAdapter;

/**
 * Created by roide on 9/8/15.
 */
public class MoviesRequestBuilder
{
    private String BASE_URL = "http://api.themoviedb.org/3";

    public static MoviesRequestBuilder mInstance;
    private MovieAPI mMovieAPI;

    private MoviesRequestBuilder()
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                //.setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mMovieAPI = adapter.create(MovieAPI.class);
    }

    public static MovieAPI getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new MoviesRequestBuilder();
        }
        return mInstance.mMovieAPI;
    }
}
