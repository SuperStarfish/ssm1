package cg.group4.view;

import cg.group4.StandUp;
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
 * @author Jean de Leeuw
 * @author Nick Cleintuar
 * @author Martijn Gribnau
 * @author Benjamin Los
 * @author Jurgen van Schagen
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
	 * A button to go to the settings.
	 */
	protected TextButton cButtonSettings;
	
	/**
	 * A button to start a stroll.
	 */
	protected TextButton cButtonStroll;
	
	/**
	 * The stage of the screen.
	 */
	protected Stage cStage;
	
	/**
	 * Font for the screen.
	 */
	protected BitmapFont cFont;
	
    /**
     * Width and height of the window.
     */
	protected int cScreenWidth, cScreenHeight;



    /**
     * TimerTask for the Stroll timer.
     */
    protected final TimerTask cStrollTimerTask = new TimerTask() {
        @Override
        public void onTick(int seconds) {
        }

        @Override
        public void onStart(int seconds) {
        }

        @Override
        public void onStop() {

        }
    };

    /**
     * TimerTask for the interval timer (hourly).
     */
	protected final TimerTask cIntervalTimerTask = new TimerTask() {
		@Override
		public void onTick(int seconds) {
			cButtonStroll.setText("Stroll: " + seconds);
		}

		@Override
		public void onStart(int seconds) {
		}

		@Override
		public void onStop() {
			cButtonStroll.setText("Stroll");
			cButtonStroll.setDisabled(false);
		}
	};

	public MainMenu() {
		super();

		cBatch = new SpriteBatch();
		cBackground = new Texture(Gdx.files.internal("demobackground.jpg"));
		cStage = new Stage();
		cFont = new BitmapFont();

		TextButtonStyle style = new TextButtonStyle();
		style.font = cFont;
		cButtonSettings = new TextButton("Settings", style);
        cButtonStroll = new TextButton("Stroll", style);


        StandUp.getInstance().getTimeKeeper().getTimer("INTERVAL").subscribe(cIntervalTimerTask);
        StandUp.getInstance().getTimeKeeper().getTimer("STROLL").subscribe(cStrollTimerTask);

		cButtonSettings.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Settings());
				Gdx.app.debug("Button", "Settings");
			}
		});
		cButtonStroll.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.debug("Button", "Stroll");
				StandUp.getInstance().startStroll();
			}
		});

		cStage.addActor(cButtonSettings);
		cStage.addActor(cButtonStroll);
	}

	@Override
	public final void show() {
		Gdx.input.setInputProcessor(cStage);
	}
	
	/**
	 * @param delta Delta time
	 */
	@Override
	public final void render(final float delta) {
		final float red = 0f;
		final float green = 132 / 255f;
		final float blue = 197 / 255f;
		final float alpha = 0f;
		
		Gdx.gl.glClearColor(red, green, blue, alpha);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cBatch.begin();
        cBatch.draw(cBackground, 0, 0);
		cBatch.end();
		
		cStage.act();
		
        cStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		cScreenWidth = width;
		cScreenHeight = height;

		cButtonSettings.setPosition(cScreenWidth / 2f - cButtonSettings.getWidth() / 2f, cScreenHeight / 2f);
		cButtonStroll.setPosition(cScreenWidth / 2f - cButtonStroll.getWidth() / 2f, cScreenHeight / 2f + 20);
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
		cIntervalTimerTask.dispose();
		cFont.dispose();
		cBatch.dispose();
		cBackground.dispose();
		cStage.dispose();
	}
}
