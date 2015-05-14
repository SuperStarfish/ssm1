package cg.group4.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class StrollScreen implements Screen{
	
	protected Stage strollStage;
	protected SpriteBatch strollSpriteBatch;
	protected Texture strollBackground;
	
	protected BitmapFont backButtonFont;
	
	protected int screenWidth, screenHeight, time;
	

	@Override
	public void show() {
		// TODO Auto-generated method stub
		strollStage = new Stage();
		Gdx.input.setInputProcessor(strollStage);
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		strollSpriteBatch = new SpriteBatch();
		strollBackground = new Texture(Gdx.files.internal("demobackground.jpg"));
		
		backButtonFont = new BitmapFont();
		TextButtonStyle backButtonStyle = new TextButtonStyle();
		backButtonStyle.font = backButtonFont;
		TextButton backButton = new TextButton("Back", backButtonStyle);
		
		backButton.setPosition(screenWidth / 2f - backButton.getWidth() / 2f, 0);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
//				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
				displayStrollComplete();
				//Stroll sc = new Stroll(this);
			}
		});
		
		strollStage.addActor(backButton);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 132 / 255f, 197 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		strollSpriteBatch.begin();
        strollSpriteBatch.draw(strollBackground, 0, 0);
        backButtonFont.draw(strollSpriteBatch, Long.toString(time), screenWidth / 2f - 10, screenHeight - 100);
		strollSpriteBatch.end();
		
		strollStage.act();
		
        strollStage.draw();
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
	public void dispose() {
		backButtonFont.dispose();
		strollSpriteBatch.dispose();
		strollBackground.dispose();
		strollStage.dispose();
	}
	
	public void displayStrollComplete() {
		WindowStyle style = new WindowStyle();
		style.titleFont = new BitmapFont();
		Dialog dialog = new Dialog("STROLL COMPLETE BITCHES!", style);
		strollStage.addActor(dialog);
	}

}
