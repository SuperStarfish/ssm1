package cg.group4.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class EventScreen implements Screen {

	protected Stage cStage;
	protected SpriteBatch cSpriteBatch;
	protected Texture cBackground;
	protected Label cLabel;
	
	protected BitmapFont cButtonFont;
	
	protected int cScreenWidth, cScreenHeight;
	
	public EventScreen() {
		super();
		cStage = new Stage();
		cSpriteBatch = new SpriteBatch();
		cButtonFont = new BitmapFont();
		cBackground = new Texture(Gdx.files.internal("demobackground.jpg"));
		
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = cButtonFont;
		
		cLabel = new Label("HOI JURGEN", labelStyle);
		
		Table table = new Table();
		table.setFillParent(true);
		table.add(cLabel);
		
		cStage.addActor(table);
		
		cScreenWidth = Gdx.graphics.getWidth();
		cScreenHeight = Gdx.graphics.getHeight();
	}

	@Override
	public final void show() {
		Gdx.input.setInputProcessor(cStage);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 132 / 255f, 197 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cSpriteBatch.begin();
        cSpriteBatch.draw(cBackground, 0, 0);
		cSpriteBatch.end();
		
		cStage.act();
		
        cStage.draw();	
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
		cButtonFont.dispose();
		cSpriteBatch.dispose();
		cBackground.dispose();
		cStage.dispose();
	}

	public Label getLabel() {
		return cLabel;
	}
}
