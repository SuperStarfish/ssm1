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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

/**
 * Class for screen that gets show after the stroll has ended.
 * @author Jean de Leeuw
 */
public class RewardScreen implements Screen {
	
	protected Stage cRewardStage;
	protected SpriteBatch cRewardSpriteBatch;
	protected Texture cRewardBackground;
	
	protected BitmapFont cAcceptButtonFont;
	
	protected int cScreenWidth, cScreenHeight, cEventsCompleted;
	
	public RewardScreen(int eventsCompleted) {
		this.cEventsCompleted = eventsCompleted;
	}

	@Override
	public final void show() {
		cRewardStage = new Stage();
		Gdx.input.setInputProcessor(cRewardStage);
		
		cScreenWidth = Gdx.graphics.getWidth();
		cScreenHeight = Gdx.graphics.getHeight();
		
		cRewardSpriteBatch = new SpriteBatch();
		cRewardBackground = new Texture(Gdx.files.internal("demobackground.jpg"));
		
		cAcceptButtonFont = new BitmapFont();
		TextButtonStyle acceptButtonStyle = new TextButtonStyle();
		acceptButtonStyle.font = cAcceptButtonFont;
		TextButton acceptButton = new TextButton("Accept", acceptButtonStyle);
		
		acceptButton.setPosition(cScreenWidth / 2f - acceptButton.getWidth() / 2f, cScreenHeight / 3f);
		acceptButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
		
		cRewardStage.addActor(acceptButton);
		
	}

	@Override
	public final void render(final float delta) {
		Gdx.gl.glClearColor(0, 132 / 255f, 197 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cRewardSpriteBatch.begin();
        cRewardSpriteBatch.draw(cRewardBackground, 0, 0);
		cRewardSpriteBatch.end();
		
		cRewardStage.act();
		
        cRewardStage.draw();		
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
	public final void hide() {
		// TODO Auto-generated method stub
		dispose();	
	}

	@Override
	public final void dispose() {
		cAcceptButtonFont.dispose();
		cRewardSpriteBatch.dispose();
		cRewardBackground.dispose();
		cRewardStage.dispose();
	}

}
