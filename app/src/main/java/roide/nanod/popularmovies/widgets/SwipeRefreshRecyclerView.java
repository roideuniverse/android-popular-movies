package roide.nanod.popularmovies.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by roide on 9/9/15.
 */
public class SwipeRefreshRecyclerView extends SwipeRefreshLayout
{
    private RecyclerView mRecyclerView;

    public SwipeRefreshRecyclerView(Context context)
    {
        super(context);
        inflateRecyclerView();
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        inflateRecyclerView();
    }

    private void inflateRecyclerView()
    {
        mRecyclerView = new RecyclerView(getContext());
        addView(mRecyclerView);
    }

    public RecyclerView getRecyclerView()
    {
        return mRecyclerView;
    }
}
