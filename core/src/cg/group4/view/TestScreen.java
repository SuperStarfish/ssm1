package cg.group4.view;

import cg.group4.Launcher;
import cg.group4.util.camera.Skinner;
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


public class TestScreen extends ScreenLogic{

    public TestScreen(final WorldRenderer worldRenderer){
        super(worldRenderer);
        Table table = new Table();
        table.setFillParent(true);
        table.row().expandY();
        TextButton button1 = Skinner.getInstance().generateDefaultMenuButton("Button1");
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("TAG", "TEST");
            }
        });
        table.add(button1);

        table.row().expandY();
        TextButton button2 = Skinner.getInstance().generateDefaultMenuButton("Button2");
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("TAG", "TEST");
            }
        });
        table.add(button2);

        table.row().expandY();
        TextButton button3 = Skinner.getInstance().generateDefaultMenuButton("Go Back");
        button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                worldRenderer.setScreen(new TestScreen2(worldRenderer));
            }
        });
        table.add(button3);

        table.debugAll();
        worldRenderer.setActor(table);
    }

}
