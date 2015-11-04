package roide.nanod.popularmovies.fragments;

import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.exceptions.ActivityClosingException;
import roide.nanod.popularmovies.network.MoviesRequestBuilder;
import roide.nanod.popularmovies.network.models.Movie;
import roide.nanod.popularmovies.network.models.Review;
import roide.nanod.popularmovies.network.models.Videos;
import roide.nanod.popularmovies.ui.TrailerRowWidget;
import roide.nanod.popularmovies.ui.WidgetReviewEntry;
import roide.nanod.popularmovies.util.FavoriteDbUtil;
import roide.nanod.popularmovies.util.Util;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener
{
    private static final String ARG_MOVIE = "arg-movie";
    private static final String ARG_IS_TWO_PANE = "arg-two-pane";

    public interface OnSelectedListener
    {
        void onMovieSelected(Movie movie, View view);
    }

    public static DetailsActivityFragment newInstance(Movie movie, boolean isTwoPane)
    {
        DetailsActivityFragment fragment = new DetailsActivityFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        args.putBoolean(ARG_IS_TWO_PANE, isTwoPane);
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
    @Bind(R.id.fragment_details_trailer_container) LinearLayout mTrailerContainer;
    @Bind(R.id.fragment_details_trailer_pb) ProgressBar mTrailerProgressBar;
    @Bind(R.id.fragment_details_trailer_header) TextView mTrailerHeader;
    @Bind(R.id.fragment_details_reviews_container) LinearLayout mReviewsContainer;
    @Bind(R.id.fragment_details_reviews_pb) ProgressBar mReviewsProgressBar;
    @Bind(R.id.fragment_details_reviews_header) TextView mReviewsHeader;

    private Movie mMovie;
    private String mTrailerKey;
    private MenuItem mShareItem;
    private boolean mIsTwoPane;

    public DetailsActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mMovie = getArguments().getParcelable(ARG_MOVIE);
        mIsTwoPane = getArguments().getBoolean(ARG_IS_TWO_PANE);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_trailer_share, menu);
        mShareItem = menu.findItem(R.id.action_share);
        if(mTrailerKey == null)
        {
            mShareItem.setVisible(false);
        }
        else
        {
            mShareItem.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_share)
        {
            String url = "http://www.youtube.com/watch?v=" + mTrailerKey;
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, url);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        return super.onOptionsItemSelected(item);
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
            loadTrailers();
            loadReviews();
            if(! mIsTwoPane)
            {
                getBaseActivity().setSupportActionBar(mToolbar);
                getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getBaseActivity().setTitle(mMovie.getTitle());
            }
            else
            {
                mToolbar.setVisibility(View.GONE);
            }
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
        if(ratio >= 0.25f)
        {
            mDPContainer.setAlpha(ratio);
            mIvDisplayPictureHidden.setAlpha(1 - ratio);
        }
        else
        {
            mDPContainer.setAlpha(0);
            mIvDisplayPictureHidden.setAlpha(1f);
        }
    }

    @OnClick(R.id.fragment_details_fab)
    public void onFabClicked(View view)
    {
        mMovie.setIsFavorite(!mMovie.getIsFavorite());
        FavoriteDbUtil.saveMovie(mMovie, getContext());
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

    private void loadTrailers() {
        String apiKey = getString(R.string.api_key);
        MoviesRequestBuilder.getInstance().getVideos(mMovie.getId(), apiKey,
            new Callback<Videos>() {
                @Override
                public void success(Videos videos, Response response) {
                    mTrailerProgressBar.setVisibility(View.GONE);
                    for (Videos.TrailerDetails trailerDetails : videos.getResults()) {
                        if (mTrailerKey == null) {
                            mTrailerKey = trailerDetails.getKey();
                        }
                        mTrailerHeader.setVisibility(View.VISIBLE);
                        TrailerRowWidget widget = new TrailerRowWidget(getContext());
                        widget.setTrailerDetails(trailerDetails);
                        mTrailerContainer.addView(widget);
                        if (mTrailerKey == null && mShareItem != null) {
                            mShareItem.setVisible(false);
                        } else if (mTrailerKey != null && mShareItem != null) {
                            mShareItem.setVisible(true);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
    }

    private void loadReviews() {
        String apiKey = getString(R.string.api_key);
        MoviesRequestBuilder.getInstance().getReviews(mMovie.getId(), apiKey,
            new Callback<Review>()
            {
                @Override
                public void success(Review reviews, Response response)
                {
                    mReviewsProgressBar.setVisibility(View.GONE);
                    for(Review.ReviewDetails rd : reviews.getResults())
                    {
                        mReviewsHeader.setVisibility(View.VISIBLE);
                        WidgetReviewEntry entry = new WidgetReviewEntry(getContext());
                        entry.setReview(rd);
                        mReviewsContainer.addView(entry);
                    }
                }

                @Override
                public void failure(RetrofitError error)
                {

                }
            });
    }
}
