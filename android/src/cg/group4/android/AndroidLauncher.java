package cg.group4.android;

import android.os.Bundle;

import android.provider.Settings;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import cg.group4.Launcher;

/**
 * The AndroidLauncher class runs the application on an Android device.
 */
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;
		String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println(id);
        System.out.println(id);
        System.out.println(id);
        System.out.println(id);
        System.out.println(id);
        System.out.println(id);
        System.out.println(id);
        System.out.println(id);
        System.out.println(id);
        System.out.println(id);
        initialize(new Launcher(), config);
	}
}
