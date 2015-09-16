package roide.nanod.popularmovies.ui;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by kaushiksaurabh on 9/13/15.
 */
public class ViewLocation implements Parcelable
{
    private int mLeft;
    private int mRight;
    private int mTop;
    private int mBottom;
    private int mOrientation;

    private ViewLocation()
    {

    }

    public int getLeft()
    {
        return mLeft;
    }

    public void setLeft(int left)
    {
        mLeft = left;
    }

    public int getRight()
    {
        return mRight;
    }

    public void setRight(int right)
    {
        mRight = right;
    }

    public int getTop()
    {
        return mTop;
    }

    public void setTop(int top)
    {
        mTop = top;
    }

    public int getBottom()
    {
        return mBottom;
    }

    public void setBottom(int bottom)
    {
        mBottom = bottom;
    }

    public int getOrientation()
    {
        return mOrientation;
    }

    public void setOrientation(int orientation)
    {
        mOrientation = orientation;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents()
    {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(mLeft);
        dest.writeInt(mRight);
        dest.writeInt(mTop);
        dest.writeInt(mBottom);
        dest.writeInt(mOrientation);
    }

    public static final Creator<ViewLocation> CREATOR = new Creator<ViewLocation>()
    {
        @Override
        public ViewLocation createFromParcel(Parcel in)
        {
            return new ViewLocation(in);
        }

        @Override
        public ViewLocation[] newArray(int size)
        {
            return new ViewLocation[size];
        }
    };

    private ViewLocation(Parcel in)
    {
        mLeft = in.readInt();
        mRight = in.readInt();
        mTop = in.readInt();
        mBottom = in.readInt();
        mOrientation = in.readInt();
    }

    public static ViewLocation getLocation(View view)
    {
        ViewLocation viewLocation = new ViewLocation();
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);

        return viewLocation;
    }
}
