/**
 * 
 */
package cg.group4.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * @author CG Group 4
 * 
 * Class responsible for the definition of the main menu.
 *
 */
public class MainMenu implements Screen {
	
	private Texture tex = new Texture(Gdx.files.internal("badlogic.jpg"));
	private Image welcomeImage = new Image(tex);
	private Stage mainMenuStage = new Stage();
	
	@Override
	public void show() {
		mainMenuStage.addActor(welcomeImage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1); // black
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mainMenuStage.act();
		mainMenuStage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
