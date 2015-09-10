package roide.nanod.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roide.nanod.popularmovies.network.apibuilders.DiscoverMoviesBuilder;
import roide.nanod.popularmovies.network.models.Movie;

public class MainDiscoveryActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_discovery);

        DiscoverMoviesBuilder.build(getApplicationContext())
                .setPage(1)
                .setCallback(new Callback<List<Movie>>()
                {
                    @Override
                    public void success(List<Movie> movies, Response response)
                    {
                        for(Movie movie : movies)
                        {
                            Log.d("kaushik", "title=" + movie.getTitle());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {

                    }
                })
                .execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_discovery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
