package cg.group4.view;

import cg.group4.util.camera.WorldRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public abstract class ScreenLogic implements Disposable{
    protected WorldRenderer cParent;
    protected SpriteBatch cBatch;

    public ScreenLogic(WorldRenderer worldRenderer){
        cParent = worldRenderer;
        cBatch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        cBatch.dispose();
    }
}
