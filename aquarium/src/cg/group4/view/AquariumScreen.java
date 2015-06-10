package cg.group4.view;

import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.FishA;
import cg.group4.data_structures.collection.collectibles.FishB;
import cg.group4.data_structures.collection.collectibles.FishC;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Screen which displays the collected fish in an aquarium.
 */
public class AquariumScreen implements Screen {

    final String tag = this.getClass().getSimpleName();
    final AquariumRenderer cAquariumRenderer;
    final SpriteBatch cSpriteBatch;


    // todo observer + subject
    final Collection fishTank;

    public AquariumScreen() {
        cAquariumRenderer = new AquariumRenderer();
        fishTank = new Collection("FISH_TANK_COLLECTION");

        fishTank.add(new FishA(1, "SampleOwnerId")); // todo replace by collectibles from server
        fishTank.add(new FishB(1, "SampleOwnerId"));
        fishTank.add(new FishC(1, "ExampleId"));

        cSpriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.app.log(tag, "FPS: " + 1/delta);

        setBackground();
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
        cSpriteBatch.dispose();
    }

    private void setBackground() {
        Gdx.gl.glClearColor(63, 67, 173f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
