package roide.nanod.popularmovies.recyclerview.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by roide on 9/27/15.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder
{
    public BaseViewHolder(View itemView)
    {
        super(itemView);
    }

    public abstract void bind(T model);
}
