package roide.nanod.popularmovies.activites;

import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.fragments.DiscoveryFragment;
import roide.nanod.popularmovies.ui.SortMenuActionView;

public class DiscoveryActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_discovery);
        if(savedInstanceState == null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_frag_container, new DiscoveryFragment())
                    .commit();
        }
    }

    @Override
    protected void findRequiredViews()
    {

    }
}
