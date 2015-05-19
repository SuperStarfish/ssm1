package cg.group4.view;

import cg.group4.StandUp;
import cg.group4.stroll.Stroll;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen that gets shown when someone is on a stroll.
 * @author Nick Cleintuar
 * @author Jean de Leeuw
 * @author Martijn Gribnau
 * @author Benjamin Los
 */
public class StrollScreen implements Screen {
	
	/**
	 * Stage containing all the actors.
	 */
	protected Stage cStage;
	
	/**
	 * Sprite batch containing all the sprites.
	 */
	protected SpriteBatch cSpriteBatch;
	
	
	/**
	 * Texture for the background.
	 */
	protected Texture cStrollBackground;
	
	/**
	 * Skin used for creating the dialog.
	 */
	protected Skin skin;
	
	/**
	 * A screen for the strolls has a current stroll backend.
	 */
	protected Stroll stroll;
	
	/**
	 * Buttonfont.
	 */
	protected BitmapFont cFont;
	
	/**
	 * Variables for keeping track of the window sizes and time.
	 */
	protected int cScreenWidth, cScreenHeight, cTime;

    protected final TimerTask cTimerTask = new TimerTask() {
        @Override
        public void onTick(int seconds) {
            cTime = seconds;
        }

        @Override
        public void onStart(int seconds) {

        }

        @Override
        public void onStop() {

        }
    };

    /**
     * Back button on the screen.
     */
    protected TextButton cBackButton;

	public StrollScreen(){
		super();

		cStage = new Stage();
        skin = new Skin();
        cSpriteBatch = new SpriteBatch();
        cStrollBackground = new Texture(Gdx.files.internal("demobackground.jpg"));
        cFont = new BitmapFont();

        StandUp.getInstance().getTimeKeeper().getTimer("STROLL").subscribe(cTimerTask);


        TextButtonStyle backButtonStyle = new TextButtonStyle();
        backButtonStyle.font = cFont;
        cBackButton = new TextButton("Back", backButtonStyle);
        cBackButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });

        cStage.addActor(cBackButton);
	}
	

	@Override
	public final void show() {
        Gdx.input.setInputProcessor(cStage);
	}

	@Override
	public final void render(final float delta) {
		Gdx.gl.glClearColor(0, 132 / 255f, 197 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cSpriteBatch.begin();
        cSpriteBatch.draw(cStrollBackground, 0, 0);
        cFont.draw(cSpriteBatch, Long.toString(cTime)
                , cScreenWidth / 2f - 10, cScreenHeight - 100);
		cSpriteBatch.end();
		
		cStage.act();
		
        cStage.draw();
	}

	@Override
	public void resize(final int width, final int height) {
        cScreenWidth = width;
		cScreenHeight = height;
        cBackButton.setPosition((cScreenWidth / 2f) - (cBackButton.getWidth() / 2f), 0);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public final void dispose() {
        cTimerTask.dispose();
		cFont.dispose();
		cSpriteBatch.dispose();
		cStrollBackground.dispose();
		cStage.dispose();
	}	
}
