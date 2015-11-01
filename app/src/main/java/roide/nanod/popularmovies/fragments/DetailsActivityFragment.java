package roide.nanod.popularmovies.fragments;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.database.FavoriteDbUtil;
import roide.nanod.popularmovies.exceptions.ActivityClosingException;
import roide.nanod.popularmovies.network.models.Movie;
import roide.nanod.popularmovies.util.Util;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener
{
    private static final String ARG_MOVIE = "arg-movie";

    public static DetailsActivityFragment newInstance(Movie movie)
    {
        DetailsActivityFragment fragment = new DetailsActivityFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.fragment_details_header) ImageView mIvHeaderImageView;
    @Bind(R.id.fragment_details_display_pic) ImageView mIvDisplayPicture;
    @Bind(R.id.fragment_details_display_pic_hidden) ImageView mIvDisplayPictureHidden;
    @Bind(R.id.fragment_details_release_date) TextView mTvReleaseDate;
    @Bind(R.id.fragment_details_rating_value) TextView mTvMovieRating;
    @Bind(R.id.fragment_details_rating_users_count) TextView mTvMovieRatingUserCount;
    @Bind(R.id.fragment_details_movie_summary) TextView mTvMovieSummary;
    @Bind(R.id.fragment_details_movie_name) TextView mTvMovieName;
    @Bind(R.id.fragment_details_toolbar) Toolbar mToolbar;
    @Bind(R.id.appbar) AppBarLayout mAppBarLayout;
    @Bind(R.id.fragment_details_fab) FloatingActionButton mFloatingActionButton;
    @Bind(R.id.fragment_container_display_pic_container) FrameLayout mDPContainer;

    private Movie mMovie;

    public DetailsActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mMovie = getArguments().getParcelable(ARG_MOVIE);
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    private static final String TAG = DetailsActivityFragment.class.getSimpleName();

    @Override
    protected void findRequiredViews(View rootView)
    {
        ButterKnife.bind(this, rootView);
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void prepareViews()
    {
        try
        {
            getBaseActivity().setSupportActionBar(mToolbar);
            getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getBaseActivity().setTitle(mMovie.getTitle());
            mMovie.setIsFavorite(FavoriteDbUtil.isFavorite(mMovie, getContext()));
            refreshFabUI();
        }
        catch(ActivityClosingException e)
        {
            e.printStackTrace();
            return;
        }

        String url = Util.getW500ImageUrl(mMovie.getBackdrop_path());
        Picasso.with(getContext()).load(url).into(mIvHeaderImageView);

        String url2 = Util.getW154ImageUrl(mMovie.getPoster_path());
        Picasso.with(getContext()).load(url2).into(mIvDisplayPicture);
        Picasso.with(getContext()).load(url2).into(mIvDisplayPictureHidden);

        mTvMovieName.setText(mMovie.getOriginal_title());
        mTvReleaseDate.setText(mMovie.getRelease_date());
        mTvMovieRating.setText(String.valueOf(mMovie.getVote_average()));
        mTvMovieRatingUserCount.setText(String.valueOf(mMovie.getVote_count()));
        mTvMovieSummary.setText(mMovie.getOverview());

    }

    @Override
    protected void loadData()
    {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int vOffset)
    {
        float ratio = 1 - 2 * (float) Math.abs(vOffset)/mAppBarLayout.getHeight();
        mDPContainer.setAlpha(ratio);
        mIvDisplayPictureHidden.setAlpha(1 - ratio);
    }

    @OnClick(R.id.fragment_details_fab)
    public void onFabClicked(View view)
    {
        if(mMovie.getIsFavorite())
        {
            FavoriteDbUtil.doFavorite(mMovie, false, getContext());
            mMovie.setIsFavorite(false);
        }
        else
        {
            FavoriteDbUtil.doFavorite(mMovie, true, getContext());
            mMovie.setIsFavorite(true);
        }
        refreshFabUI();
    }

    private void refreshFabUI()
    {
        if(mMovie.getIsFavorite())
        {
            mFloatingActionButton.setImageResource(R.drawable.ic_favorite_true);
        }
        else
        {
            mFloatingActionButton.setImageResource(R.drawable.ic_favorite_false);
        }
    }
}
