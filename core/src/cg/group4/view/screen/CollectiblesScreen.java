package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.data_structures.Selection;
import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.data_structures.collection.collectibles.collectible_comparators.HueComparator;
import cg.group4.data_structures.collection.collectibles.collectible_comparators.RarityComparator;
import cg.group4.data_structures.groups.GroupData;
import cg.group4.game_logic.Player;
import cg.group4.game_logic.StandUp;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import cg.group4.view.util.rewards.CollectibleDrawer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Screen to be displayed when pressing the "Collection" button on the home screen.
 */
public class CollectiblesScreen extends ScreenLogic {

    /**
     * Numbers of collectibles displayed without scrolling, and the amount of
     * checkboxes in the menu.
     */
    protected final int cItemsOnScreen = 10, cNumberOfTopBarItems = 3;
    /**
     * cContentTable contains the collectibles of the collection.
     * cContainer contains the scrollpane displaying the collectibles.
     */
    protected Table cContentTable, cContainer;
    /**
     * ScrollPane displaying the collectibles of the collection.
     */
    protected ScrollPane cScrollPane;
    /**
     * Button that takes the player back to the home screen.
     */
    protected TextButton cBackButton;
    /**
     * SelectBox that contains the groups that the user is currently in.
     */
    protected SelectBox<Selection> cGroupsBox;
    /**
     * SelectBox that contains all the possible sorting options.
     */
    protected SelectBox<Comparator<Collectible>> cSortBox;
    /**
     * Width and height of the screen.
     */
    protected int cScreenWidth, cScreenHeight;
    /**
     * Currently displayed collection.
     */
    protected Collection cSelectedCollection;
    /**
     * Currently used sorter.
     */
    protected Comparator<Collectible> cSorter;


    @Override
    protected WidgetGroup createWidgetGroup() {
        cScreenWidth = Gdx.graphics.getWidth();
        cScreenHeight = Gdx.graphics.getHeight();
        cGroupsBox = cGameSkin.generateDefaultSelectbox();

        fillDrawer();
        setBackButton();
        createSortBox();
        getGroups();

        fillContainer();

        return cContainer;
    }

    @Override
    protected void rebuildWidgetGroup() {
        cScreenWidth = Gdx.graphics.getWidth();
        cScreenHeight = Gdx.graphics.getHeight();
        cContentTable.setWidth(cScreenWidth);

        cBackButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        constructContents();
    }

    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }

    @Override
    public void display() {
        getGroups();
    }

    /**
     * Fills the drawer and table of the screen.
     */
    protected void fillDrawer() {
        cContentTable = new Table();
        cContentTable.setWidth(cScreenWidth);
        cScrollPane = new ScrollPane(cContentTable);
        cScrollPane.setForceScroll(false, true);
    }

    /**
     * Sets up the back button of the screen.
     */
    protected void setBackButton() {
        cBackButton = cGameSkin.generateDefaultMenuButton("Back");
        cBackButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                ScreenStore.getInstance().setScreen("Home");
            }
        });
    }

    /**
     * Creates the dropdown box to specify the sorting of the collection.
     */
    protected void createSortBox() {
        cSortBox = cGameSkin.generateDefaultSelectbox();

        cSorter = new RarityComparator();
        cSortBox.setItems(cSorter, new HueComparator());
        cSortBox.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                System.out.println("Selected Sorting: " + cSortBox.getSelected());
                cSorter = cSortBox.getSelected();
                constructContents();
            }
        });
    }

    /**
     * gets the groups to be displayed in the group box.
     */
    protected void getGroups() {
        if (Client.getRemoteInstance().isConnected()) {
            Client.getRemoteInstance().getGroupData(new ResponseHandler() {
                @Override
                public void handleResponse(Response response) {
                    fillGroupBox((ArrayList<GroupData>) response.getData());
                }
            });
        } else {
            fillGroupBox(new ArrayList<GroupData>());
        }
    }

    /**
     * Creates the dropdown box to select the collection to display.
     */
    protected void fillGroupBox(ArrayList<GroupData> groups) {

        Selection[] list = new Selection[groups.size() + 1];
        list[0] = new Selection(StandUp.getInstance().getPlayer().getPlayerData());

        for (int i = 1; i < list.length; i++) {
            list[i] = new Selection(groups.get(i - 1));
        }

        cGroupsBox.setItems(list);
        cGroupsBox.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                if (cGroupsBox.getSelected() != null) {
                    System.out.println("Selected Collection: " + cGroupsBox.getSelected());
                    updateCollection();
                }
            }
        });
    }

    /**
     * Fills the container (screen).
     */
    protected void fillContainer() {
        cContainer = new Table();
        cContainer.setFillParent(true);
        cContainer.row().height(cScreenHeight / cItemsOnScreen).width(cScreenWidth / cNumberOfTopBarItems).fill();
        cContainer.add(cBackButton).fill();
        cContainer.add(cSortBox).fill();
        cContainer.add(cGroupsBox).fill();
        cContainer.row();
        cContainer.add(cScrollPane).colspan(cNumberOfTopBarItems).fill();
    }

    /**
     * Updates teh collection to the latest version.
     */
    protected void updateCollection() {
        Client client = Client.getLocalInstance();
        if (cGroupsBox.getSelectedIndex() != 0) {
            client = Client.getRemoteInstance();
        }
        client.getCollection(cGroupsBox.getSelected().getValue(), new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                if (response.isSuccess()) {
                    cSelectedCollection = (Collection) response.getData();
                    constructContents();
                }
            }
        });
    }

    /**
     * Helper method that should not be called outside of this class.
     * Sorts and rebuilds all the collectibles in the collection. Called upon initialisation
     * of the screen, resize and collection changes.
     */
    protected void constructContents() {
        Gdx.app.log(getClass().getSimpleName(), "Construction");
        final Player player = StandUp.getInstance().getPlayer();
        DecimalFormat format = new DecimalFormat("0.##");

        boolean myCollection = cGroupsBox.getSelectedIndex() == 0
                && player.getGroupId() != null
                && Client.getRemoteInstance().isConnected();
        cContentTable.clear();
        for (final Collectible collectible : cSelectedCollection.sort(cSorter)) {
            cContentTable.row().height(cScreenHeight / cItemsOnScreen).width(cScreenWidth / 6);
            cContentTable.add(new Image(CollectibleDrawer.drawCollectible(collectible)));
            cContentTable.add(cGameSkin.generateDefaultLabel(format.format(collectible.getRarity())));
            cContentTable.add(cGameSkin.generateDefaultLabel(Integer.toString(collectible.getAmount())));
            if (myCollection) {
                TextButton donate = cGameSkin.generateDefaultMenuButton("Donate");
                donate.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Client.getRemoteInstance().addCollectible(collectible, player.getGroupId(), null);
                        Client.getLocalInstance().removeCollectible(collectible, player.getId(), null);

                        updateCollection();
                    }
                });
                cContentTable.add(donate);
            }
            
        }
    }
}
