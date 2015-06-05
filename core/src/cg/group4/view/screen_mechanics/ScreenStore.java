package cg.group4.view.screen_mechanics;

import cg.group4.view.screen.CollectiblesScreen;
import cg.group4.view.screen.HomeScreen;
import cg.group4.view.screen.NetworkScreen;
import cg.group4.view.screen.SettingsScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of all the screens throughout the game.
 *
 * @author Benjamin Los
 */
public final class ScreenStore {

    /**
     * Singleton of screen handler.
     */
    protected static final ScreenStore INSTANCE = new ScreenStore();

    /**
     * Hashmap that contains all the screens.
     */
    protected Map<String, ScreenLogic> cScreens;

    /**
     * World renderer to display the screens.
     */
    protected WorldRenderer cWorldRenderer;

    /**
     * Contains the default skin that is used in the game.
     */
    protected GameSkin cGameSkin;


    /**
     * Class to store screens to be accessed later.
     */
    protected ScreenStore() {
        cScreens = new HashMap<String, ScreenLogic>();
        cGameSkin = new GameSkin();
        cWorldRenderer = new WorldRenderer();
    }

    /**
     * Getter for screen store instance.
     * @return The instance.
     */
    public static ScreenStore getInstance() {
        return INSTANCE;
    }

    /**
     * Initializes the Home Screen, Settings Screen and the Collection Screen,
     * since those are highly likely to be opened.
     */
    public void init() {
        addScreen("Home", new HomeScreen());
        addScreen("Collection", new CollectiblesScreen());
        addScreen("Settings", new SettingsScreen());
        addScreen("Network", new NetworkScreen());
    }

    /**
     * Stores the screen in the store under the given tag.
     *
     * @param tag    Tag of the screen.
     * @param screen Screen to be stored.
     */
    public void addScreen(final String tag, final ScreenLogic screen) {
        cScreens.put(tag, screen);
    }

    /**
     * Removes the screen from the store under the given tag.
     *
     * @param tag Tag of the screen to be removed.
     */
    public void removeScreen(final String tag) {
        cScreens.remove(tag);
    }

    /**
     * Displays the screen under the given tag.
     *
     * @param tag Tag of the screen to be displayed.
     */
    public void setScreen(final String tag) {
        cWorldRenderer.setScreen(cScreens.get(tag));
    }

    /**
     * Returns the screen of the given tag from the store.
     *
     * @param tag Tag of the screen to be returned.
     * @return Returns the screen belonging to the given tag.
     */
    public ScreenLogic getScreen(final String tag) {
        return cScreens.get(tag);
    }

    /**
     * Getter for the world renderer.
     *
     * @return The world renderer.
     */
    public WorldRenderer getWorldRenderer() {
        return cWorldRenderer;
    }

    /**
     * Getter for the default game skin.
     *
     * @return The game skin.
     */
    public GameSkin getGameSkin() {
        return cGameSkin;
    }

    /**
     * Upon resizing the game, this method is called. It will first update the GameSkin and then ask the
     * Screens it contains to update their UI Elements.
     *
     * @param uiSize The new width/height (depending on orientation) that is used by the GameSkin to rescale the UI
     *               elements.
     */
    public void rebuild(final int uiSize) {
        cGameSkin.createUIElements(uiSize);
        for (ScreenLogic screen : cScreens.values()) {
            screen.rebuildWidgetGroup();
        }
    }
}
