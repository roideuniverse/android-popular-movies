package roide.nanod.popularmovies.fragments;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import roide.nanod.popularmovies.R;
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

    private ImageView mIvHeaderImageView;
    private ImageView mIvDisplayPicture;
    private ImageView mIvDisplayPictureHidden;
    private TextView mTvReleaseDate;
    private TextView mTvMovieRating;
    private TextView mTvMovieRatingUserCount;
    private TextView mTvMovieSummary;
    private TextView mTvMovieName;
    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;
    private FrameLayout mDPContainer;

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
        mTvMovieRating = (TextView) rootView.findViewById(R.id.fragment_details_rating_value);
        mTvReleaseDate = (TextView) rootView.findViewById(R.id.fragment_details_release_date);
        mTvMovieRatingUserCount = (TextView) rootView.findViewById(R.id.fragment_details_rating_users_count);
        mTvMovieSummary = (TextView) rootView.findViewById(R.id.fragment_details_movie_summary);
        mIvHeaderImageView = (ImageView) rootView.findViewById(R.id.fragment_details_header);
        mToolbar = (Toolbar) rootView.findViewById(R.id.fragment_details_toolbar);
        mIvDisplayPicture = (ImageView) rootView.findViewById(R.id.fragment_details_display_pic);
        mIvDisplayPictureHidden = (ImageView) rootView.findViewById(R.id.fragment_details_display_pic_hidden);
        mDPContainer = (FrameLayout) rootView.findViewById(R.id.fragment_container_display_pic_container);
        mTvMovieName = (TextView) rootView.findViewById(R.id.fragment_details_movie_name);
        mAppBarLayout = (AppBarLayout) rootView.findViewById(R.id.appbar);
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
        mTvMovieRatingUserCount.setText( String.valueOf(mMovie.getVote_count()) );
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
}
