package cg.group4.view.aquarium;

import cg.group4.aquarium.Aquarium;
import cg.group4.data_structures.PlayerData;
import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.data_structures.groups.GroupData;
import cg.group4.view.screen_mechanics.GameSkin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
     * Table to hold layout items.
     */
    protected Table cMenuTable;
    /**
     * Table to hold fish.
     */
    protected Table cFishTable;

    protected TextButton cChangeIpButton;
    /**
     * Label to display owner information after having clicked on a collectible.
     */
    protected Label cOwnerLabel;
    /**
     * Label to display catch date information after having clicked on a collectible.
     */
    protected Label cDateLabel;
    /**
     * SelectBox that contains the groups.
     */
    protected SelectBox<GroupData> cGroupsBox;
    /**
     * The background colour.
     */
    protected Vector3f cBackgroundColour;
    /**
     * Set containing all displayed elements.
     */
    protected HashSet<CollectibleRenderer> cDisplayRendererSet = new HashSet<CollectibleRenderer>();
    /**
     * Converts the users ids to usernames.
     */
    protected HashMap<String, String> cIdToUserName = new HashMap<String, String>();
    /**
     * Observer for the info label.
     */
    protected Observer cLabelObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            Collectible collectible = (Collectible) arg;

            cOwnerLabel.setText("Owner: " + convertToUsername(collectible.getOwnerId()));
            cDateLabel.setText("Date: " + collectible.getDateAsString());
        }
    };

    /**
     * Initializes the aquarium.
     */
    public AquariumScreen() {
        initBackgroundColour();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(cStage);
        final int fontSize = 720;
        cStyle.createUIElements(fontSize);

        initFishTable();
        initMenuTable();
    }

    protected void initFishTable() {
        cFishTable = new Table();
        cFishTable.setFillParent(true);
        cFishTable.setZIndex(0);
        cFishTable.row().fill();

        cStage.addActor(cFishTable);
    }

    protected void initMenuTable() {
        cMenuTable = new Table();
        cMenuTable.setFillParent(true);
        cMenuTable.align(Align.top);
        cMenuTable.setZIndex(1);

        createGroupBox();
        cMenuTable.add(cGroupsBox).expandX();
        cMenuTable.add(initTooltipLabels()).expandX();
        createChangeIpButton();
        cMenuTable.add(cChangeIpButton).expandX();

        cStage.addActor(cMenuTable);
    }

    /**
     *
     */
    protected void createChangeIpButton() {
        cChangeIpButton = cStyle.generateDefaultMenuButton("Change server ip");
    }
    /**
     * Creates the group box.
     */
    protected void createGroupBox() {
        cGroupsBox = cStyle.generateDefaultSelectbox();
        cGroupsBox.setVisible(false);
        cGroupsBox.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                if(cGroupsBox.getItems().size == 0) {
                    cGroupsBox.setVisible(false);
                } else {
                    cGroupsBox.setVisible(true);
                    if (cGroupsBox.getSelected() != null) {
                        Aquarium.getInstance().setGroupId(cGroupsBox.getSelected().getGroupId());
                    }
                }
            }
        });
    }

    /**
     * Initializes tooltip labels.
     */
    protected Table initTooltipLabels() {
        Table table = new Table();

        cOwnerLabel = cStyle.generateDefaultLabel("Owner: ");
        table.add(cOwnerLabel);
        table.row();
        cDateLabel = cStyle.generateDefaultLabel("Date: ");
        table.add(cDateLabel);
        table.row();

        return table;
    }

    /**
     * Returns the background colour.
     *
     * @return Blue background colour.
     */
    private void initBackgroundColour() {
        final float maxColour = 255f;
        final float x = 149f / maxColour;
        final float y = 221f / maxColour;
        final float z = 226f / maxColour;
        cBackgroundColour =  new Vector3f(x, y, z);
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
    public void render(float delta) {
        for (CollectibleRenderer c : cDisplayRendererSet) {
            c.render();
        }
        cStage.act();
        final Vector3f background = cBackgroundColour;

        Gdx.gl.glClearColor(background.getX(), background.getY(), background.getZ(), 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
     * Observer for the groupdata.
     * Used for updating the groupbox.
     *
     * @return Observer
     */
    public Observer getGroupDataObserver() {
        return new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                ArrayList<GroupData> list = (ArrayList<GroupData>) arg;
                cGroupsBox.setItems(list.toArray(new GroupData[list.size()]));
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
            collectibleRenderer.getActor().setZIndex(1);
            cDisplayRendererSet.add(collectibleRenderer);
            cFishTable.addActor(collectibleRenderer.getActor());
        }
    }
}
