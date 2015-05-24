package cg.group4.view.screen_mechanics;

import cg.group4.view.screen.HomeScreen;
import cg.group4.view.screen.SettingsScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of all the screens throughout the game.
 *
 * @author Benjamin Los
 */
public class ScreenStore {

    /**
     * Singleton of screen handler.
     */
    protected static final ScreenStore cInstance = new ScreenStore();

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
    private ScreenStore() {
        cScreens = new HashMap<String, ScreenLogic>();
        cGameSkin = new GameSkin();
        cWorldRenderer = new WorldRenderer();
    }

    /**
     * Getter for time keeper instance.
     *
     * @return cInstance
     */
    public static ScreenStore getInstance() {
        return cInstance;
    }

    public void init() {
        addScreen("Home", new HomeScreen());
        addScreen("Settings", new SettingsScreen());
    }

    /**
     * Stores the screen in the store under the given tag.
     *
     * @param tag    Tag of the screen.
     * @param screen Screen to be stored.
     */
    public void addScreen(String tag, ScreenLogic screen) {
        cScreens.put(tag, screen);
    }

    /**
     * Removes the screen from the store under the given tag.
     *
     * @param tag Tag of the screen to be removed.
     */
    public void removeScreen(String tag) {
        cScreens.remove(tag);
    }

    /**
     * Displays the screen under the given tag.
     *
     * @param tag Tag of the screen to be displayed.
     */
    public void setScreen(String tag) {
        cWorldRenderer.setScreen(cScreens.get(tag));
    }

    /**
     * Returns the screen of the given tag from the store.
     *
     * @param tag Tag of the screen to be returned.
     * @return Returns the screen belonging to the given tag.
     */
    public ScreenLogic getScreen(String tag) {
        return cScreens.get(tag);
    }

    /**
     * Getter for the world renderer.
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

    public void rebuild(int UISize){
        cGameSkin.createUIElements(UISize);
        for(ScreenLogic screen : cScreens.values()){
            screen.buildScreen();
        }
    }
}
