package roide.nanod.popularmovies.network.apibuilders;

import android.content.Context;

import roide.nanod.popularmovies.R;

/**
 * Created by roide on 9/9/15.
 */
public abstract class BaseApiBuilder
{
    private Context mContext;
    protected Runnable mRunnable;
    private String mApiKey;

    protected BaseApiBuilder(Context context)
    {
        mContext = context;
        mApiKey = context.getString(R.string.api_key);
    }

    public Context getContext()
    {
        return mContext;
    }

    public String getApiKey()
    {
        return mApiKey;
    }

    public BaseApiBuilder execute()
    {
        prepareExecutable();
        if(mRunnable != null)
        {
            mRunnable.run();
        }
        return this;
    }

    public abstract void prepareExecutable();
}
