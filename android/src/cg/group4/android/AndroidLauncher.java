package cg.group4.android;

import android.hardware.SensorManager;
import android.os.Bundle;
import cg.group4.Launcher;
import cg.group4.server.AndroidIDResolver;
import cg.group4.util.notification.AndroidNotificationController;
import cg.group4.util.sensor.AndroidAccelerationStatus;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * The AndroidLauncher class runs the application on an Android device.
 */
public class AndroidLauncher extends AndroidApplication {

    /**
     * System sensor service of Android. Used for the accelerometer.
     */
    protected SensorManager cSensorManager;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useWakelock = true;
        initialize(new Launcher(
                        new AndroidAccelerationStatus(cSensorManager),
                        new AndroidNotificationController(this),
                        new AndroidIDResolver(getContext())),
                config);
    }
}
