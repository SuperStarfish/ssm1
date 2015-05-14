package cg.group4.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


/**
 * Displays the main menu screen.
 */
public class MainMenu implements Screen {
	
	/**
	 * Stores the background for drawing on the screen.
	 */
	SpriteBatch batch = new SpriteBatch();
	
	/**
	 * Background image of the main menu.
	 */
	Texture background = new Texture(Gdx.files.internal("demobackground.jpg"));
    
	/**
	 * A button, for testing events.
	 */
	TextButton button;
	
	/**
	 * Stage containing all the actors.
	 */
	Stage stage = new Stage();
	BitmapFont font = new BitmapFont();
    
	int width, height, time;

	@Override
	public final void show() {
		Gdx.input.setInputProcessor(stage);

        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        button = new TextButton("Settings", style);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        button.setPosition(width / 2f - button.getWidth() / 2f, height / 2f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Settings());
               System.out.println("Settings");
            }
        });

        stage.addActor(button);
	}

	@Override
	public final void render(final float delta) {
		Gdx.gl.glClearColor(0, 132 / 255f, 197 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
        batch.draw(background, 0, 0);
        font.draw(batch, Long.toString(time), width / 2f - 10, height - 100);
		batch.end();
		
		stage.act();
		
        stage.draw();
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
		font.dispose();
		batch.dispose();
		background.dispose();
		stage.dispose();
	}
}
