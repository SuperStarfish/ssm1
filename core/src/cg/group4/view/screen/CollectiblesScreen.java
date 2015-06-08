package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.data_structures.Selection;
import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.data_structures.collection.collectibles.collectible_comparators.RarityComparator;
import cg.group4.data_structures.groups.GroupData;
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
import java.util.Observable;
import java.util.Observer;

/**
 * Screen to be displayed when pressing the "Collection" button on the home screen.
 */
public final class CollectiblesScreen extends ScreenLogic {

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
     * Object that creates images for the collectibles.
     */
    protected CollectibleDrawer cDrawer;

    /**
     * SelectBox that contains the groups that the user is currently in.
     */
    protected SelectBox<Selection> cGroupsBox;

    /**
     * SelectBox that contains all the possible sorting options.
     */
    protected SelectBox<String> cSortBox;

    /**
     * Width and height of the screen.
     */
    protected int cScreenWidth, cScreenHeight;
    
    /**
     * Currently displayed collection.
     */
    protected Collection cSelectedCollection;
    
    protected ArrayList<GroupData> cGroups = new ArrayList<GroupData>();

    /**
     * Observers additions made to the collection.
     */
    protected Observer cAddObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            constructContents();
        }
    };

    /**
     * Observers removals made to the collection.
     */
    protected Observer cRemoveObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            constructContents();
        }
    };

    /**
     * Creates a new CollectibleScreen.
     */
    public CollectiblesScreen() {
        cDrawer = new CollectibleDrawer();
        
        Client.getRemoteInstance().getGroupData(new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                cGroups = (ArrayList<GroupData>) response.getData();
            }
        });

        cSelectedCollection = StandUp.getInstance().getPlayer().getCollection();
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        cScreenWidth = Gdx.graphics.getWidth();
        cScreenHeight = Gdx.graphics.getHeight();

        fillDrawer();
        setBackButton();
        createSortBox();
        createGroupBox();
        fillContainer();
        return cContainer;
    }

    /**
     * Fills the drawer and table of the screen.
     */
    protected void fillDrawer() {
        cDrawer = new CollectibleDrawer();
        cContentTable = new Table();
        cContentTable.setWidth(cScreenWidth);
        cScrollPane = new ScrollPane(cContentTable);
        cScrollPane.setForceScroll(false, true);
    }

    /**
     * Sets up the backbutton of the screen.
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
        String[] def = new String[2];
        def[0] = "Sort_Rarity";
        def[1] = "<Insert Sort Object>";
        cSortBox.setItems(def);
        cSortBox.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                System.out.println("Selected Sorting: " + cSortBox.getSelected());
            }
        });
    }

    /**
     * Creates the dropdown box to select the collection to display.
     */
    protected void createGroupBox() {
        cGroupsBox = cGameSkin.generateDefaultSelectbox();
        Selection[] list = new Selection[cGroups.size() + 1];
        list[0] = new Selection(StandUp.getInstance().getPlayer().getPlayerData());
        for (int i = 1; i < list.length; i++) {
            list[i] = new Selection(cGroups.get(i - 1));
        }
        cGroupsBox.setItems(list);
        cGroupsBox.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                System.out.println("Selected Collection: " + cGroupsBox.getSelected());
                String groupId = cGroupsBox.getSelected().getValue();
                Client.getRemoteInstance().getCollection(groupId, new ResponseHandler() {
                    @Override
                    public void handleResponse(Response response) {
						cSelectedCollection = (Collection) response.getData();
						constructContents();
					}
                });
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

        constructContents();
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
        constructContents();
    }

    /**
     * Helper method that should not be called outside of this class.
     * Sorts and rebuilds all the collectibles in the collection. Called upon initialisation
     * of the screen, resize and collection changes.
     */
    protected void constructContents() {
        //Collection collection = StandUp.getInstance().getPlayer().getCollection();
        ArrayList<Collectible> sortedList = cSelectedCollection.sort(new RarityComparator());
        DecimalFormat format = new DecimalFormat("#.00");

        cContentTable.clear();
        for (Collectible c : sortedList) {
            cContentTable.row().height(cScreenHeight / cItemsOnScreen).width(cScreenWidth / 6);
            Image img = new Image(cDrawer.drawCollectible(c));
            cContentTable.add(img);
            cContentTable.add(cGameSkin.generateDefaultLabel(format.format(c.getRarity())));
            cContentTable.add(cGameSkin.generateDefaultLabel("DATE"));
            cContentTable.add(cGameSkin.generateDefaultLabel("OWNER"));
            cContentTable.add(cGameSkin.generateDefaultLabel("GROUP"));
            TextButton donate = cGameSkin.generateDefaultMenuButton("DONATE");
            donate.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					
				}
            });
            cContentTable.add(donate);
            
        }
    }
}
