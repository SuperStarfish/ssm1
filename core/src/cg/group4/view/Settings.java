package cg.group4.view;

import cg.group4.util.timer.TimeKeeper;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Settings implements Screen {

	Stage stage = new Stage();
	TextButton buttonResetInterval;
	TextButton buttonResetStroll;
    TextButton buttonBack;

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
		style.font = new BitmapFont();

		buttonResetInterval = new TextButton("Reset interval", style);
		buttonResetStroll = new TextButton("Reset stroll", style);
        buttonBack = new TextButton("Back", style);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		buttonResetInterval.setPosition(width / 2f - buttonResetInterval.getWidth() / 2f, height / 2f+10);
		buttonResetStroll.setPosition(width / 2f - buttonResetStroll.getWidth() / 2f, height / 2f-10);
        buttonBack.setPosition(width / 2f - buttonBack.getWidth() / 2f, 20);

		buttonResetInterval.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                TimeKeeper.getInstance().getTimer("INTERVAL").reset();
            }
        });
		buttonResetStroll.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				TimeKeeper.getInstance().getTimer("STROLL").reset();
			}
		});
        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                Gdx.app.debug("Button", "MainMenu");
            }
        });

		stage.addActor(buttonResetInterval);
		stage.addActor(buttonResetStroll);
        stage.addActor(buttonBack);	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

		stage.draw();
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
	public void dispose() {
        stage.dispose();
	}

}
