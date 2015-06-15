package cg.group4.util.sensor;

import com.badlogic.gdx.math.Vector3;

public class Gyroscope {
	
	protected SensorReader cReader;
	
	public Gyroscope(final SensorReader reader) {
		cReader = reader;
	}
	
	public Vector3 update() {
		return cReader.readGyroscope();
	}

}
