package roide.nanod.popularmovies.fragments;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.exceptions.ActivityClosingException;
import roide.nanod.popularmovies.network.models.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends BaseFragment
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
    private TextView mTvReleaseDate;
    private TextView mTvMovieRating;
    private TextView mTvMovieRatingUserCount;
    private TextView mTvMovieSummary;
    private FrameLayout mFlDisplayPicContainer;
    private Toolbar mToolbar;

    private Movie mMovie;

    private int mPreviousHeight = 0;

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
        mIvHeaderImageView = (ImageView) rootView.findViewById(R.id.details_header);
        mToolbar = (Toolbar) rootView.findViewById(R.id.fragment_details_toolbar);
        mIvDisplayPicture = (ImageView) rootView.findViewById(R.id.fragment_details_display_pic);
        mFlDisplayPicContainer = (FrameLayout) rootView.findViewById(R.id.fragment_container_display_pic_container);
    }

    @Override
    protected void prepareViews()
    {
        mIvDisplayPicture.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            @Override
            public boolean onPreDraw()
            {
                int height = mIvDisplayPicture.getHeight();
                if(height != 0 && height > mPreviousHeight)
                {
                    Log.d(TAG, "height=" + height);
                    //mIvDisplayPicture.getViewTreeObserver().removeOnPreDrawListener(this);

                    mPreviousHeight = height;
                    int pLeft = mFlDisplayPicContainer.getPaddingLeft();
                    int pTop = mFlDisplayPicContainer.getPaddingTop() + (int)(height * 0.75);
                    int pRight = mFlDisplayPicContainer.getPaddingRight();
                    int pBottom = mFlDisplayPicContainer.getPaddingBottom();

                    //mFlDisplayPicContainer.setPadding(pLeft, pTop, pRight, pBottom);
                }
                return true;
            }
        });

        try
        {
            getBaseActivity().setSupportActionBar(mToolbar);
            getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getBaseActivity().setTitle(mMovie.getTitle());
        }
        catch(ActivityClosingException e)
        {
            e.printStackTrace();
        }

        String url = "http://image.tmdb.org/t/p/w500" + mMovie.getBackdrop_path();
        Picasso.with(getContext()).load(url).into(mIvHeaderImageView);

        String url2 = "http://image.tmdb.org/t/p/w154" + mMovie.getPoster_path();
        Picasso.with(getContext()).load(url2).into(mIvDisplayPicture);

        mTvReleaseDate.setText(mMovie.getRelease_date());
        mTvMovieRating.setText(String.valueOf(mMovie.getVote_average()));
        mTvMovieRatingUserCount.setText( String.valueOf(mMovie.getVote_count()) );
        mTvMovieSummary.setText(mMovie.getOverview());

    }

    @Override
    protected void loadData()
    {

    }
}
