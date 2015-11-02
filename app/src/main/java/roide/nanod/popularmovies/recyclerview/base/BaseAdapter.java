package roide.nanod.popularmovies.recyclerview.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.network.models.Movie;
import roide.nanod.popularmovies.recyclerview.LoadMoreViewHolder;
import roide.nanod.popularmovies.recyclerview.DiscoverViewHolder;
import roide.nanod.popularmovies.ui.WidgetLoadMore;

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    private static final int VIEW_MOVIE = 0;
    private static final int VIEW_FOOTER = 1;

    private List<Movie> mMovieList;
    private WidgetLoadMore mWidgetLoadMore;
    private OnLoadMoreListener mLoadMoreListener;
    private boolean mLoadMoreEnabled;

    public BaseAdapter(List<Movie> movies)
    {
        mMovieList = movies;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        if(viewType == VIEW_MOVIE)
        {
            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_discover_grid_view,
                    viewGroup, false);
            return new DiscoverViewHolder(root);
        }
        else if(viewType == VIEW_FOOTER && mLoadMoreEnabled)
        {
            mWidgetLoadMore = new WidgetLoadMore(viewGroup.getContext());
            return new LoadMoreViewHolder(mWidgetLoadMore);
        }
        throw new IllegalStateException("Unexpected View Type=" + viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int position)
    {
        if(position >= mMovieList.size())
        {
            mWidgetLoadMore.setOnLoadMoreListener(mLoadMoreListener);
            baseViewHolder.bind(mWidgetLoadMore);
        }
        else
        {
            baseViewHolder.bind(mMovieList.get(position));
        }
    }

    @Override
    public int getItemCount()
    {
        if(mLoadMoreEnabled)
        {
            return mMovieList.size() + 1;
        }
        else
        {
            return mMovieList.size();
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position >= mMovieList.size())
        {
            return VIEW_FOOTER;
        }
        return VIEW_MOVIE;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener)
    {
        mLoadMoreListener = onLoadMoreListener;
    }

    public void enableLoadMore(boolean enable)
    {
        mLoadMoreEnabled = enable;
    }
}
