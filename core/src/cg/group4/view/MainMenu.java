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
    protected SpriteBatch cBatch = new SpriteBatch();

    /**
     * Background image of the main menu.
     */
    protected Texture cBackground = new Texture(Gdx.files.internal("demobackground.jpg"));

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
    protected Stage cStage = new Stage();

    /**
     * Font for the screen.
     */
    protected BitmapFont cFont = new BitmapFont();

    /**
     * Width and height of the window.
     */
    protected int cWidth, cHeight;

    @Override
    public final void show() {
        Gdx.input.setInputProcessor(cStage);

        TextButtonStyle style = new TextButtonStyle();
        style.font = cFont;
        cButtonSettings = new TextButton("Settings", style);
        cButtonStroll = new TextButton("Stroll", style);

        cWidth = Gdx.graphics.getWidth();
        cHeight = Gdx.graphics.getHeight();

        cButtonSettings.setPosition(cWidth / 2f - cButtonSettings.getWidth() / 2f, cHeight / 2f);
        cButtonStroll.setPosition(cWidth / 2f - cButtonStroll.getWidth() / 2f, cHeight / 2f + 20);

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
            }
        });

        cStage.addActor(cButtonSettings);
        cStage.addActor(cButtonStroll);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void onTick(int seconds) {
                cButtonStroll.setText("Stroll: " + seconds);
            }

            @Override
            public void onStart() {
                cButtonStroll.setText("Stroll: ");
                cButtonStroll.setDisabled(true);
            }

            @Override
            public void onStop() {
                cButtonStroll.setText("Stroll");
                cButtonStroll.setDisabled(false);
            }
        };
        TimeKeeper.getInstance().getTimer("INTERVAL").subscribe(timerTask);
    }

    /**
     * @param delta Delta time
     */
    @Override
    public final void render(float delta) {
        Gdx.gl.glClearColor(0, 132 / 255f, 197 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cBatch.begin();
        cBatch.draw(cBackground, 0, 0);
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
    public final void dispose() {
        cFont.dispose();
        cBatch.dispose();
        cBackground.dispose();
        cStage.dispose();
    }
}
