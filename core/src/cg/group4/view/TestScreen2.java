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


public class TestScreen2 extends ScreenLogic{



    public TestScreen2(final WorldRenderer worldRenderer){
        super(worldRenderer);
        cActor = Skinner.getInstance().generateDefaultMenuButton("Screen 1");
        cActor.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                worldRenderer.setScreen(new TestScreen(worldRenderer));
            }
        });
        worldRenderer.setActor(cActor);
    }

}
