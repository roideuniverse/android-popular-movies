package roide.nanod.popularmovies.recyclerview.loadmore;

import roide.nanod.popularmovies.recyclerview.base.BaseModel;

/**
 * Created by roide on 11/1/15.
 */
public class LoadMoreModel extends BaseModel {
    public static final int VIEW_TYPE = 1;
    @Override
    public int getType() {
        return VIEW_TYPE;
    }
}
