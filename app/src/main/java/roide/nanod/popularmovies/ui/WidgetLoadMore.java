package roide.nanod.popularmovies.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.recyclerview.base.OnLoadMoreListener;

/**
 * Created by roide on 9/27/15.
 */
public class WidgetLoadMore extends FrameLayout
{
    private OnLoadMoreListener mLoadMoreListener;
    private Button mLoadMoreButton;
    private ProgressBar mProgressBar;

    public WidgetLoadMore(Context context)
    {
        this(context, null);
    }

    public WidgetLoadMore(Context context, AttributeSet attrs)
    {
        this(context, attrs, - 1);
    }

    public WidgetLoadMore(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        inflate();
    }

    private void inflate()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.element_load_more, this);
        mProgressBar = (ProgressBar) findViewById(R.id.element_load_more_pb);
        mLoadMoreButton = (Button) findViewById(R.id.element_load_more_button);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener)
    {
        mLoadMoreListener = onLoadMoreListener;
    }

    public OnLoadMoreListener getOnLoadMoreListener()
    {
        return mLoadMoreListener;
    }

    public void setInProgress(boolean inProgress)
    {
        if(inProgress)
        {
            mLoadMoreButton.setVisibility(INVISIBLE);
            mProgressBar.setVisibility(VISIBLE);
        }
        else
        {
            mLoadMoreButton.setVisibility(VISIBLE);
            mProgressBar.setVisibility(INVISIBLE);
        }
    }
}
