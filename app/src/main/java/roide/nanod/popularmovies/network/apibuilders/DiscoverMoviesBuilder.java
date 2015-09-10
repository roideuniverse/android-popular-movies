package roide.nanod.popularmovies.network.apibuilders;

import android.content.Context;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roide.nanod.popularmovies.network.MovieAPI;
import roide.nanod.popularmovies.network.MoviesRequestBuilder;
import roide.nanod.popularmovies.network.models.Movie;
import roide.nanod.popularmovies.network.responses.DiscoverMoviesResponse;

/**
 * Created by roide on 9/9/15.
 */
public class DiscoverMoviesBuilder extends BaseApiBuilder
{
    private int mPageNo = 1;
    private MovieAPI.SortOrder mSortOrder = MovieAPI.SortOrder.MOST_POPULAR;
    private Callback<List<Movie>> mRetrofitCallback;

    public static DiscoverMoviesBuilder build(Context context)
    {
        return new DiscoverMoviesBuilder(context);
    }

    private DiscoverMoviesBuilder(Context context)
    {
        super(context);
    }

    public DiscoverMoviesBuilder setPage(int pageNo)
    {
        mPageNo = pageNo;
        return this;
    }

    public DiscoverMoviesBuilder setSortOrder(MovieAPI.SortOrder sortOrder)
    {
        mSortOrder = sortOrder;
        return this;
    }

    public DiscoverMoviesBuilder setCallback(Callback<List<Movie>> callback)
    {
        mRetrofitCallback = callback;
        return this;
    }

    @Override
    public void prepareExecutable()
    {
        mRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                MoviesRequestBuilder.getInstance().discoverMovies(getApiKey(), mPageNo, mSortOrder.toString(), new Callback<DiscoverMoviesResponse>()
                {
                    @Override
                    public void success(DiscoverMoviesResponse moviesResponse, Response response)
                    {
                        if(mRetrofitCallback != null && moviesResponse != null)
                        {
                            mRetrofitCallback.success(moviesResponse.getResults(), response);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        mRetrofitCallback.failure(error);
                    }
                });
            }
        };
    }
}
