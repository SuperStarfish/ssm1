package cg.group4.view.screen_mechanics;

import com.badlogic.gdx.*;
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
 * Is responsible for the display of the screens and properly settings the background image.
 * Draws the WidgetGroups correctly on all screen resolution supported withing the viewport ratios.
 */
public class WorldRenderer extends InputAdapter implements Screen {

    /**
     * The minimal Viewport aspect ratio.
     */
    protected final float cMinViewportRatio = 9f;
    /**
     * The maximum Viewport aspect ratio.
     */
    protected final float cMaxViewportRatio = 16f;
    /**
     * Used to position the background to the center of the Viewport.
     */
    protected final float cToCenter = -2f;
    /**
     * Path to the default landscape background image.
     */
    protected final String cDefaultLandscapePath = "images/default_landscape_background.jpg";
    /**
     * Path to the default portrait background image.
     */
    protected final String cDefaultPortraitPath = "images/default_portrait_background.jpg";
    /**
     * The viewport for the game. Makes sure that the game is rendered between 16:9 and 4:3 aspect ratio.
     */
    protected Viewport cViewport;
    /**
     * Camera used by the Viewport.
     */
    protected OrthographicCamera cCamera;
    /**
     * The SpriteBatch used to draw elements on the screen.
     */
    protected SpriteBatch cBatch;
    /**
     * The Stage that will contain the UI elements from the ScreenLogic.
     */
    protected Stage cStage;
    /**
     * Stores ScreenLogic so it can easily be accessed again.
     */
    protected ScreenStore cScreenStore;
    /**
     * InputMultiplexer stores multiple InputProcessors. This is used to have the Stage as well as the
     * WorldRenderer be able to handle input events.
     */
    protected InputMultiplexer cInputMultiplexer;
    /**
     * The Sprite used for the background.
     */
    protected Sprite cBackgroundSprite;
    /**
     * The current ScreenLogic that is active and displayed.
     */
    protected ScreenLogic cScreen;
    /**
     * Defines if the application is in 'landscape' or in 'portrait'.
     */
    protected boolean cIsLandscape;

    @Override
    public final void show() {
        initDefaults();
        captureInput();
        initBackgroundAndUI();
    }

    /**
     * Initializes the necessary components for this class to function.
     */
    protected final void initDefaults() {
        cCamera = new OrthographicCamera();
        cViewport = new ExtendViewport(
                cMinViewportRatio,
                cMinViewportRatio,
                cMaxViewportRatio,
                cMaxViewportRatio,
                cCamera);
        cBatch = new SpriteBatch();
        cStage = new Stage();
        cScreenStore = ScreenStore.getInstance();
    }

    /**
     * Sets the input to be captured by the stage and handles this WorldRenderer.
     */
    protected final void captureInput() {
        Gdx.input.setCatchBackKey(true);
        cInputMultiplexer = new InputMultiplexer();
        cInputMultiplexer.addProcessor(this);
        cInputMultiplexer.addProcessor(cStage);
        Gdx.input.setInputProcessor(cInputMultiplexer);
    }

    /**
     * Sets the default background and UI. First determines if the application is in Landscape or Portrait.
     * Then uses this to get the default background and set the initial UI scale.
     */
    protected final void initBackgroundAndUI() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        cIsLandscape = width > height;
        setDefaultBackground();
        if (cIsLandscape) {
            cScreenStore.getGameSkin().createUIElements(height);
        } else {
            cScreenStore.getGameSkin().createUIElements(width);
        }

    }

    @Override
    public final void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cCamera.update();
        cBatch.setProjectionMatrix(cCamera.combined);
        cBatch.begin();
        cBackgroundSprite.draw(cBatch);
        cBatch.end();
        cStage.act();
        cStage.draw();
    }

    /**
     * Upon resizing checks to see if the orientation is changed. If so, it will change the background to default.
     * Resizing always resets the GameSkin and ScreenLogic to match the new size.
     *
     * @param width  New width of the application.
     * @param height New height of the application.
     */
    @Override
    public final void resize(final int width, final int height) {
        cViewport.update(width, height);
        if (width > height != cIsLandscape) {
            cIsLandscape = !cIsLandscape;
            setDefaultBackground();
        }

        if (cIsLandscape) {
            cScreenStore.rebuild(height);
        } else {
            cScreenStore.rebuild(width);
        }

        if (cScreen != null) {
            setScreen(cScreen);
        }
    }

    /**
     * This method will use the current orientation to determine which default background to set.
     * Calls SetBackground() using the PATH to the proper background file.
     */
    protected final void setDefaultBackground() {
        if (cIsLandscape) {
            setBackground(cDefaultLandscapePath);
        } else {
            setBackground(cDefaultPortraitPath);
        }

    }

    /**
     * Sets the current background using the PATH that is supplied.
     *
     * @param filename PATH to the image.
     */
    public final void setBackground(final String filename) {
        FileHandle fileHandle = Gdx.files.internal(filename);
        if (fileHandle.exists()) {
            setBackground(fileHandle);
        }
    }

    /**
     * Discards previous background textures (if existing) and then sets the background to the file in the FileHandle.
     * If the new background does not fit the aspect ratio, the default background will be set.
     *
     * @param fileHandle The FileHandle to the file.
     */
    public final void setBackground(final FileHandle fileHandle) {
        if (cBackgroundSprite != null) {
            cBackgroundSprite.getTexture().dispose();
        }
        Texture texture = new Texture(fileHandle);
        if (texture.getWidth() > texture.getHeight() != cIsLandscape) {
            setDefaultBackground();
        } else {
            setBackgroundSprite(texture);
        }

    }

    /**
     * Properly sets the backgroundSprite to the new texture (background image).
     *
     * @param texture The texture to be used as background.
     */
    protected final void setBackgroundSprite(final Texture texture) {
        cBackgroundSprite = new Sprite(texture);
        if (cIsLandscape) {
            cBackgroundSprite.setSize(cMaxViewportRatio, cMinViewportRatio);
        } else {
            cBackgroundSprite.setSize(cMinViewportRatio, cMaxViewportRatio);
        }

        cBackgroundSprite.setOriginCenter();
        cBackgroundSprite
                .setPosition(cBackgroundSprite.getWidth() / cToCenter, cBackgroundSprite.getHeight() / cToCenter);
    }

    /**
     * Disposes the previous screen and sets the new given screen.
     *
     * @param screen Screen to set the view to
     */
    public final void setScreen(final ScreenLogic screen) {
        cInputMultiplexer.removeProcessor(cStage);
        cScreen = screen;
        cStage.dispose();
        cStage = new Stage();
        cStage.setDebugAll(false);
        cStage.addActor(cScreen.getWidgetGroup());
        cScreen.display();
        cInputMultiplexer.addProcessor(cStage);
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
    public final void dispose() {
        cBackgroundSprite.getTexture().dispose();
        cBatch.dispose();
        cStage.dispose();
    }

    @Override
    public final boolean keyDown(final int keycode) {
        if (keycode == Input.Keys.BACK || keycode == Input.Keys.F1) {
            String previousScreenName = cScreen.getPreviousScreenName();
            if (previousScreenName == null) {
                Gdx.app.exit();
            } else {
                cScreenStore.setScreen(previousScreenName);
            }
        }
        return false;
    }
}