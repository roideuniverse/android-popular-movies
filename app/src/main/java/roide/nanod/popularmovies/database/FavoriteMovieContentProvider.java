package roide.nanod.popularmovies.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by roide on 11/1/15.
 */
public class FavoriteMovieContentProvider extends ContentProvider {
    private static final String AUTHORITY = "roide.nanod.popularmovies.provider";

    private static final String PATH_MOVIES = FavoriteDbContract.TABLE_NAME;

    private static final int MOVIES = 0x1;

    private static final UriMatcher mUriMatcher;
    private FavoriteDbHelper mDbHelper;

    public static final Uri CONTENT_URI_MOVIES = Uri.parse("content://" + AUTHORITY + "/" + PATH_MOVIES);

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, PATH_MOVIES, MOVIES);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new FavoriteDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (mUriMatcher.match(uri)) {
            case MOVIES:
                queryBuilder.setTables(FavoriteDbContract.TABLE_NAME);
                break;
            default:
                throw new IllegalStateException("Unknown Uri");
        }
        String groupBy = null;
        String having = null;
        Cursor cursor = queryBuilder.query(mDbHelper.getReadableDatabase(),
            projection, selection, selectionArgs, groupBy, having, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = 0;
        String path = null;
        String nullColumnHack = null;
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int conflictAlgorithm = SQLiteDatabase.CONFLICT_REPLACE;

        switch (mUriMatcher.match(uri)) {
            case MOVIES:
                id = database.insertWithOnConflict(FavoriteDbContract.TABLE_NAME,
                    nullColumnHack, values, conflictAlgorithm);
                path = PATH_MOVIES;
                break;
            default:
                throw new IllegalStateException("Unknown Uri");
        }

        ContentObserver observer = null;
        getContext().getContentResolver().notifyChange(uri, observer);

        String insertUri = path + "/" + id;
        return Uri.parse(insertUri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted = 0;
        String id = uri.getLastPathSegment();
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        switch (mUriMatcher.match(uri)) {
            case MOVIES:
                database.delete(FavoriteDbContract.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalStateException("Unknown Uri");
        }

        ContentObserver observer = null;
        getContext().getContentResolver().notifyChange(uri, observer);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated = 0;
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        switch (mUriMatcher.match(uri)) {
            case MOVIES:
                rowsUpdated = database.update(FavoriteDbContract.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
                break;
            default:
                throw new IllegalStateException("Unknown Uri");
        }

        ContentObserver observer = null;
        getContext().getContentResolver().notifyChange(uri, observer);
        return rowsUpdated;
    }
}
