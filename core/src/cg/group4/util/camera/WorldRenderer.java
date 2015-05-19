package cg.group4.util.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WorldRenderer implements Screen {
    SpriteBatch cBatch;
    Sprite cBackgroundSprite;
    Container cContainer;
    Stage cStage;

    OrthographicCamera cCamera, cPlayCamera;
    Viewport cViewPort, cPlayView;

    final float GAME_WORLD_WIDTH = 12f;
    final float GAME_WORLD_HEIGHT = 9f;
    final float DEV_WIDTH = 1280;
    float UIScalar;

    public WorldRenderer(){
        cBatch = new SpriteBatch();
        cCamera = new OrthographicCamera();
        cViewPort = new ExtendViewport(12f, 9f, 16f, 9f, cCamera);
        cBackgroundSprite = new Sprite(new Texture(Gdx.files.internal("default_background.jpg")));
        cBackgroundSprite.setSize(16f, 9f);
        cBackgroundSprite.setOriginCenter();
        cBackgroundSprite.setPosition(cBackgroundSprite.getWidth() / -2f, cBackgroundSprite.getHeight() / -2f);

        cPlayCamera = new OrthographicCamera();
        cPlayView = new FitViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, cPlayCamera);
        cStage = new Stage();
        Gdx.input.setInputProcessor(cStage);
        cContainer = new Container();
        cContainer.setFillParent(true);
        cContainer.debugAll();
        cContainer.background(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("images/debugpixel.png")))));
        cStage.addActor(cContainer);
    }

    public void setActor(Actor actor){
        cContainer.clearChildren();
        cContainer.setActor(actor);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cViewPort.apply();
        cCamera.update();
        cBatch.setProjectionMatrix(cCamera.combined);
        cBatch.begin();
        cBackgroundSprite.draw(cBatch);
        cBatch.end();

//        cPlayView.apply();
//        cPlayCamera.update();
        cStage.act();
        cStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        cViewPort.update(width, height);
        cPlayView.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        cBackgroundSprite.getTexture().dispose();
    }
}
