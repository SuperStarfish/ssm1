package cg.group4.view.screen_mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Class that handles the viewport and swapping of Actors and Sprites.
 *
 * @author Jurgen van Schagen
 */
public class WorldRenderer implements Screen {

    /**
     * The world is seen in a 4:3 ratio. This sets the width to also fit with 16:9 ratio.
     */
    static final float GAME_WORLD_WIDTH = 12f;
    /**
     * Specifies the max viewport width allowed.
     */
    static final float MAX_VIEWPORT_WIDTH = 16f;
    /**
     * The world is seen in a 4:3 ratio. This sets the height to also fit with 16:9 ratio.
     */
    static final float GAME_WORLD_HEIGHT = 9f;
    /**
     * User to reposition something to center.
     */
    static final float CENTERER = -2f;
    /**
     * Spritebatch for the background of the screen.
     */
    SpriteBatch cBatch;
    /**
     * The background sprite that will be rendered
     */
    Sprite cBackgroundSprite;
    /**
     * The stage in which actors are placed. Stage does not change.
     */
    Stage cStage;
    /**
     * A simple music player. To be refactored somewhere else later.
     */
    Music music;
    /**
     * Different screens can be 'built' by assigning the Screen in the WorldRenderer.
     */
    ScreenLogic cScreen;
    /**
     * The camera that captures what can be seen.
     */
    OrthographicCamera cCamera;
    /**
     * The visible viewport of the game. Aspect ratios between 16:9 and 4:3 are supported.
     */
    Viewport cViewPort;

    /**
     * Creates everything that is needed to properly display the game and sets the screen to the HomeScreen.
     */
    public WorldRenderer() {
        initCameraAndViewport();
        createMusicPlayer();
    }

    /**
     * Initializes everything for the camera and viewport. Also creates a SpriteBatch and Stage.
     */
    protected final void initCameraAndViewport() {
        cBatch = new SpriteBatch();
        cStage = new Stage();
        Gdx.input.setInputProcessor(cStage);

        cCamera = new OrthographicCamera();
        cViewPort = new ExtendViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, MAX_VIEWPORT_WIDTH, GAME_WORLD_HEIGHT, cCamera);
        setBackground("default_background.jpg");
    }

    /**
     * Creates a simple music player. To be refactored later.
     */
    protected final void createMusicPlayer() {
        music = Gdx.audio.newMusic(Gdx.files.internal("music/Summer Day.mp3"));
        music.setLooping(true);
        music.play();
    }

    /**
     * Disposes the previous screen and sets the new given screen.
     *
     * @param screen Screen to set the view to
     */
    public final void setScreen(final ScreenLogic screen) {
        cScreen = screen;
        cStage.clear();
        cStage.addActor(cScreen.getWidgetGroup());
    }

    /**
     * Calls setBackground with a FileHandle after finding the specified file.
     *
     * @param fileName Path to the file relative to assets folder.
     */
    public final void setBackground(final String fileName) {
        setBackground(Gdx.files.internal(fileName));
    }

    /**
     * Sets the background to fit properly in the viewport.
     *
     * @param file The background FileHandle
     */
    public final void setBackground(final FileHandle file) {
        if (cBackgroundSprite != null) {
            cBackgroundSprite.getTexture().dispose();
        }
        cBackgroundSprite = new Sprite(new Texture(file));
        cBackgroundSprite.setSize(
                (cBackgroundSprite.getWidth() / cBackgroundSprite.getHeight()) * GAME_WORLD_HEIGHT,
                GAME_WORLD_HEIGHT);
        cBackgroundSprite.setPosition(cBackgroundSprite.getWidth() / CENTERER, cBackgroundSprite.getHeight() / CENTERER);
    }


    @Override
    public final void show() {

    }

    @Override
    public final void render(float delta) {
        cViewPort.apply();
        renderDefaults();
        cBatch.begin();
        cBackgroundSprite.draw(cBatch);
        cBatch.end();
        cStage.act();
        cStage.draw();
    }

    /**
     * Does the very basic task of 'cleaning' the screen so a new render can happen.
     */
    protected final void renderDefaults() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cCamera.update();
        cBatch.setProjectionMatrix(cCamera.combined);
    }

    @Override
    public final void resize(final int width, final int height) {
        cViewPort.update(width, height);
    }

    @Override
    public final void pause() {

    }

    @Override
    public final void resume() {

    }

    @Override
    public final void hide() {

    }

    @Override
    public final void dispose() {
        cBackgroundSprite.getTexture().dispose();
    }
}
