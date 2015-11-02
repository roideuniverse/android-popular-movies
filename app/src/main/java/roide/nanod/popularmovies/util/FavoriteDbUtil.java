package roide.nanod.popularmovies.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import roide.nanod.popularmovies.database.FavoriteDbContract;
import roide.nanod.popularmovies.database.FavoriteMovieContentProvider;
import roide.nanod.popularmovies.network.models.Movie;

/**
 * Created by roide on 11/1/15.
 */
public class FavoriteDbUtil {

    private static final int IS_NOT_FAVORITE = 0;
    private static final int IS_FAVORITE = 1;

    private FavoriteDbUtil() {
    }

    private static List<Movie> getAllFavoriteList(Context context) {
        List<Movie> movieList = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;

        Cursor cursor = resolver.query(
            FavoriteMovieContentProvider.CONTENT_URI_MOVIES,
            projection,
            selection,
            selectionArgs,
            sortOrder);

        if (cursor != null) {
            int indexId = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_ID);
            int indexOrgTitle = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_ORIGINAL_TITLE);
            int indexOverview = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_OVERVIEW);
            int indexRelDate = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_RELEASE_DATE);
            int indexPop = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_POPULARITY);
            int indexTitle = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_TITLE);
            int indexVoteAvg = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_VOTE_AVERAGE);
            int indexBackdrop = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_BACKDROP_PATH);
            int indexPoster = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_POSTER_PATH);
            int indexVoteCount = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_VOTE_COUNT);
            int indexFav = cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_IS_FAVORITE);

            while (cursor.moveToNext()) {
                boolean isFavorite = cursor.getInt(indexFav) == IS_FAVORITE ? true : false;
                Movie movie = new Movie();
                movie.setId(cursor.getInt(indexId));
                movie.setOriginal_title(cursor.getString(indexOrgTitle));
                movie.setOverview(cursor.getString(indexOverview));
                movie.setRelease_date(cursor.getString(indexRelDate));
                movie.setPopularity(cursor.getFloat(indexPop));
                movie.setTitle(cursor.getString(indexTitle));
                movie.setVote_average(cursor.getFloat(indexVoteAvg));
                movie.setBackdrop_path(cursor.getString(indexBackdrop));
                movie.setPoster_path(cursor.getString(indexPoster));
                movie.setVote_count(cursor.getInt(indexVoteCount));
                movie.setIsFavorite(isFavorite);

                movieList.add(movie);
            }
            cursor.close();
        }
        return movieList;
    }

    public static void saveMovie(Movie movie, Context context) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues value = new ContentValues();
        value.put(FavoriteDbContract.Entry.MOV_ID, movie.getId());
        value.put(FavoriteDbContract.Entry.MOV_ORIGINAL_TITLE, movie.getOriginal_title());
        value.put(FavoriteDbContract.Entry.MOV_OVERVIEW, movie.getOverview());
        value.put(FavoriteDbContract.Entry.MOV_RELEASE_DATE, movie.getRelease_date());
        value.put(FavoriteDbContract.Entry.MOV_POPULARITY, movie.getPopularity());
        value.put(FavoriteDbContract.Entry.MOV_TITLE, movie.getTitle());
        value.put(FavoriteDbContract.Entry.MOV_VOTE_AVERAGE, movie.getVote_average());
        value.put(FavoriteDbContract.Entry.MOV_BACKDROP_PATH, movie.getBackdrop_path());
        value.put(FavoriteDbContract.Entry.MOV_POSTER_PATH, movie.getPoster_path());
        value.put(FavoriteDbContract.Entry.MOV_VOTE_COUNT, movie.getVote_count());
        int isFav = movie.getIsFavorite() == true ? IS_FAVORITE : IS_NOT_FAVORITE;
        value.put(FavoriteDbContract.Entry.MOV_IS_FAVORITE, isFav);

        resolver.insert(FavoriteMovieContentProvider.CONTENT_URI_MOVIES, value);
    }

    public static boolean isFavorite(Movie movie, Context context) {
        ContentResolver resolver = context.getContentResolver();
        String[] projection = {
            FavoriteDbContract.Entry.MOV_ID,
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
        if (cursor != null) {
            if (cursor.moveToNext()) {
                isFavorite = cursor.getInt(cursor.getColumnIndex(FavoriteDbContract.Entry.MOV_IS_FAVORITE));
            }
            cursor.close();
        }

        return (isFavorite == IS_FAVORITE);
    }
}
