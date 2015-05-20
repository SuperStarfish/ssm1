package cg.group4.view;

import cg.group4.game_logic.StandUp;
import cg.group4.util.camera.GameSkin;
import cg.group4.util.camera.WorldRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Abstract class which defines the base screen logic.
 * Should be extended by all other screens in the application.
 *
 * @author Jurgen van Schagen
 */
public abstract class ScreenLogic implements Disposable {

    /**
     * The camera viewport.
     */
    protected WorldRenderer cWorldRenderer;

    /**
     * A sprite batch used to display sprite images.
     */
    protected SpriteBatch cBatch;

    /**
     * A default game skin.
     */
    protected GameSkin cGameSkin;

    /**
     * A default constructor which initializes the screen logic.
     *
     * @param worldRenderer The camera viewport
     */
    public ScreenLogic(final WorldRenderer worldRenderer) {
        cWorldRenderer = worldRenderer;
        cBatch = new SpriteBatch();
        cGameSkin = StandUp.getInstance().getGameSkin();
    }

    /**
     * Disposes the sprite batch contained by the screen logic.
     */
    @Override
    public final void dispose() {
        cBatch.dispose();
    }
}
