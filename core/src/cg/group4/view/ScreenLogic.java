package cg.group4.view;

import cg.group4.game_logic.StandUp;
import cg.group4.util.camera.GameSkin;
import cg.group4.util.camera.WorldRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class ScreenLogic implements Disposable{
    protected WorldRenderer cWorldRenderer;
    protected SpriteBatch cBatch;
    protected GameSkin cGameSkin;

    public ScreenLogic(WorldRenderer worldRenderer){
        cWorldRenderer = worldRenderer;
        cBatch = new SpriteBatch();
        cGameSkin = StandUp.getInstance().getGameSkin();
    }

    @Override
    public void dispose() {
        cBatch.dispose();
    }
}
