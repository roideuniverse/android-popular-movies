package roide.nanod.popularmovies.recyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import roide.nanod.popularmovies.R;

/**
 * Created by kaushiksaurabh on 9/13/15.
 */
public class DiscoverItemDecor extends RecyclerView.ItemDecoration
{
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state)
    {
        super.getItemOffsets(outRect, view, parent, state);

        Resources resources = view.getContext().getResources();
        int position = parent.getChildLayoutPosition(view);

        outRect.left = resources.getDimensionPixelSize(R.dimen.half_margins);
        outRect.right = resources.getDimensionPixelSize(R.dimen.half_margins);
        outRect.bottom = resources.getDimensionPixelSize(R.dimen.half_margins);

        if(position % 2 == 0)
        {
            outRect.right = outRect.right / 2;
        }
        else
        {
            outRect.left = outRect.left / 2;
        }
    }
}
