package cg.group4.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Renderer for the Aquarium display.
 * Displays the style of the aquarium box.
 */
public class AquariumRenderer {

    public AquariumRenderer() {
    }

    public void render() {
        setBackground();
    }



    public void dispose() {
    }

    public void setBackground() {
        Gdx.gl.glClearColor(63, 67, 173f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
