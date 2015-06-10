package cg.group4.client;

import android.content.Context;
import android.provider.Settings;
import cg.group4.client.UserIDResolver;

/**
 * Gets the user is from the android application.
 */
public class AndroidIDResolver implements UserIDResolver {

    /**
     * The context of the android game application.
     */
    protected Context cContext;

    /**
     * Constructs a new android is resolver.
     *
     * @param context The game context.
     */
    public AndroidIDResolver(final Context context) {
        cContext = context;
    }

    @Override
    public String getID() {
        return Settings.Secure.getString(cContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
