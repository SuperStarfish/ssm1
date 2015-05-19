package cg.group4.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestScreen implements Screen, InputProcessor{
    SpriteBatch batch;
    Stage stage;

    Sprite background;

    OrthographicCamera camera;
    Viewport viewport;

    int width, height;

    final float GAME_WORLD_WIDTH = 16f;
    final float GAME_WORLD_HEIGHT = 9f;

    Table table, wrapper;


    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();

        background = new Sprite(new Texture(Gdx.files.internal("images/16cut.jpg")));
        background.setSize(16f, 9f);
        background.setOrigin(background.getWidth() / 2f, background.getHeight() / 2f);

        background.setPosition(background.getWidth() / -2f, background.getHeight() / -2f);

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        viewport = new ExtendViewport(14f, 9f, 16f, 12f, camera);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.BLACK;
        Label label = new Label("Charsequence", style);
        Label label2 = new Label("Charsequence2", style);

        wrapper = new Table();
        wrapper.debugAll();
        wrapper.setFillParent(true);

        table = new Table();
        table.setBackground(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")))));
        table.setSize(viewport.getScreenHeight() * (4 / 3f), viewport.getScreenHeight());
        table.debugAll();
        table.add(label);
        table.add(label2);

        wrapper.add(table);
        System.out.println(wrapper.getCell(table).getExpandX());
        stage.addActor(wrapper);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        background.draw(batch);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        background.setScale(viewport.getWorldHeight() / background.getHeight());
        //table.setSize(viewport.getScreenHeight() * (4 / 3f), viewport.getScreenHeight());
        wrapper.getCell(table).expand((int)(viewport.getScreenHeight() * (4 / 3f)), viewport.getScreenHeight());

//        table.setScale(wrapper.getHeight() / table.getHeight());
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

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT){
            camera.translate(-1f, 0f);
        }
        if(keycode == Input.Keys.RIGHT){
            camera.translate(1f, 0f);
        }
        if(keycode == Input.Keys.UP){
            camera.translate(0f, 1f);
        }
        if(keycode == Input.Keys.DOWN){
            camera.translate(0f, -1f);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
