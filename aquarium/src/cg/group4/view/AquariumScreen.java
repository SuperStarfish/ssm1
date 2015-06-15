package cg.group4.view;

import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.data_structures.subscribe.Subject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

/**
 * Screen which displays the collected fish in an aquarium.
 */
public class AquariumScreen implements Screen {

    /**
     * Tag for debug prints.
     */
    final String tag = this.getClass().getSimpleName();

    /**
     *
     */
    final Stage cStage;

    /**
     *
     */
    HashSet<CollectibleRenderer> cCollectibleRendererSet;

    /**
     *
     */
    final Style cStyle;

    /**
     *
     */
    Label cOwnerLabel;

    /**
     *
     */
    Label cDateLabel;

    /**
     *
     */
    Table cLabelTable;

    /**
     *
     */
    public AquariumScreen(Collection collection) {
        cCollectibleRendererSet = new HashSet<CollectibleRenderer>();
        cStyle = new Style();
        cStyle.addDefaultsAquarium();
        cStage = new Stage();


        // temp
        //fishTank.add(new FishA(1f, "SampleOwnerId")); // todo replace by collectibles from server
//        for (int i = 0; i < 100; i++) {
//            RewardGenerator gen = new RewardGenerator(Client.getLocalInstance().getUserID() + ":me" + i);
//
//            fishTank.add(gen.generateCollectible(1));
//        }
        // end temp

        initCollectibleRendererSet(collection);
        initStage();
        initLabels();
    }

    protected void initLabels() {
        cLabelTable = new Table();
        cLabelTable.setFillParent(true);

        Label.LabelStyle style = cStyle.getDefaultLabelStyle();
        style.font.setColor(new Color(1f, 0, 0, 1f));

        cOwnerLabel = new Label("OWNER", style);
        cDateLabel = new Label("DATE", style);

        cOwnerLabel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Label got pushed");
            }
        });

        cLabelTable.align(Align.top);
        cLabelTable.add(cOwnerLabel).expandX().width(Gdx.graphics.getWidth() / 4).height(Gdx.graphics.getHeight() / 5);
        cLabelTable.add(cDateLabel).expandX().width(Gdx.graphics.getWidth() / 4).height(Gdx.graphics.getHeight() / 5).expandX();
//        cLabelTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cLabelTable.setFillParent(true);

        cLabelTable.debugAll();

        cStage.addActor(cLabelTable);
    }


    /**
     * For each collectible in the collection create a render object which handles the movement and view.
     * @param collection
     */
    public void initCollectibleRendererSet(Collection collection) {
        for (Collectible c : collection) {
            CollectibleRenderer collectibleRenderer = new CollectibleRenderer(c);
            collectibleRenderer.once();
            cCollectibleRendererSet.add(collectibleRenderer);
        }
    }

    /**
     * For a single collectible create a render object which handles the movement and view.
     * @param collectible
     */
    public void addCollectibleRendererItem(Collectible collectible) {
            CollectibleRenderer collectibleRenderer = new CollectibleRenderer(collectible);
            cCollectibleRendererSet.add(collectibleRenderer);
    }

    /**
     * To be executed only after initialization of the collectible renderer entities.
     */
    public void initStage() {
        for (CollectibleRenderer cr : cCollectibleRendererSet) {
            cStage.addActor(cr.getActor());
        }
        Gdx.input.setInputProcessor(cStage);
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        //System.out.println("Fps -> " + (1/delta));
        for (CollectibleRenderer c : cCollectibleRendererSet) {
            c.render();
        }

        cStage.act();
        Gdx.gl.glClearColor(63/255, 67/255, 173f/255, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cStage.setDebugAll(true);
        cStage.draw();

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
        cStage.dispose();
    }

}
