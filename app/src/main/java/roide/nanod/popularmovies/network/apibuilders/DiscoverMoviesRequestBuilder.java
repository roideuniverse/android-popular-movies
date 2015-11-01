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
import roide.nanod.popularmovies.util.SortOrder;

/**
 * Created by roide on 9/9/15.
 */
public class DiscoverMoviesRequestBuilder extends BaseApiBuilder
{
    private int mPageNo = 1;
    private SortOrder mSortOrder = SortOrder.MOST_POPULAR;
    private Callback<List<Movie>> mRetrofitCallback;

    public static DiscoverMoviesRequestBuilder build(Context context)
    {
        return new DiscoverMoviesRequestBuilder(context);
    }

    private DiscoverMoviesRequestBuilder(Context context)
    {
        super(context);
    }

    public DiscoverMoviesRequestBuilder setPage(int pageNo)
    {
        mPageNo = pageNo;
        return this;
    }

    public DiscoverMoviesRequestBuilder setSortOrder(SortOrder sortOrder)
    {
        mSortOrder = sortOrder;
        return this;
    }

    public DiscoverMoviesRequestBuilder setCallback(Callback<List<Movie>> callback)
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
