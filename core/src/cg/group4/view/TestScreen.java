package cg.group4.view;

import cg.group4.util.camera.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    protected Table table;

    /**
     * Initializes the test screen.
     * @param worldRenderer the camera viewport
     */
    public TestScreen(final WorldRenderer worldRenderer) {
        super(worldRenderer);

        initTable();

        initButton1();
        initButton2();
        initBackButton(worldRenderer);

        table.debugAll();
        setAsActiveScreen();
    }

    /**
     * Initializes the table container.
     */
    public final void initTable() {
        table = new Table();
        table.setFillParent(true);
        table.row().expandY();
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
        table.add(button1);
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
        table.add(button2);
    }

    /**
     * Initializes the back button.
     * @param worldRenderer the camera viewport
     */
    public final void initBackButton(final WorldRenderer worldRenderer) {
        TextButton button3 = cGameSkin.generateDefaultMenuButton("Go Back");
        button3.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                worldRenderer.setScreen(new HomeScreen(worldRenderer));
            }
        });
        table.add(button3);
    }

	@Override
	public void setAsActiveScreen() {
		// TODO Auto-generated method stub
		cWorldRenderer.setActor(table);
	}

}
