package roide.nanod.popularmovies.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import roide.nanod.popularmovies.database.FavoriteDbContract;
import roide.nanod.popularmovies.database.FavoriteMovieContentProvider;
import roide.nanod.popularmovies.network.models.Movie;

/**
 * Created by roide on 11/1/15.
 */
public class FavoriteDbUtil {

    private static final int IS_NOT_FAVORITE = 0;
    private static final int IS_FAVORITE = 1;

    private FavoriteDbUtil() {}

    public static void doFavorite(Movie movie, boolean isFavorite, Context context)
    {
        int isFav = isFavorite==true ? IS_FAVORITE : IS_NOT_FAVORITE;
        ContentResolver resolver = context.getContentResolver();
        ContentValues value = new ContentValues();
        value.put(FavoriteDbContract.Entry.MOV_ID, movie.getId());
        value.put(FavoriteDbContract.Entry.MOV_IS_FAVORITE, isFav);
        value.put(FavoriteDbContract.Entry.MOV_NAME, movie.getTitle());

        resolver.insert(FavoriteMovieContentProvider.CONTENT_URI_MOVIES, value);
    }

    public static boolean isFavorite(Movie movie, Context context)
    {
        ContentResolver resolver = context.getContentResolver();
        String[] projection = {
            FavoriteDbContract.Entry.MOV_ID,
            FavoriteDbContract.Entry.MOV_NAME,
            FavoriteDbContract.Entry.MOV_IS_FAVORITE
        };
        String selection = FavoriteDbContract.Entry.MOV_ID + " = ?";
        String[] selectionArgs = {Integer.toString(movie.getId())};
        String sortOrder = null;

        Cursor cursor = resolver.query(
            FavoriteMovieContentProvider.CONTENT_URI_MOVIES,
            projection,
            selection,
            selectionArgs,
            sortOrder);

        int isFavorite = IS_NOT_FAVORITE;
        if(cursor != null)
        {
            if(cursor.moveToNext())
            {
                String id = cursor.getString(cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_ID));
                isFavorite = cursor.getInt(cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_IS_FAVORITE));
                String movieName = cursor.getString(cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_NAME));
            }
            cursor.close();
        }

        return (isFavorite == IS_FAVORITE);
    }
}
