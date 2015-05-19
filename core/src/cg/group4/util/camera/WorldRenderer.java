package cg.group4.util.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WorldRenderer implements Screen {
    SpriteBatch cBatch;
    Sprite cBackground;
    Stage cStage;

    OrthographicCamera cCamera;
    Viewport cViewPort;

    final float GAME_WORLD_WIDTH = 12f;
    final float GAME_WORLD_HEIGHT = 9f;
    final float DEV_WIDTH = 1280;
    float UIScalar;

    public WorldRenderer(){
        cBatch = new SpriteBatch();
        cBackground = new Sprite(new Texture(Gdx.files.internal("default_background.jpg")));
        float aspectRatio = cBackground.getWidth() / cBackground.getHeight();
        cBackground.setSize(GAME_WORLD_HEIGHT * aspectRatio, GAME_WORLD_HEIGHT);
        cBackground.setOriginCenter();
        cBackground.setPosition(cBackground.getWidth() / -2f, cBackground.getHeight() / -2f);
        cCamera = new OrthographicCamera(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        cViewPort = new ExtendViewport(12f, 9f, 16f, 9f, cCamera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        renderDefaults();
        cBatch.begin();
        cBackground.draw(cBatch);
        cBatch.end();
    }

    protected void renderDefaults(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cCamera.update();
        cBatch.setProjectionMatrix(cCamera.combined);
    }

    @Override
    public void resize(int width, int height) {
        UIScalar = width / DEV_WIDTH;
        cViewPort.update(width, height);
        cBackground.setScale(cViewPort.getWorldHeight() / cBackground.getHeight());
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
        cBackground.getTexture().dispose();
    }
}
