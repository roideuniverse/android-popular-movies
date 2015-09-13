package roide.nanod.popularmovies.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.network.models.Movie;

/**
 * Created by kaushiksaurabh on 9/12/15.
 */
public class DiscoverMovieViewHolder extends RecyclerView.ViewHolder
{
    private ImageView mImageView;

    public DiscoverMovieViewHolder(View itemView)
    {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.element_discover_grid_image);
    }

    public void bind(Movie movie)
    {
        String url = "http://image.tmdb.org/t/p/w185/" + movie.getPoster_path();
        Picasso.with(itemView.getContext()).load(url).into(mImageView);
    }
}
