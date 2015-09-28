package roide.nanod.popularmovies.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.recyclerview.base.BaseViewHolder;
import roide.nanod.popularmovies.recyclerview.base.OnLoadMoreListener;
import roide.nanod.popularmovies.ui.WidgetLoadMore;

/**
 * Created by roide on 9/27/15.
 */
public class LoadMoreViewHolder extends BaseViewHolder<WidgetLoadMore>
{
    private Button mLoadMoreButton;

    public LoadMoreViewHolder(View itemView)
    {
        super(itemView);
        mLoadMoreButton = (Button) itemView.findViewById(R.id.element_load_more_button);
    }

    @Override
    public void bind(final WidgetLoadMore widgetLoadMore)
    {
        mLoadMoreButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(widgetLoadMore.getOnLoadMoreListener() != null)
                {
                    widgetLoadMore.getOnLoadMoreListener().onLoadMore(widgetLoadMore);
                }
            }
        });
    }
}
