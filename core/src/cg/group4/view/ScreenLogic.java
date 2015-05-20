package cg.group4.view;

import cg.group4.util.camera.WorldRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ScreenLogic {
    protected WorldRenderer cParent;
    protected SpriteBatch cBatch;
    protected Actor cActor;

    public ScreenLogic(WorldRenderer worldRenderer){
        cParent = worldRenderer;
        cBatch = new SpriteBatch();
    }

}
