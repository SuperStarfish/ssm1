package cg.group4;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
 * The StandUp class serves as an input point for the LibGDX application.
 * This class handles all the cycles that LibGDX goes through and thus
 * serves as the backbone of the entire application.
 */
public class StandUp extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
    TextButton button;
	Stage stage;
    BitmapFont font;
    long timeInFive;
    int width, height;
	
	@Override
	public void create () {
        stage = new Stage();
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("demobackground.jpg"));
        Gdx.input.setInputProcessor(stage);

        setTimer(1);

        font = new BitmapFont();

        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        button = new TextButton("Other Scene", style);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        button.setPosition(width / 2f - button.getWidth() / 2f, height / 2f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("test");
            }
        });

        stage.addActor(button);
//		stage = new Stage();
//		Gdx.input.setInputProcessor(stage);
//
//		BitmapFont font = new BitmapFont();
//		skin = new Skin();
//		skin.add("default", font);
//
//		Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
//		pixmap.setColor(Color.WHITE);
//		pixmap.fill();
//		skin.add("background",new Texture(pixmap));
//
//		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//		textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
//		textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
//		textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
//		textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
//		textButtonStyle.font = skin.getFont("default");
//		skin.add("default", textButtonStyle);
//
//		TextButton newGameButton = new TextButton("New game", skin);
//        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
//        stage.addActor(newGameButton);
	}

    /**
     * setTimer sets the internal countdown timer to n minutes in the future.
     * @param n Amount of minutes
     */
    private void setTimer(int n){
        timeInFive = System.currentTimeMillis() + n * 60 * 1000;
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 132 / 255f, 197 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        batch.draw(background, 0, 0);
        long time = (timeInFive - System.currentTimeMillis()) / 1000;
        if(time < 0) setTimer(2);
        font.draw(batch, Long.toString(time), width / 2f - 10, height - 100);
//		stage.act();
		batch.end();

        stage.draw();
	}

    /**
     * A really simple test method in order to make sure that tests work.
     * Will be removed later.
     * @return 2
     */
	public static int testerooni() {
		return 2;
	}
}
