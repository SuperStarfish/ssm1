package cg.group4.view.util.rewards;

import static org.junit.Assert.assertEquals;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.badlogic.gdx.graphics.Color;

@RunWith(Theories.class)
public class RewardUtilTest {	
	
	@DataPoints
	public static Object[][] params() {
		return new Object[][] {
				{0f, new Color(1, 0, 0, 1)},
				{0.1f, new Color(1, 0.5f, 0, 1)},
				{0.2f, new Color(1, 1, 0, 1)},
				{0.3f, new Color(0.5f, 1, 0, 1)},
				{0.4f, new Color(0, 1, 0, 1)},
				{0.5f, new Color(0, 1, 0.5f, 1)},
				{0.6f, new Color(0, 1, 1, 1)},
				{0.7f, new Color(0, 0.5f, 1, 1)},
				{0.8f, new Color(0, 0, 1, 1)},
				{0.9f, new Color(0.5f, 0, 1, 1)},
				{1f, new Color(1, 0, 1, 1)},
				{2f, new Color(0, 0, 0, 1)} //Default case
				};
	}

	@Theory
	public void generateColorTest(final Object[] param) {
		float hue = (float) param[0];
		Color ans = (Color) param[1];
		
		Color generatedColour = RewardUtil.generateColor(hue);
		assertEquals(ans, generatedColour);
	}

}
