package roide.nanod.popularmovies.database;

/**
 * Created by roide on 11/1/15.
 */
public interface FavoriteDbContract {
    int DATABASE_VERSION = 4;
    String DATABASE_NAME = "FAVORITE.DB";
    String TABLE_NAME = "favorite_table";

    String CREATE_FAVORITE_TABLE =
        "CREATE TABLE " + TABLE_NAME
            + " ("
            + Entry.MOV_TITLE + " TEXT, "
            + Entry.MOV_ID + " INTEGER PRIMARY KEY, "
            + Entry.MOV_OVERVIEW + " TEXT, "
            + Entry.MOV_RELEASE_DATE + " TEXT, "
            + Entry.MOV_POPULARITY + " REAL, "
            + Entry.MOV_ORIGINAL_TITLE + " TEXT, "
            + Entry.MOV_VOTE_AVERAGE + " REAL, "
            + Entry.MOV_BACKDROP_PATH + " TEXT, "
            + Entry.MOV_POSTER_PATH + " TEXT, "
            + Entry.MOV_VOTE_COUNT + " INTEGER, "
            + Entry.MOV_IS_FAVORITE + " INTEGER );";

    String DROP_FAVORITE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    interface Entry {
        String MOV_ID = "id";
        String MOV_TITLE = "title";
        String MOV_OVERVIEW = "overview";
        String MOV_RELEASE_DATE = "relase_date";
        String MOV_POPULARITY = "popularity";
        String MOV_ORIGINAL_TITLE = "original_title";
        String MOV_VOTE_AVERAGE = "vote_average";
        String MOV_BACKDROP_PATH = "backdrop_path";
        String MOV_POSTER_PATH = "poster_path";
        String MOV_VOTE_COUNT = "cote_count";
        String MOV_IS_FAVORITE = "is_favorite";
    }
}
