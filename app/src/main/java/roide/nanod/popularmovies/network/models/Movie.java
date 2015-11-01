package roide.nanod.popularmovies.network.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by roide on 9/8/15.
 */
public class Movie implements Parcelable
{
    private int id;
    private String original_title;
    private String overview;
    private String release_date;
    private float popularity;
    private String title;
    private float vote_average;

    private boolean adult;
    private String backdrop_path;
    private int[] genre_ids;
    private String original_language;
    private String poster_path;
    private boolean video;
    private int vote_count;
    private boolean mIsFavorite;

    public Movie() {}

    public int getId()
    {
        return id;
    }

    public String getOriginal_title()
    {
        return original_title;
    }

    public String getOverview()
    {
        return overview;
    }

    public String getRelease_date()
    {
        return release_date;
    }

    public float getPopularity()
    {
        return popularity;
    }

    public String getTitle()
    {
        return title;
    }

    public float getVote_average()
    {
        return vote_average;
    }

    public String getPoster_path()
    {
        return poster_path;
    }

    public String getBackdrop_path()
    {
        return backdrop_path;
    }

    public int getVote_count()
    {
        return vote_count;
    }

    public boolean getIsFavorite()
    {
        return mIsFavorite;
    }

    public void setIsFavorite(boolean isFavorite)
    {
        mIsFavorite = isFavorite;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags)
    {
        destination.writeInt(id);
        destination.writeString(original_title);
        destination.writeString(overview);
        destination.writeString(release_date);
        destination.writeString(title);
        destination.writeString(poster_path);
        destination.writeString(backdrop_path);

        destination.writeInt(vote_count);

        destination.writeFloat(popularity);
        destination.writeFloat(vote_average);

        destination.writeInt(mIsFavorite==true ? 1: 0);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        public Movie createFromParcel(Parcel in)
        {
            return new Movie(in);
        }

        public Movie[] newArray(int size)
        {
            return new Movie[size];
        }
    };

    private Movie(Parcel source)
    {
        id = source.readInt();
        original_title = source.readString();
        overview = source.readString();
        release_date = source.readString();
        title = source.readString();
        poster_path = source.readString();
        backdrop_path = source.readString();

        vote_count = source.readInt();

        popularity = source.readFloat();
        vote_average = source.readFloat();

        mIsFavorite = source.readInt()==1 ? true : false;
    }

}
