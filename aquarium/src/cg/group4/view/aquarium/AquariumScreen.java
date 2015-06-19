package cg.group4.view.aquarium;

import cg.group4.data_structures.Pair;
import cg.group4.data_structures.PlayerData;
import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.view.screen_mechanics.GameSkin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import org.lwjgl.util.vector.Vector3f;

import java.util.*;


/**
 * Screen which displays the collected fish in an aquarium.
 */
public class AquariumScreen implements Screen {

    /**
     * Stage for the content.
     */
    protected final Stage cStage = new Stage();
    /**
     * Style for layout items.
     */
    protected final GameSkin cStyle = new GameSkin();
    /**
     * Set containing all displayed elements.
     */
    protected HashSet<CollectibleRenderer> cDisplayRendererSet = new HashSet<CollectibleRenderer>();
    /**
     * Label to display owner information after having clicked on a collectible.
     */
    protected Label cOwnerLabel;

    /**
     * Label to display catch date information after having clicked on a collectible.
     */
    protected Label cDateLabel;

    /**
     * Label to display catch date information after having clicked on a collectible.
     */
    protected Label cStatusLabel;

    /**
     * Converts the users ids to usernames.
     */
    protected HashMap<String, String> cIdToUserName = new HashMap<String, String>();
    /**
     * Table to hold layout items.
     */
    protected Table cLabelTable = new Table();

    /**
     * Observer for the info label.
     */
    protected Observer cLabelObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            Pair<String> pair = (Pair<String>) arg;

            cOwnerLabel.setText("Owner: " + convertToUsername(pair.getElement1()));
            cDateLabel.setText("Date of achievement: " + pair.getElement2());
        }
    };

    /**
     * Initializes the aquarium.
     */
    public AquariumScreen() {
        Gdx.input.setInputProcessor(cStage);
        final int fontSize = 720;
        cStyle.createUIElements(fontSize);

        cOwnerLabel = cStyle.generateDefaultLabel("");
        cDateLabel = cStyle.generateDefaultLabel("");
        cStatusLabel = cStyle.generateDefaultLabel("");

        initTooltipLabels();
    }


    /**
     * Initializes tooltip labels.
     */
    protected void initTooltipLabels() {
        cLabelTable.setFillParent(true);

        cLabelTable.align(Align.topLeft);
        cLabelTable.add(cOwnerLabel).expandX();
        cLabelTable.row();
        cLabelTable.add(cDateLabel).expandX();
        cLabelTable.row();
        cLabelTable.add(cStatusLabel).expandX();
        cStage.addActor(cLabelTable);
    }

    /**
     * Converts the id to s username.
     *
     * @param id The id.
     * @return The username.
     */
    public String convertToUsername(String id) {
        if (cIdToUserName.containsKey(id)) {
            return cIdToUserName.get(id);
        }
        return id;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        for (CollectibleRenderer c : cDisplayRendererSet) {
            c.render();
        }
        cStage.act();
        final Vector3f background = backgroundColour();

        Gdx.gl.glClearColor(background.getX(), background.getY(), background.getZ(), 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cStage.draw();
    }

    /**
     * Returns the background colour.
     *
     * @return Blue background colour.
     */
    private Vector3f backgroundColour() {
        final float maxColour = 255f;
        final float x = 149f / maxColour;
        final float y = 221f / maxColour;
        final float z = 226f / maxColour;
        return new Vector3f(x, y, z);
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

    /**
     * Observer for the collection.
     * Used for updating the displayed collection of fish.
     *
     * @return Observer
     */
    public Observer getMembersObserver() {
        return new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                for (PlayerData playerData : (Collection<PlayerData>) arg) {
                    cIdToUserName.put(playerData.getId(), playerData.toString());
                }
            }
        };
    }

    /**
     * Observer for the collection.
     * Used for updating the displayed collection of fish.
     *
     * @return Observer
     */
    public Observer getCollectionObserver() {
        return new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                HashSet<CollectibleRenderer> temporarySet = convertToCollectibleRenderers(
                        (cg.group4.data_structures.collection.Collection) arg);

                Gdx.app.log(this.getClass().getName(), "Received Collection of size: " + temporarySet.size());

                removeOldFish(temporarySet);
                addNewFish(temporarySet);
            }
        };
    }

    /**
     * Converts each collectible from a collection to a CollectibleRenderer.
     *
     * @param collection The collectibles.
     * @return The CollectibleRenderers.
     */
    protected HashSet<CollectibleRenderer> convertToCollectibleRenderers(
            cg.group4.data_structures.collection.Collection collection) {
        HashSet<CollectibleRenderer> temporarySet = new HashSet<CollectibleRenderer>();

        for (Collectible collectible : collection) {
            temporarySet.add(new CollectibleRenderer(collectible));
        }
        return temporarySet;
    }

    /**
     * Removes the fish that are not in the new collection.
     *
     * @param newSet The new fish.
     */
    protected void removeOldFish(HashSet<CollectibleRenderer> newSet) {
        HashSet<CollectibleRenderer> oldSet = (HashSet<CollectibleRenderer>) cDisplayRendererSet.clone();
        oldSet.removeAll(newSet);
        cDisplayRendererSet.removeAll(oldSet);

        for (CollectibleRenderer collectibleRenderer : oldSet) {
            collectibleRenderer.getSubject().deleteObserver(cLabelObserver);
            collectibleRenderer.getActor().remove();
        }
    }

    /**
     * Adds the fish from the new collection that were not in the old collection.
     *
     * @param newSet The new fish.
     */
    protected void addNewFish(HashSet<CollectibleRenderer> newSet) {
        newSet.removeAll(cDisplayRendererSet);

        for (CollectibleRenderer collectibleRenderer : newSet) {
            collectibleRenderer.getSubject().addObserver(cLabelObserver);
            cDisplayRendererSet.add(collectibleRenderer);
            cStage.addActor(collectibleRenderer.getActor());
        }
    }
}
