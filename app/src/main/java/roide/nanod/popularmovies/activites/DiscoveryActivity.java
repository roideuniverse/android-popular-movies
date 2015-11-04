package roide.nanod.popularmovies.activites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.fragments.DetailsActivityFragment;
import roide.nanod.popularmovies.fragments.DiscoveryFragment;
import roide.nanod.popularmovies.network.models.Movie;

public class DiscoveryActivity extends BaseActivity
    implements DetailsActivityFragment.OnSelectedListener
{
    @Nullable
    @Bind(R.id.activity_main_fragment_details) FrameLayout mDetailsLayout;

    private boolean mIsTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_discovery);
        ButterKnife.bind(this);

        if(mDetailsLayout != null)
        {
            mIsTwoPane = true;
        }

        if(savedInstanceState == null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_frag_container,
                        DiscoveryFragment.newInstance(mIsTwoPane))
                    .commit();
        }
    }

    @Override
    protected void findRequiredViews()
    {

    }

    @Override
    public void onMovieSelected(Movie movie, View view)
    {
        if(mIsTwoPane)
        {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_details,
                    DetailsActivityFragment.newInstance(movie, mIsTwoPane))
                .commit();
        }
        else
        {
            DetailsActivity.launch(this, movie, view);
        }
    }
}
