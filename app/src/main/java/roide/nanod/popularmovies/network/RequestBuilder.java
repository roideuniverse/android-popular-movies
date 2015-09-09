package roide.nanod.popularmovies.network;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import roide.nanod.popularmovies.R;

/**
 * Created by roide on 9/8/15.
 */
public class RequestBuilder
{
    public static RequestBuilder mInstance;
    private MovieAPI mMovieAPI;

    private RequestBuilder()
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(MovieAPI.BASE_URL)
                .build();

        mMovieAPI = adapter.create(MovieAPI.class);
    }

    public static MovieAPI getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new RequestBuilder();
        }
        return mInstance.mMovieAPI;
    }
}
