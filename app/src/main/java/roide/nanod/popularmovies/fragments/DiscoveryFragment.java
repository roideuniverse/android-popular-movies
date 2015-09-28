package roide.nanod.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.network.MovieAPI;
import roide.nanod.popularmovies.network.apibuilders.DiscoverMoviesRequestBuilder;
import roide.nanod.popularmovies.network.models.Movie;
import roide.nanod.popularmovies.recyclerview.base.BaseAdapter;
import roide.nanod.popularmovies.recyclerview.DiscoverItemDecor;
import roide.nanod.popularmovies.recyclerview.base.OnLoadMoreListener;
import roide.nanod.popularmovies.ui.SortMenuActionView;
import roide.nanod.popularmovies.ui.SwipeRefreshRecyclerView;
import roide.nanod.popularmovies.ui.WidgetLoadMore;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscoveryFragment extends BaseFragment
{

    private static final String SORT_ORDER = "sort-order";
    private SwipeRefreshRecyclerView mSwipeRefreshRecyclerView;
    private RecyclerView mRecyclerView;
    private BaseAdapter mBaseAdapter;
    private MovieAPI.SortOrder mCurrentSortOrder = MovieAPI.SortOrder.MOST_POPULAR;

    private List<Movie> mMoviesList;
    private int mPageNumber = 1;
    private WeakReference<WidgetLoadMore> mWidgetLoadMoreRef;

    private boolean mLoadingMore = false;

    private ArrayList<String> mSortMenuSpinnerList = new ArrayList<>();
    private Spinner.OnItemSelectedListener mSortItemSelectedListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            if(getSortOrder(position) != mCurrentSortOrder && mMoviesList != null)
            {
                mCurrentSortOrder = getSortOrder(position);
                reloadData();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    };
/*
    private Comparator<Movie> mMovieComparator = new Comparator<Movie>()
    {
        @Override
        public int compare(Movie lhs, Movie rhs)
        {
            if(mCurrentSortOrder == MovieAPI.SortOrder.MOST_POPULAR)
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
    */

    private SwipeRefreshLayout.OnRefreshListener mSwipeRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener()
    {
        @Override
        public void onRefresh()
        {
            reloadData();
        }
    };

    private void reloadData()
    {
        mPageNumber = 1;
        loadData();
    }

    private GridLayoutManager.SpanSizeLookup mSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position)
        {
            if(position >= mMoviesList.size())
            {
                return 2;
            }
            return 1;
        }
    };

    private OnLoadMoreListener mOnLoadMoreListener = new OnLoadMoreListener()
    {
        @Override
        public void onLoadMore(WidgetLoadMore widgetLoadMore)
        {
            widgetLoadMore.setInProgress(true);
            mWidgetLoadMoreRef = new WeakReference<>(widgetLoadMore);
            mLoadingMore = true;
            mPageNumber++;
            loadData();
        }
    };

    public MovieAPI.SortOrder getSortOrder(int position)
    {
        if(position == 0)
        {
            return MovieAPI.SortOrder.MOST_POPULAR;
        }
        return MovieAPI.SortOrder.HIGHEST_RATED;
    }

    public int getPositionForSortOrder(MovieAPI.SortOrder sortOrder)
    {
        if(sortOrder == MovieAPI.SortOrder.MOST_POPULAR)
        {
            return 0;
        }
        return 1;
    }

    //================================================ //
    //================== Fragment ==================== //
    //================================================ //

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
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(SORT_ORDER, getPositionForSortOrder(mCurrentSortOrder));

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        if(savedInstanceState != null)
        {
            mCurrentSortOrder = getSortOrder(savedInstanceState.getInt(SORT_ORDER));
        }
        super.onViewStateRestored(savedInstanceState);
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView = mSwipeRefreshRecyclerView.getRecyclerView();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(mSpanSizeLookup);

        mSwipeRefreshRecyclerView.setOnRefreshListener(mSwipeRefreshListener);
    }

    @Override
    protected void loadData()
    {
        mSwipeRefreshRecyclerView.post(new Runnable()
        {
            @Override
            public void run()
            {
                mSwipeRefreshRecyclerView.setRefreshing(true);
            }
        });
        DiscoverMoviesRequestBuilder.build(getContext())
                .setPage(mPageNumber)
                .setSortOrder(mCurrentSortOrder)
                .setCallback(new Callback<List<Movie>>()
                {
                    @Override
                    public void success(List<Movie> movies, Response response)
                    {

                        mSwipeRefreshRecyclerView.setRefreshing(false);
                        if(mLoadingMore == true)
                        {
                            mLoadingMore = false;
                            WidgetLoadMore wlm = mWidgetLoadMoreRef.get();
                            if(wlm != null)
                            {
                                wlm.setInProgress(false);
                            }
                            addAll(movies);
                            mBaseAdapter.notifyDataSetChanged();
                        } else
                        {
                            if(mMoviesList != null)
                            {
                                mMoviesList.clear();
                            }

                            addAll(movies);
                            if(mBaseAdapter == null)
                            {
                                mBaseAdapter = new BaseAdapter(mMoviesList);
                                mBaseAdapter.setOnLoadMoreListener(mOnLoadMoreListener);
                                mRecyclerView.addItemDecoration(new DiscoverItemDecor());
                                mRecyclerView.setAdapter(mBaseAdapter);
                            } else
                            {
                                mBaseAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        mSwipeRefreshRecyclerView.setRefreshing(false);
                        if(mLoadingMore == true)
                        {
                            mLoadingMore = false;
                            WidgetLoadMore wlm = mWidgetLoadMoreRef.get();
                            if(wlm != null)
                            {
                                wlm.setInProgress(false);
                            }

                        }
                    }
                }).execute();

    }

    private void addAll(List<Movie> movieList)
    {
        if(mMoviesList == null)
        {
            mMoviesList = new ArrayList<>();
        }
        for(Movie movie: movieList)
        {
            if(checkNull(movie.getBackdrop_path()) || checkNull(movie.getPoster_path()))
            {
                continue;
            }
            mMoviesList.add(movie);
        }
    }

    private boolean checkNull(String str)
    {
        if(str == null || str.equalsIgnoreCase("null") || str.isEmpty())
        {
            return true;
        }
        return false;
    }
}
