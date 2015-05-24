package cg.group4.view.screen_mechanics;

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

/**
 * Abstract class which defines the base screen logic.
 * Should be extended by all other screens in the application.
 *
 * @author Jurgen van Schagen
 */
public abstract class ScreenLogic {
    /**
     * A default game skin.
     */
    protected GameSkin cGameSkin;

    /**
     * This group will be the active Actor in the WorldRenderer when this screen becomes
     * active.
     */
    protected WidgetGroup cWidgetGroup;

    /**
     * Reference to the ScreenStore. Used to switch to other screens.
     */
    protected ScreenStore cScreenStore;

    /**
     * The name of the previous screen. Used to go back to that screen through the ScreenStore.
     */
    protected final String cPreviousScreenName;

    /**
     * A default constructor which initializes the screen logic.
     */
    public ScreenLogic() {
        cScreenStore = ScreenStore.getInstance();
        cGameSkin = cScreenStore.getGameSkin();
        buildScreen();
        cPreviousScreenName = setPreviousScreenName();
    }

    public void buildScreen(){
        cWidgetGroup = createWidgetGroup();
    }

    protected abstract WidgetGroup createWidgetGroup();

    protected abstract String setPreviousScreenName();

    public String getPreviousScreenName(){
        return cPreviousScreenName;
    }

    public WidgetGroup getWidgetGroup() {
        return cWidgetGroup;
    }


}
