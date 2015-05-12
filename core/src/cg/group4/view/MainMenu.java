package cg.group4.view;

import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.TimerTask;
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
	 * A button to go to the settings.
	 */
	TextButton buttonSettings;

    /**
     * A button to start a stroll.
     */
    TextButton buttonStroll;
	
	/**
	 *  
	 */
	Stage stage = new Stage();
	BitmapFont font = new BitmapFont();
    
	int width, height;

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        buttonSettings = new TextButton("Settings", style);
        buttonStroll = new TextButton("Stroll",style);

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        buttonSettings.setPosition(width / 2f - buttonSettings.getWidth() / 2f, height / 2f);
        buttonStroll.setPosition(width / 2f - buttonStroll.getWidth() / 2f, height / 2f+20);

        buttonSettings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Settings());
                Gdx.app.debug("Button", "Settings");
            }
        });
        buttonStroll.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("Button", "Stroll");
            }
        });

        stage.addActor(buttonSettings);
        stage.addActor(buttonStroll);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void onTick(int seconds) {
                buttonStroll.setText("Stroll: " +seconds);
            }

            @Override
            public void onStart() {
                buttonStroll.setText("Stroll: ");
                buttonStroll.setDisabled(true);
            }

            @Override
            public void onStop() {
                buttonStroll.setText("Stroll");
                buttonStroll.setDisabled(false);
            }
        };
        TimeKeeper.getInstance().getTimer("INTERVAL").subscribe(timerTask);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 132 / 255f, 197 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0);
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
