package roide.nanod.popularmovies.exceptions;

/**
 * Created by roide on 9/9/15.
 */
public class ActivityClosingException extends Exception
{
    private static final String MSG = "The Activity is finished or is finished.";

    public ActivityClosingException()
    {
        super(MSG);
    }
}
