package roide.nanod.popularmovies.database;

/**
 * Created by roide on 11/1/15.
 */
public interface FavoriteDbContract {
    int DATABASE_VERSION = 1;
    String DATABASE_NAME = "FAVORITE.DB";
    String TABLE_NAME = "favorite_table";

    String CREATE_FAVORITE_TABLE =
        "CREATE TABLE " + TABLE_NAME
            + " (" + Entry.MOV_NAME + " TEXT, "
            + Entry.MOV_ID + " INTEGER PRIMARY KEY, "
            + Entry.MOV_IS_FAVORITE + " INTEGER);";

    String DROP_FAVORITE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    interface Entry {
        String MOV_NAME = "name";
        String MOV_ID = "id";
        String MOV_IS_FAVORITE = "is_favorite";
    }
}
