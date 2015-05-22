package cg.group4.view.screen;

import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Test screen for testing purposes.
 *
 * @author Jurgen van Schagen
 * @author Martijn Gribnau
 */
public class TestScreen extends ScreenLogic {

    /**
     * Container group used for the layout of the view.
     */
    protected Table cTable;

    @Override
    protected WidgetGroup createWidgetGroup() {
        initTable();

        initButton1();
        initButton2();
        initBackButton();

        return cTable;
    }

    /**
     * Initializes the table container.
     */
    public final void initTable() {
        cTable = new Table();
        cTable.setFillParent(true);
        cTable.row().expandY();
    }

    /**
     * Initializes test button 1.
     */
    public final void initButton1() {
        TextButton button1 = cGameSkin.generateDefaultMenuButton("Button1");
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                Gdx.app.debug("TAG", "TEST button 1");
            }
        });
        cTable.add(button1);
    }

    /**
     * Initializes test button 2.
     */
    public final void initButton2() {
        TextButton button2 = cGameSkin.generateDefaultMenuButton("Button2");
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                Gdx.app.debug("TAG", "TEST button 2");
            }
        });
        cTable.add(button2);
    }

    /**
     * Initializes the back button.
     */
    public final void initBackButton() {
        TextButton button3 = cGameSkin.generateDefaultMenuButton("Go Back");
        button3.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                cScreenStore.setScreen("Home");
            }
        });
        cTable.add(button3);
    }

}
