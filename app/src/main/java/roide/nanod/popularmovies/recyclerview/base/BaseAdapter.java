package roide.nanod.popularmovies.recyclerview.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.network.models.Movie;
import roide.nanod.popularmovies.recyclerview.loadmore.LoadMoreModel;
import roide.nanod.popularmovies.recyclerview.loadmore.LoadMoreViewHolder;
import roide.nanod.popularmovies.recyclerview.discover.DiscoverViewHolder;
import roide.nanod.popularmovies.ui.WidgetLoadMore;

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    private List<BaseModel> mModelList;
    private WidgetLoadMore mWidgetLoadMore;
    private OnLoadMoreListener mLoadMoreListener;
    private boolean mLoadMoreEnabled;

    public BaseAdapter(List<BaseModel> movies)
    {
        mModelList = movies;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        if(viewType == Movie.VIEW_TYPE)
        {
            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_discover_grid_view,
                    viewGroup, false);
            return new DiscoverViewHolder(root);
        }
        else if(viewType == LoadMoreModel.VIEW_TYPE && mLoadMoreEnabled)
        {
            mWidgetLoadMore = new WidgetLoadMore(viewGroup.getContext());
            return new LoadMoreViewHolder(mWidgetLoadMore);
        }
        throw new IllegalStateException("Unexpected View Type=" + viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int position)
    {
        if(position >= mModelList.size())
        {
            mWidgetLoadMore.setOnLoadMoreListener(mLoadMoreListener);
            baseViewHolder.bind(mWidgetLoadMore);
        }
        else
        {
            baseViewHolder.bind(mModelList.get(position));
        }
    }

    @Override
    public int getItemCount()
    {
        if(mLoadMoreEnabled)
        {
            return mModelList.size() + 1;
        }
        else
        {
            return mModelList.size();
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position >= mModelList.size())
        {
            return LoadMoreModel.VIEW_TYPE;
        }
        return mModelList.get(position).getType();
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
