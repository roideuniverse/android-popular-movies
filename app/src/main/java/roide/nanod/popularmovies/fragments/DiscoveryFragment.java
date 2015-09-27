package roide.nanod.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private static final int SORT_MOST_POPULAR = 0;
    private static final int SORT_HIGHEST_RATED = 1;

    private SwipeRefreshRecyclerView mSwipeRefreshRecyclerView;
    private RecyclerView mRecyclerView;
    private DiscoverAdapter mDiscoverAdapter;
    private int mCurrentSortOrder = SORT_MOST_POPULAR;

    private List<Movie> mMoviesList;

    private ArrayList<String> mSortMenuSpinnerList = new ArrayList<>();
    private Spinner.OnItemSelectedListener mSortItemSelectedListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            if(position != mCurrentSortOrder && mMoviesList != null)
            {
                mCurrentSortOrder = position;
                Collections.sort(mMoviesList, mMovieComparator);
                mDiscoverAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    };

    private Comparator<Movie> mMovieComparator = new Comparator<Movie>()
    {
        @Override
        public int compare(Movie lhs, Movie rhs)
        {
            if(mCurrentSortOrder == SORT_MOST_POPULAR)
            {
                float diff = rhs.getPopularity()*100 - lhs.getPopularity()*100;
                return (int)(diff);
            }
            else
            {
                float diff = rhs.getVote_average()*100 - lhs.getVote_average()*100;
                return (int)(diff);
            }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main_discovery, menu);
        MenuItem sortItem = menu.findItem(R.id.action_sorting);
        if(sortItem != null)
        {
            SortMenuActionView actionMenuView = new SortMenuActionView(
                    getActivity().getApplicationContext(), mSortMenuSpinnerList);
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
                        mMoviesList = movies;
                        Collections.sort(mMoviesList, mMovieComparator);
                        mDiscoverAdapter = new DiscoverAdapter(mMoviesList);
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
