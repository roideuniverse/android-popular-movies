package roide.nanod.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.network.apibuilders.DiscoverMoviesRequestBuilder;
import roide.nanod.popularmovies.network.models.Movie;
import roide.nanod.popularmovies.recyclerview.DiscoverAdapter;
import roide.nanod.popularmovies.recyclerview.DiscoverItemDecor;
import roide.nanod.popularmovies.ui.SwipeRefreshRecyclerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscoveryFragment extends BaseFragment
{
    private SwipeRefreshRecyclerView mSwipeRefreshRecyclerView;
    private RecyclerView mRecyclerView;
    private DiscoverAdapter mDiscoverAdapter;

    public DiscoveryFragment()
    {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_main_discovery, container, false);
    }

    @Override
    protected void findRequiredViews(View rootView)
    {
        mSwipeRefreshRecyclerView = (SwipeRefreshRecyclerView)
                rootView.findViewById(R.id.main_discovery_swipe_refresh_rv);
    }

    @Override
    protected void prepareViews()
    {
        mRecyclerView = mSwipeRefreshRecyclerView.getRecyclerView();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    protected void loadData()
    {
        DiscoverMoviesRequestBuilder.build(getContext())
                .setPage(1)
                .setCallback(new Callback<List<Movie>>()
                {
                    @Override
                    public void success(List<Movie> movies, Response response)
                    {
                        mDiscoverAdapter = new DiscoverAdapter(movies);
                        mRecyclerView.addItemDecoration(new DiscoverItemDecor());
                        mRecyclerView.setAdapter(mDiscoverAdapter);
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {

                    }
                }).execute();

    }
}
