package cg.group4.view;

import cg.group4.StandUp;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen where users can set their settings.
 * @author Benjamin Los
 * @author Martijn Gribnau
 */
public class Settings implements Screen {
	
	/**
	 * Create the stage for adding all the various actions.
	 */
	protected Stage cStage;
	
	/**
	 * Development button to reset the interval timer.
	 */
	protected TextButton cButtonResetInterval;
	
	/**
	 * Development button to reset the stroll timer.
	 */
	protected TextButton cButtonResetStroll;

    /**
     * Development button to set the stroll timer to zero.
     */
    protected TextButton cButtonStopInterval;
	
	/**
	 * Button to go back to the main menu.
	 */
    protected TextButton cButtonBack;

	protected final int cOffset = 10;
	protected int cScreenWidth, cScreenHeight;

    public Settings() {
        super();

        cStage = new Stage();

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();

        cButtonResetInterval = new TextButton("Reset interval", style);
        cButtonResetStroll = new TextButton("Reset stroll", style);
        cButtonStopInterval = new TextButton("Stop interval", style);
        cButtonBack = new TextButton("Back", style);

        cButtonResetInterval.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                StandUp.getInstance().getTimeKeeper().getTimer("INTERVAL").reset();
            }
        });
        cButtonResetStroll.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                StandUp.getInstance().getTimeKeeper().getTimer("STROLL").reset();
            }
        });
        cButtonStopInterval.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                StandUp.getInstance().getTimeKeeper().getTimer("INTERVAL").stop();
            }
        });
        cButtonBack.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                Gdx.app.debug("Button", "MainMenu");
            }
        });

        cStage.addActor(cButtonResetInterval);
        cStage.addActor(cButtonResetStroll);
        cStage.addActor(cButtonStopInterval);
        cStage.addActor(cButtonBack);
    }

	@Override
	public final void show() {
		Gdx.input.setInputProcessor(cStage);
    }

	@Override
	public final void render(float delta) {
		final float red = 1f;
		final float green = 0f;
		final float blue = 0f;
		final float alpha = 1f;
		
        Gdx.gl.glClearColor(red, green, blue, alpha);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cStage.act();

		cStage.draw();
	}

	@Override
	public void resize(int width, int height) {
        cScreenWidth = width;
        cScreenHeight = height;
		cButtonResetInterval.setPosition(width / 2f - cButtonResetInterval.getWidth() / 2f, height / 2f + cOffset);
		cButtonResetStroll.setPosition(width / 2f - cButtonResetStroll.getWidth() / 2f, height / 2f - cOffset);
        cButtonStopInterval.setPosition(width / 2f - cButtonStopInterval.getWidth() / 2f, height / 2f - 3*cOffset);
        cButtonBack.setPosition(cScreenWidth / 2f - cButtonBack.getWidth() / 2f, 2 * cOffset);
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
	public final void dispose() {
        cStage.dispose();
	}

}
