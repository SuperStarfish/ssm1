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

    protected ScreenStore cScreenStore;

    /**
     * A default constructor which initializes the screen logic.
     */
    public ScreenLogic() {
        cScreenStore = ScreenStore.getInstance();
        cGameSkin = cScreenStore.getGameSkin();
        cWidgetGroup = createWidgetGroup();
    }

    protected abstract WidgetGroup createWidgetGroup();

    public WidgetGroup getWidgetGroup() {
        return cWidgetGroup;
    }


}
