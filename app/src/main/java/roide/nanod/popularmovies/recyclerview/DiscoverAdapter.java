package roide.nanod.popularmovies.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.network.models.Movie;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverMovieViewHolder>
{
    private List<Movie> mMovieList;

    public DiscoverAdapter(List<Movie> movies)
    {
        mMovieList = movies;
    }

    @Override
    public DiscoverMovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_discover_grid_view,
                viewGroup, false);
        return new DiscoverMovieViewHolder(root);
    }

    @Override
    public void onBindViewHolder(DiscoverMovieViewHolder discoverMovieViewHolder, int position)
    {
        discoverMovieViewHolder.bind(mMovieList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return mMovieList.size();
    }
}
