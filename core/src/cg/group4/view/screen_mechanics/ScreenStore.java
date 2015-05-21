package cg.group4.view.screen_mechanics;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ben on 21-5-2015.
 */
public class ScreenStore {

    /**
     * Hashmap that contains all the screens.
     */
    protected Map<String,ScreenLogic> cScreens;

    /**
     * World renderer to display the screens.
     */
    protected WorldRenderer cWorldRenderer;

    /**
     * Class to store screens to be accessed later.
     */
    public ScreenStore() {
        cScreens = new HashMap<String, ScreenLogic>();
        cWorldRenderer = new WorldRenderer();
    }

    /**
     * Stores the screen in the store under the given tag and displays it.
     * @param tag Tag of the screen.
     * @param screen Screen to be displayed.
     */
    public void setScreen(String tag, ScreenLogic screen){
        cScreens.put(tag,screen);
        cWorldRenderer.setScreen(screen);
    }

    /**
     * Displays the screen under the given tag.
     * @param tag Tag of the screen to be displayed.
     */
    public void setScreen(String tag){
        cWorldRenderer.setScreen(cScreens.get(tag));
    }

    /**
     * Returns the screen of the given tag from the store.
     * @param tag Tag of the screen to be returned.
     * @return Returns the screen belonging to the given tag.
     */
    public ScreenLogic getScreen(String tag){
        return  cScreens.get(tag);
    }
}
