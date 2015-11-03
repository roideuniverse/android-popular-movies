package roide.nanod.popularmovies.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import roide.nanod.popularmovies.R;
import roide.nanod.popularmovies.network.models.Review;

/**
 * Created by roide on 11/2/15.
 */
public class WidgetReviewEntry extends FrameLayout {

    @Bind(R.id.element_reviews_author_name) TextView mAuthorName;
    @Bind(R.id.element_reviews_text_view) TextView mReview;

    public WidgetReviewEntry(Context context) {
        this(context, null);
    }

    public WidgetReviewEntry(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetReviewEntry(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.element_reivew_entry, this, true);
        ButterKnife.bind(this);
    }

    public void setReview(Review.ReviewDetails reviewDetails)
    {
        mAuthorName.setText(reviewDetails.getAuthor());
        mReview.setText(reviewDetails.getContent());
    }
}
