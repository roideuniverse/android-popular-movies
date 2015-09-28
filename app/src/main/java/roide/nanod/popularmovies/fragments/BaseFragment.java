package roide.nanod.popularmovies.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import roide.nanod.popularmovies.activites.BaseActivity;
import roide.nanod.popularmovies.exceptions.ActivityClosingException;

/**
 * Created by roide on 9/9/15.
 */
public abstract class BaseFragment extends Fragment
{
    private BaseActivity mBaseActivity;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(mBaseActivity != null) return;

        if(! (context instanceof BaseActivity))
        {
            throwInvalidActivityException();
        }
        mBaseActivity = (BaseActivity) context;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        if(mBaseActivity != null) return;

        if(! (activity instanceof BaseActivity))
        {
            throwInvalidActivityException();
        }
        mBaseActivity = (BaseActivity) activity;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        findRequiredViews(view);
        prepareViews();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        loadData();
    }

    private void throwInvalidActivityException()
    {
        throw new ClassCastException("The Activity must be instance of " + BaseActivity.class.getSimpleName());
    }

    public BaseActivity getBaseActivity() throws ActivityClosingException
    {
        if(mBaseActivity == null || mBaseActivity.isFinishing())
        {
            throw new ActivityClosingException();
        }
        return mBaseActivity;
    }

    /**
     * Abstract functions
     */

    protected abstract void findRequiredViews(View rootView);
    protected abstract void prepareViews();
    protected abstract void loadData();
}
