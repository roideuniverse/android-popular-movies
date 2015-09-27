package roide.nanod.popularmovies.util;

/**
 * Created by roide on 9/27/15.
 */
public final class Util
{
    private static final String W154 = "http://image.tmdb.org/t/p/w154";
    private static final String W500 = "http://image.tmdb.org/t/p/w500";
    private Util() {};

    public static String getW154ImageUrl(String token)
    {
        return W154 + token;
    }

    public static String getW500ImageUrl(String token)
    {
        return W500 + token;
    }
}
