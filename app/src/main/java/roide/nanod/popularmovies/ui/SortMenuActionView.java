package roide.nanod.popularmovies.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.List;

import roide.nanod.popularmovies.R;

/**
 * Created by roide on 9/27/15.
 */
public class SortMenuActionView extends ActionMenuView
{
    private Spinner mSpinner;

    public SortMenuActionView(Context context, List<String> spinnerItem)
    {
        super(context);
        addSpinner(spinnerItem);
    }

    private void addSpinner(List<String> spinnerItem)
    {
        mSpinner = new Spinner(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item,
                spinnerItem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        addView(mSpinner);
    }

    public Spinner getSpinner()
    {
        return mSpinner;
    }
}
