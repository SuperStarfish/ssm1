package cg.group4.view.screen_mechanics;

import cg.group4.Launcher;
import cg.group4.util.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * @author Jurgen van Schagen
 */
public class LoadingScreen implements Screen {
    protected Assets cAssets;

    protected Launcher cLauncher;

    public LoadingScreen(Launcher launcher) {
        cLauncher = launcher;
        cAssets = Assets.getInstance();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(cAssets.update()) {
            cLauncher.assetsdone();
        } else {
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
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
