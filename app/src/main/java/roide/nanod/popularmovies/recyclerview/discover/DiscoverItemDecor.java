package roide.nanod.popularmovies.recyclerview.discover;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import roide.nanod.popularmovies.R;

/**
 * Created by kaushiksaurabh on 9/13/15.
 */
public class DiscoverItemDecor extends RecyclerView.ItemDecoration
{
    private int mColCount;

    public DiscoverItemDecor(int colCount)
    {
        mColCount = colCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state)
    {
        super.getItemOffsets(outRect, view, parent, state);

        Resources resources = view.getContext().getResources();
        int position = parent.getChildLayoutPosition(view);

        outRect.left = resources.getDimensionPixelSize(R.dimen.half_margins)/2;
        outRect.right = resources.getDimensionPixelSize(R.dimen.half_margins)/2;
        outRect.bottom = resources.getDimensionPixelSize(R.dimen.half_margins);

        int rowNumber = position / mColCount;
        int colNumber = position % mColCount;

        if(colNumber == mColCount-1)
        {
            outRect.right = outRect.right * 2;
        }
        else if(colNumber == 0)
        {
            outRect.left = outRect.left *2;
        }

        /**
         * For the top row
         */
        if(rowNumber == 0)
        {
            outRect.top = resources.getDimensionPixelSize(R.dimen.half_margins);
        }
    }
}
