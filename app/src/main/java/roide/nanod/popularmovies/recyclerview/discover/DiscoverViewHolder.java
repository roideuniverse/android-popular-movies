package roide.nanod.popularmovies.recyclerview.discover;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.activites.DetailsActivity;
import roide.nanod.popularmovies.network.models.Movie;
import roide.nanod.popularmovies.recyclerview.base.BaseViewHolder;
import roide.nanod.popularmovies.util.Util;

/**
 * Created by kaushiksaurabh on 9/12/15.
 */
public class DiscoverViewHolder extends BaseViewHolder<Movie>
{
    private ImageView mImageView;

    public DiscoverViewHolder(View itemView)
    {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.element_discover_grid_image);
    }

    @Override
    public void bind(Movie movie)
    {
        String url = Util.getW154ImageUrl(movie.getPoster_path());
        Picasso.with(itemView.getContext()).load(url).into(mImageView);

        bindOnImageClickListener(mImageView, movie);
    }

    private void bindOnImageClickListener(ImageView imageView, final Movie movie)
    {
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                movie.getOnSelectedListener().onMovieSelected(movie, v);
            }
        });
    }

    public void doCustomClick()
    {
        mImageView.callOnClick();
    }
}
