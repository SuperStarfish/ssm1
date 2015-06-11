package cg.group4.view;

import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.data_structures.collection.collectibles.FishA;
import cg.group4.data_structures.collection.collectibles.FishB;
import cg.group4.data_structures.collection.collectibles.FishC;
import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import java.util.HashSet;

/**
 * Screen which displays the collected fish in an aquarium.
 */
public class AquariumScreen implements Screen {

    final String tag = this.getClass().getSimpleName();
    final AquariumRenderer cAquariumRenderer;

    HashSet<CollectibleRenderer> cCollectibleRendererSet;
    // test
    // todo observer + subject
    final Collection fishTank;

    public AquariumScreen() {
        cCollectibleRendererSet = new HashSet<>();
        cAquariumRenderer = new AquariumRenderer();

        fishTank = new Collection("FISH_TANK_COLLECTION");

        fishTank.add(new FishA(1f, "SampleOwnerId")); // todo replace by collectibles from server
        fishTank.add(new FishA(0.5f, "SampleOwnerId"));
        fishTank.add(new FishC(0.2f, "ExampleId"));

        setCollectibleRendererSet(fishTank);

    }

    public void setCollectibleRendererSet(Collection h) {
        for (Collectible c : h) {
            CollectibleRenderer collectibleRenderer = new CollectibleRenderer(c);
            cCollectibleRendererSet.add(collectibleRenderer);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        cAquariumRenderer.render();
//        System.out.println("fps " + (1/delta));
        for (CollectibleRenderer c : cCollectibleRendererSet) {
            c.render();
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
        cAquariumRenderer.dispose();
    }

}
