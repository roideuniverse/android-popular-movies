package roide.nanod.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.network.apibuilders.DiscoverMoviesRequestBuilder;
import roide.nanod.popularmovies.network.models.Movie;
import roide.nanod.popularmovies.recyclerview.DiscoverAdapter;
import roide.nanod.popularmovies.recyclerview.DiscoverItemDecor;
import roide.nanod.popularmovies.ui.SortMenuActionView;
import roide.nanod.popularmovies.ui.SwipeRefreshRecyclerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscoveryFragment extends BaseFragment
{
    private SwipeRefreshRecyclerView mSwipeRefreshRecyclerView;
    private RecyclerView mRecyclerView;
    private DiscoverAdapter mDiscoverAdapter;

    private ArrayList<String> mSortMenuSpinnerList = new ArrayList<>();
    private Spinner.OnItemSelectedListener mSortItemSelectedListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            Log.d("kaushik", "onItemSelected::" + position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    };

    public DiscoveryFragment()
    {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mSortMenuSpinnerList.add("Most Popular");
        mSortMenuSpinnerList.add("Highest Rated");
        setHasOptionsMenu(true);
        Log.d("DF", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_main_discovery, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        Log.d("DF", "onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main_discovery, menu);
        MenuItem sortItem = menu.findItem(R.id.action_sorting);
        if(sortItem != null)
        {
            SortMenuActionView actionMenuView = new SortMenuActionView(getContext(), mSortMenuSpinnerList);
            sortItem.setActionView(actionMenuView);
            actionMenuView.getSpinner().setOnItemSelectedListener(mSortItemSelectedListener);
        }

        super.onCreateOptionsMenu(menu, inflater);
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
