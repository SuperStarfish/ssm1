package cg.group4.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Renderer for the Aquarium display.
 */
public class AquariumRenderer {

    protected OrthographicCamera cCamera;
    protected AquariumAssets cAquariumAssets;
    protected Stage cStage;
    protected SpriteBatch cSpriteBatch;

    public AquariumRenderer() {
        cCamera = new OrthographicCamera();
        cAquariumAssets = new AquariumAssets();
        cStage = new Stage();
    }

    public void render() {
        setBackground();
        cCamera.update();
        cStage.draw();
    }

    public void dispose() {
        cSpriteBatch.dispose();
        cStage.dispose();
    }

    public void setBackground() {
        Gdx.gl.glClearColor(63, 67, 173f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
