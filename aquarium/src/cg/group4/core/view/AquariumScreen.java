package cg.group4.core.view;

import cg.group4.data_structures.Pair;
import cg.group4.data_structures.collection.Collection;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

/**
 * Screen which displays the collected fish in an aquarium.
 */
public class AquariumScreen implements Screen {

    /**
     * Stage for the content.
     */
    protected final Stage cStage;
    /**
     * Style for layout items.
     */
    protected final GameSkin cStyle;
    /**
     * Set containing all displayed elements.
     */
    protected HashSet<CollectibleRenderer> cDisplayRendererSet, cInitialSet;
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
    protected HashMap<String, String> cIdToUserName;
    /**
     * Table to hold layout items.
     */
    protected Table cLabelTable;

    /**
     * Observer for the info label.
     */
    protected Observer cLabelObserver;

    /**
     * Initializes the aquarium.
     */
    public AquariumScreen() {


        createLabelObserver();
        cIdToUserName = new HashMap<String, String>();
        cInitialSet = new HashSet<CollectibleRenderer>();
        cDisplayRendererSet = new HashSet<CollectibleRenderer>();

        cStyle = new GameSkin();
        final int fontSize = 720;
        cStyle.createUIElements(fontSize);
        cStage = new Stage();

        initStage();
        initTooltipLabels();
    }


    /**
     * Creates the observer for the label.
     */
    public void createLabelObserver() {
        cLabelObserver = new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                Pair<String> pair = (Pair<String>) arg;

                cOwnerLabel.setText("Owner: " + convertToUsername(pair.getElement1()));
                cDateLabel.setText("Date of achievement: " + pair.getElement2());
            }
        };
    }

    /**
     * To be executed only after initialization of the collectible renderer entities.
     */
    public void initStage() {
        for (CollectibleRenderer cr : cDisplayRendererSet) {
            cStage.addActor(cr.getActor());
        }
        Gdx.input.setInputProcessor(cStage);
    }

    /**
     * Initializes tooltip labels.
     */
    protected void initTooltipLabels() {
        cLabelTable = new Table();
        cLabelTable.setFillParent(true);

        cOwnerLabel = cStyle.generateDefaultLabel("");
        cDateLabel = cStyle.generateDefaultLabel("");

        cLabelTable.align(Align.topLeft);
        cLabelTable.add(cOwnerLabel).expandX();
        cLabelTable.row();
        cLabelTable.add(cDateLabel).expandX();
        cLabelTable.row();
        initStatusLabel();

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

    /**
     * Initializes the status label.
     */
    protected void initStatusLabel() {
        cStatusLabel = cStyle.generateDefaultLabel("");
        cLabelTable.add(cStatusLabel).expandX();
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

        final float alpha = 1f;
        final Vector3f background = backgroundColour();

        Gdx.gl.glClearColor(background.getX(), background.getY(), background.getZ(), alpha);
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
    public Observer getCollectionObserver() {
        return new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                Collection collection = (Collection) arg;
                Gdx.app.log(this.getClass().getName(), "Received Collection update: " + collection.toString());


                if (collection.isEmpty()) {
                    notifyUserEmptyContainerReceived();
                } else {

                    for (Collectible c : collection) {
                        addCollectibleRendererItem(c);
                    }

                    for (CollectibleRenderer cr : cInitialSet) {
                        if (!isInDisplaySet(cr)) {
                            cDisplayRendererSet.add(cr);

                            cStage.addActor(cr.getActor());
                        }
                    }
                }
            }
        };
    }

    /**
     * Notifies the user an empty collection has been received from the server.
     * Does this by setting the status label.
     */
    public void notifyUserEmptyContainerReceived() {
        cStatusLabel.setText("Received an empty collection from the server!");
    }

    /**
     * For a single collectible renderer, add an object to the set which will be used to render.
     *
     * @param collectible Collectible to render
     */
    public void addCollectibleRendererItem(Collectible collectible) {
        CollectibleRenderer collectibleRenderer = new CollectibleRenderer(collectible);
        collectibleRenderer.getSubject().addObserver(cLabelObserver);
        cInitialSet.add(collectibleRenderer);
    }

    /**
     * Checks whether the display set already contains the given collectible.
     *
     * @param collectibleRenderer check whether this element exists in the already displayed set.
     * @return true if in the set, false if not.
     */
    public boolean isInDisplaySet(final CollectibleRenderer collectibleRenderer) {
        return cDisplayRendererSet.contains(collectibleRenderer);
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
                cIdToUserName = (HashMap<String, String>) arg;
            }
        };
    }
}
