package roide.nanod.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by roide on 11/1/15.
 */
public class FavoriteDbHelper extends SQLiteOpenHelper {

  public FavoriteDbHelper(Context context) {
    super(context, FavoriteDbContract.DATABASE_NAME, null,
        FavoriteDbContract.DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(FavoriteDbContract.CREATE_FAVORITE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(FavoriteDbContract.DROP_FAVORITE_TABLE);
    onCreate(db);
  }
}
