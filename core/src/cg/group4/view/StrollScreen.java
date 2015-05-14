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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen that gets shown when someone is on a stroll.
 * @author Nick Cleintuar
 * @author Jean de Leeuw
 */
public class StrollScreen implements Screen {
	
	protected Stage cStrollStage;
	protected SpriteBatch cStrollSpriteBatch;
	protected Texture cStrollBackground;
	
	protected BitmapFont cBackButtonFont;
	
	protected int cScreenWidth, cScreenHeight, cTime;
	

	@Override
	public void show() {
		// TODO Auto-generated method stub
		cStrollStage = new Stage();
		Gdx.input.setInputProcessor(cStrollStage);
		
		cScreenWidth = Gdx.graphics.getWidth();
		cScreenHeight = Gdx.graphics.getHeight();
		
		cStrollSpriteBatch = new SpriteBatch();
		cStrollBackground = new Texture(Gdx.files.internal("demobackground.jpg"));
		
		cBackButtonFont = new BitmapFont();
		TextButtonStyle backButtonStyle = new TextButtonStyle();
		backButtonStyle.font = cBackButtonFont;
		TextButton backButton = new TextButton("Back", backButtonStyle);
		
		backButton.setPosition(cScreenWidth / 2f - backButton.getWidth() / 2f, 0);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
//				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());;
				Stroll sc = new Stroll(cStrollStage);
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
        cBackButtonFont.draw(cStrollSpriteBatch, Long.toString(cTime), cScreenWidth / 2f - 10, cScreenHeight - 100);
		cStrollSpriteBatch.end();
		
		cStrollStage.act();
		
        cStrollStage.draw();
	}

	@Override
	public void resize(int width, int height) {
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
