package roide.nanod.popularmovies.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.network.models.Videos;

/**
 * Created by roide on 11/1/15.
 */
public class TrailerRowWidget extends FrameLayout
{
    @Bind(R.id.element_trailer_container) View mContainer;
    @Bind(R.id.element_trailer_title) TextView mTrailerName;

    public TrailerRowWidget(Context context)
    {
        this(context, null);
    }

    public TrailerRowWidget(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public TrailerRowWidget(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.element_trailer_row, this, true);
        ButterKnife.bind(this);
    }

    public void setTrailerDetails(Videos.TrailerDetails trailerDetails)
    {
        mTrailerName.setText(trailerDetails.getName());
        mContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
