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
	protected SpriteBatch cBatch;
	
	/**
	 * Background image of the main menu.
	 */
	protected Texture cBackground;
    
	/**
	 * A button, for testing events.
	 */
	protected TextButton cButton;
	
	/**
	 *  
	 */
	protected Stage cStage;
	protected BitmapFont cFont;
    
	protected int cWidth, cHeight, cTime;

	@Override
	public void show() {
		cBatch = new SpriteBatch();
		cBackground = new Texture(Gdx.files.internal("demobackground.jpg"));
		cStage = new Stage();
		cFont = new BitmapFont();
		
		Gdx.input.setInputProcessor(cStage);

        TextButtonStyle style = new TextButtonStyle();
        style.font = cFont;
        cButton = new TextButton("Settings", style);
        cWidth = Gdx.graphics.getWidth();
        cHeight = Gdx.graphics.getHeight();

        cButton.setPosition(cWidth / 2f - cButton.getWidth() / 2f, cHeight / 2f);
        cButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Settings());
               System.out.println("Settings");
            }
        });

        cStage.addActor(cButton);
	}

	@Override
	public void render(float delta) {
		final float red = 0f;
		final float green = 132 / 255f;
		final float blue = 197 / 255f;
		final float alpha = 0f;
		
		Gdx.gl.glClearColor(red, green, blue, alpha);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cBatch.begin();
        cBatch.draw(cBackground, 0, 0);
        cFont.draw(cBatch, Long.toString(cTime), cWidth / 2f - 10, cHeight - 100);
		cBatch.end();
		
		cStage.act();
		
        cStage.draw();
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
		cFont.dispose();
		cBatch.dispose();
		cBackground.dispose();
		cStage.dispose();
	}
}
