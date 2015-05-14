package cg.group4.view;

import cg.group4.stroll.Stroll;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen that gets shown when someone is on a stroll.
 * @author Nick Cleintuar
 * @author Jean de Leeuw
 */
public class StrollScreen implements Screen {
	
	/**
	 * Stage containing all the actors.
	 */
	protected Stage cStrollStage;
	
	/**
	 * Sprite batch containing all the sprites.
	 */
	protected SpriteBatch cStrollSpriteBatch;
	
	
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
	protected BitmapFont cBackButtonFont;
	
	/**
	 * Variables for keeping track of the window sizes and time.
	 */
	protected int cScreenWidth, cScreenHeight, cTime;
	

	@Override
	public final void show() {
		// TODO Auto-generated method stub
		cStrollStage = new Stage();
		Gdx.input.setInputProcessor(cStrollStage);
		
		cScreenWidth = Gdx.graphics.getWidth();
		cScreenHeight = Gdx.graphics.getHeight();
		
		stroll = new Stroll(cStrollStage);
		
		skin = new Skin();
		
		cStrollSpriteBatch = new SpriteBatch();
		cStrollBackground = new Texture(Gdx.files.internal("demobackground.jpg"));
		
		
		// Code below here should be replaced to a place where we can initialise the skin.
		cBackButtonFont = new BitmapFont();
		TextButtonStyle backButtonStyle = new TextButtonStyle();
		backButtonStyle.font = cBackButtonFont;
		TextButton backButton = new TextButton("Back", backButtonStyle);
		WindowStyle wstyle = new WindowStyle();
		LabelStyle lstyle = new LabelStyle();
		lstyle.font = cBackButtonFont;
		wstyle.titleFont = cBackButtonFont;
		//
		
		skin.add("dialog", backButton, TextButton.class);
		skin.add("dialog", backButtonStyle, TextButtonStyle.class);
		skin.add("dialog", cBackButtonFont, BitmapFont.class);
		skin.add("dialog", wstyle, WindowStyle.class);
		skin.add("default", lstyle, LabelStyle.class);
		skin.add("default", backButton, TextButton.class);
		skin.add("default", backButtonStyle, TextButtonStyle.class);
		skin.add("default", cBackButtonFont, BitmapFont.class);
		skin.add("default", wstyle, WindowStyle.class);
		
		backButton.setPosition(cScreenWidth / 2f - backButton.getWidth() / 2f, 0);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
//				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());;
				new Dialog("Some Dialog", skin, "dialog") {
				    protected void result(final Object object) {
				        
				    }
				}.text("An event has started!").button("OK!", true).show(cStrollStage);
			}
		});
		
		cStrollStage.addActor(backButton);
	}

	@Override
	public final void render(final float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 132 / 255f, 197 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cStrollSpriteBatch.begin();
        cStrollSpriteBatch.draw(cStrollBackground, 0, 0);
        cBackButtonFont.draw(cStrollSpriteBatch, Long.toString(cTime)
        		, cScreenWidth / 2f - 10, cScreenHeight - 100);
		cStrollSpriteBatch.end();
		
		cStrollStage.act();
		
        cStrollStage.draw();
	}

	@Override
	public void resize(final int width, final int height) {
		// TODO Auto-generated method stub
		
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
		cBackButtonFont.dispose();
		cStrollSpriteBatch.dispose();
		cStrollBackground.dispose();
		cStrollStage.dispose();
	}	
}
