package cg.group4.view;

import cg.group4.Launcher;
import cg.group4.util.camera.WorldRenderer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Created by Jurgen on 19-5-2015.
 */
public class TestScreen2 implements Screen {
    Launcher cGame;
    Table table;


    public TestScreen2(Launcher game){
        cGame = game;
    }

    @Override
    public void show() {
        table = new Table();
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/blow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.borderColor = Color.BLACK;
        fontParameter.borderWidth = 2;
        fontParameter.color = Color.WHITE;
        fontParameter.size = 58;
        BitmapFont buttonFont = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = Color.GREEN;
        buttonStyle.font = buttonFont;

        TextButton button = new TextButton("Start", buttonStyle);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("Button", "Start");
            }
        });

        table.row().expandY();
        table.add(button);

        cGame.getWorldRenderer().setActor(table);
    }

    @Override
    public void render(float delta) {

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

    }
}
