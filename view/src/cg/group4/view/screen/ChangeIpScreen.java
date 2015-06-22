package cg.group4.view.screen;

import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 *
 */
public class ChangeIpScreen extends ScreenLogic {

    /**
     * Table used for layout purposes.
     */
    protected Table cTable;

    /**
     * Buttons seen on the screen.
     */
    protected TextButton cSave, cBack;

    /**
     * TextField where the user can change his username.
     */
    protected TextField cIp, cPort;

    /**
     * Regular expression used to validate the ip address.
     */
    protected String validateIpRegex;

    /**
     * Label containing messages if the update was successful or not.
     */
    protected Label cMessage;




    @Override
    protected String setPreviousScreenName() {
        return "Network";
    }

    @Override
    protected void rebuildWidgetGroup() {

    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        cTable = new Table();
        cTable.setFillParent(true);




        return cTable;
    }
}
