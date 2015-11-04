package roide.nanod.popularmovies.fragments;

import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.activites.DiscoveryActivity;
import roide.nanod.popularmovies.database.FavoriteDbContract;
import roide.nanod.popularmovies.database.FavoriteMovieContentProvider;
import roide.nanod.popularmovies.network.apibuilders.DiscoverMoviesRequestBuilder;
import roide.nanod.popularmovies.network.models.Movie;
import roide.nanod.popularmovies.recyclerview.base.BaseAdapter;
import roide.nanod.popularmovies.recyclerview.base.BaseModel;
import roide.nanod.popularmovies.recyclerview.discover.DiscoverItemDecor;
import roide.nanod.popularmovies.recyclerview.base.OnLoadMoreListener;
import roide.nanod.popularmovies.ui.SortMenuActionView;
import roide.nanod.popularmovies.ui.SwipeRefreshRecyclerView;
import roide.nanod.popularmovies.ui.WidgetLoadMore;
import roide.nanod.popularmovies.util.SortOrder;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscoveryFragment extends BaseFragment
{
    private static final String SORT_ORDER = "sort-order";
    private SwipeRefreshRecyclerView mSwipeRefreshRecyclerView;
    private RecyclerView mRecyclerView;
    private BaseAdapter mBaseAdapter;
    private SortOrder mCurrentSortOrder = SortOrder.MOST_POPULAR;

    private List<BaseModel> mMoviesList;
    private int mPageNumber = 1;
    private WeakReference<WidgetLoadMore> mWidgetLoadMoreRef;

    private boolean mLoadingMore = false;
    private List<Movie> mFavoriteMovieList = new ArrayList<>();

    private boolean mIsLoaderInitialized;

    private int mColCount = 2;
    private int mActiveMenuItem = -1;

    private ArrayList<String> mSortMenuSpinnerList = new ArrayList<>();
    private Spinner.OnItemSelectedListener mSortItemSelectedListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            mActiveMenuItem = position;
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

    LoaderManager.LoaderCallbacks<Cursor> mCursorLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(
                getContext(),
                FavoriteMovieContentProvider.CONTENT_URI_MOVIES,
                null,
                null,
                null,
                null
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            mFavoriteMovieList.clear();

            if (cursor != null) {
                cursor.moveToPosition(-1);
                int indexId = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_ID);
                int indexOrgTitle = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_ORIGINAL_TITLE);
                int indexOverview = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_OVERVIEW);
                int indexRelDate = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_RELEASE_DATE);
                int indexPop = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_POPULARITY);
                int indexTitle = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_TITLE);
                int indexVoteAvg = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_VOTE_AVERAGE);
                int indexBackdrop = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_BACKDROP_PATH);
                int indexPoster = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_POSTER_PATH);
                int indexVoteCount = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_VOTE_COUNT);
                int indexFav = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_IS_FAVORITE);

                while (cursor.moveToNext()) {
                    boolean isFavorite = cursor.getInt(indexFav) == 1 ? true : false;
                    Movie movie = new Movie();
                    movie.setId(cursor.getInt(indexId));
                    movie.setOriginal_title(cursor.getString(indexOrgTitle));
                    movie.setOverview(cursor.getString(indexOverview));
                    movie.setRelease_date(cursor.getString(indexRelDate));
                    movie.setPopularity(cursor.getFloat(indexPop));
                    movie.setTitle(cursor.getString(indexTitle));
                    movie.setVote_average(cursor.getFloat(indexVoteAvg));
                    movie.setBackdrop_path(cursor.getString(indexBackdrop));
                    movie.setPoster_path(cursor.getString(indexPoster));
                    movie.setVote_count(cursor.getInt(indexVoteCount));
                    movie.setIsFavorite(isFavorite);
                    if(isFavorite)
                    {
                        mFavoriteMovieList.add(movie);
                    }
                }
            }
            if(mCurrentSortOrder == SortOrder.FAVORITE)
            {
                mMoviesList.clear();
                addAll(mFavoriteMovieList);
                mBaseAdapter.notifyDataSetChanged();
            }
            mIsLoaderInitialized = true;
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {}
    };

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
                return mColCount;
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

    public SortOrder getSortOrder(int position)
    {
        if(position == 0)
        {
            return SortOrder.MOST_POPULAR;
        }
        else if(position == 1)
        {
            return SortOrder.HIGHEST_RATED;
        }

        return SortOrder.FAVORITE;
    }

    public int getPositionForSortOrder(SortOrder sortOrder)
    {
        if(sortOrder == SortOrder.MOST_POPULAR)
        {
            return 0;
        }
        else if(sortOrder == SortOrder.HIGHEST_RATED)
        {
            return 1;
        }
        return 2;
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
        mSortMenuSpinnerList.add("Favorite");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            mColCount++;
        }

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
            if(mActiveMenuItem != -1)
            {
                actionMenuView.getSpinner().setSelection(mActiveMenuItem);
            }
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), mColCount);
        mRecyclerView = mSwipeRefreshRecyclerView.getRecyclerView();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(mSpanSizeLookup);
        mSwipeRefreshRecyclerView.setOnRefreshListener(mSwipeRefreshListener);

    }

    @Override
    protected void loadData()
    {
        if(mCurrentSortOrder == SortOrder.FAVORITE)
        {
            mBaseAdapter.enableLoadMore(false);
            mSwipeRefreshRecyclerView.setEnabled(false);

            if(! mIsLoaderInitialized)
            {
                getLoaderManager().initLoader(0, null, mCursorLoaderCallbacks);
            }
            else
            {
                mMoviesList.clear();
                addAll(mFavoriteMovieList);
                mBaseAdapter.notifyDataSetChanged();
            }
        }
        else
        {
            mSwipeRefreshRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshRecyclerView.setEnabled(true);
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
                        }
                        else
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
                                mRecyclerView.addItemDecoration(new DiscoverItemDecor(mColCount));
                                mRecyclerView.setAdapter(mBaseAdapter);
                            } else
                            {
                                mBaseAdapter.notifyDataSetChanged();
                            }
                        }
                        mSwipeRefreshRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshRecyclerView.setRefreshing(false);
                            }
                        });
                        mBaseAdapter.enableLoadMore(true);
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        mSwipeRefreshRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshRecyclerView.setRefreshing(false);
                            }
                        });
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
            movie.setOnSelectedListener(mOnSelectedListener);
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

    private DetailsActivityFragment.OnSelectedListener mOnSelectedListener =
        new DetailsActivityFragment.OnSelectedListener()
        {
        @Override
        public void onMovieSelected(Movie movie, View view)
        {
            ((DiscoveryActivity)getActivity()).onMovieSelected(movie, view);
        }
    };
}
