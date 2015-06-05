package cg.group4.server;

import android.content.Context;
import android.provider.Settings;
import cg.group4.client.UserIDResolver;

public class AndroidIDResolver implements UserIDResolver {

    protected Context cContext;

    public AndroidIDResolver(Context context) {
        cContext = context;
    }

    @Override
    public String getID() {
        return  Settings.Secure.getString(cContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
