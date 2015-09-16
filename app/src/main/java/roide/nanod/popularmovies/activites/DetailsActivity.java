package roide.nanod.popularmovies.activites;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import roide.nanod.popularmovies.R;

public class DetailsActivity extends BaseActivity
{
    public static void launch(Context context)
    {
        Intent intent = new Intent(context, DetailsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        prepareActionBar();
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
