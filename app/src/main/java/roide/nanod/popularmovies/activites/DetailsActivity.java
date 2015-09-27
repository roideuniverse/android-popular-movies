package roide.nanod.popularmovies.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.fragments.DetailsActivityFragment;
import roide.nanod.popularmovies.network.models.Movie;
import roide.nanod.popularmovies.ui.ViewLocation;

public class DetailsActivity extends BaseActivity
{
    private static final String PREV_VIEW_LOCATION = "prev-view-location";
    private static final String MOVIE_DETAILS = "movie-details";

    public static void launch(Context context, Movie movie, ViewLocation viewLocation, View view)
    {
        Activity activity = (Activity) context;
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, view,
                activity.getResources().getString(R.string.transition_movie_display_image)
        );

        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(MOVIE_DETAILS, movie);
        intent.putExtra(PREV_VIEW_LOCATION, viewLocation);
        //context.startActivity(intent);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    private Movie mMovie;
    private ViewLocation mPrevLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        prepareActionBar();
        readIntent();

        DetailsActivityFragment fragment = DetailsActivityFragment.newInstance(mMovie, mPrevLocation);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void readIntent()
    {
        mMovie = getIntent().getParcelableExtra(MOVIE_DETAILS);
        mPrevLocation = getIntent().getParcelableExtra(PREV_VIEW_LOCATION);
    }

    @Override
    protected void findRequiredViews()
    {

    }

    private void prepareActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
