package cg.group4.view;

import cg.group4.Launcher;
import cg.group4.util.camera.WorldRenderer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Jurgen on 19-5-2015.
 */
public class TestScreen2 implements Screen {
    Launcher cGame;
    WorldRenderer cWorldRenderer;

    public TestScreen2(Launcher game){
        cGame = game;
        cWorldRenderer = cGame.getWorldRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        cWorldRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        cWorldRenderer.resize(width, height);
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
