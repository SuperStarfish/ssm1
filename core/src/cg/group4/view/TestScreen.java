package cg.group4.view;

import cg.group4.util.camera.GameSkin;
import cg.group4.util.camera.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class TestScreen extends ScreenLogic{

    public TestScreen(final WorldRenderer worldRenderer){
        super(worldRenderer);
        Table table = new Table();
        table.setFillParent(true);
        table.row().expandY();
        TextButton button1 = cGameSkin.generateDefaultMenuButton("Button1");
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("TAG", "TEST");
            }
        });
        table.add(button1);

        table.row().expandY();
        TextButton button2 = cGameSkin.generateDefaultMenuButton("Button2");
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("TAG", "TEST");
            }
        });
        table.add(button2);

        table.row().expandY();
        TextButton button3 = cGameSkin.generateDefaultMenuButton("Go Back");
        button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                worldRenderer.setScreen(new HomeScreen(worldRenderer));
            }
        });
        table.add(button3);

        table.debugAll();
        worldRenderer.setActor(table);
    }

}
