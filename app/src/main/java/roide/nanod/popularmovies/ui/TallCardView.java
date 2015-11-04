package roide.nanod.popularmovies.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

/**
 * Created by roide on 11/3/15.
 */
public class TallCardView extends CardView
{
    public TallCardView(Context context)
    {
        this(context, null);
    }

    public TallCardView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public TallCardView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = (int)(width * 1.5f);
        setMeasuredDimension(width, height);
    }
}
