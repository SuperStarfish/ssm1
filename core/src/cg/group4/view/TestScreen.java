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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestScreen implements Screen, InputProcessor{
    SpriteBatch batch;
    Sprite sprite;

    OrthographicCamera camera;
    Viewport viewport;

    int width, height;

    final float GAME_WORLD_WIDTH = 160f;
    final float GAME_WORLD_HEIGHT = 90f;

    float aspectRatio;

    TextButton button;
    Stage stage;
    BitmapFont font;
    Label title;


    @Override
    public void show() {
        stage = new Stage();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        aspectRatio = (float)width / height;

        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("testbackground.jpg")));
        sprite.setPosition(0, 0);
        sprite.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);

        camera = new OrthographicCamera(GAME_WORLD_HEIGHT * aspectRatio, GAME_WORLD_HEIGHT);
        camera.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);
        //viewport = new ExtendViewport(120f, 90f, camera);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/blow.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        Color color = new Color(Color.valueOf("ffff00"));
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.size = 58;
        parameter.color = color;

        TextButtonStyle style = new TextButtonStyle();
        font = generator.generateFont(parameter);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, color);
        title = new Label("Super Startfish Mania", labelStyle);
        title.setPosition(width / 2 - title.getWidth() / 2f,
                height - title.getHeight());

        style.font = font;
        button = new TextButton("Settings", style);
        button.debug();

        button.setSize(width / 5f, height / 10f);
        button.setPosition(width / 2 - button.getWidth() / 2f, height / 2 - button.getHeight() / 2f);

        button.setPosition(width / 2f - button.getWidth() / 2f, button.getHeight() * 2f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //((Game)Gdx.app.getApplicationListener()).setScreen(new Settings());
                System.out.println("Settings");
            }
        });

        stage.addActor(button);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        font.draw(batch, "Super Starfish Mania", 200, 200);
        title.draw(batch, 1);
        sprite.draw(batch);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
