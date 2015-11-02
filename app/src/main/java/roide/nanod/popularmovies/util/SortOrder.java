package roide.nanod.popularmovies.util;

import roide.nanod.popularmovies.network.MovieAPI;

/**
 * Created by roide on 11/1/15.
 */
public enum SortOrder
{
    HIGHEST_RATED("vote_average.desc"),
    MOST_POPULAR("popularity.desc"),
    FAVORITE("favorite");

    private final String mSortOrder;
    SortOrder(String sortOrder)
    {
        mSortOrder = sortOrder;
    }

    @Override
    public String toString()
    {
        return mSortOrder;
    }
}
