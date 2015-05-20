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

/**
 * Class for screen that gets show after the stroll has ended.
 * @author Jean de Leeuw
 */
public class RewardScreen implements Screen {
	
	protected Stage cStage;
	protected SpriteBatch cSpriteBatch;
	protected Texture cBackground;
	
	protected BitmapFont cFont;
	
	protected int cScreenWidth, cScreenHeight, cReward;

    protected TextButton cAcceptButton;
	


	public RewardScreen(int reward){
        super();
        cReward = reward;

        cStage = new Stage();

        cSpriteBatch = new SpriteBatch();
        cBackground = new Texture(Gdx.files.internal("demobackground.jpg"));

        cFont = new BitmapFont();
        TextButtonStyle acceptButton = new TextButtonStyle();
        acceptButton.font = cFont;
        cAcceptButton = new TextButton("Accept", acceptButton);

//        cAcceptButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(final ChangeEvent event, final Actor actor) {
//                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
//            }
//        });

        cStage.addActor(cAcceptButton);
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
        cSpriteBatch.draw(cBackground, 0, 0);
        cFont.draw(cSpriteBatch, Integer.toString(cReward)
                , cScreenWidth / 2f - 10, cScreenHeight - 100);
		cSpriteBatch.end();
		
		cStage.act();
		
        cStage.draw();
	}

	@Override
	public void resize(final int width, final int height) {
		cScreenWidth = width;
		cScreenHeight = height;
        cAcceptButton.setPosition(cScreenWidth / 2f - cAcceptButton.getWidth() / 2f, cScreenHeight / 3f);
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
		cFont.dispose();
		cSpriteBatch.dispose();
		cBackground.dispose();
		cStage.dispose();
	}

}
