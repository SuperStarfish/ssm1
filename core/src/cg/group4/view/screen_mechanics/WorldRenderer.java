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

public class WorldRenderer extends InputAdapter implements Screen {
    protected Viewport cViewport;
    protected SpriteBatch cBatch;
    protected Stage cStage;
    protected ScreenStore cScreenStore;

    protected final float MIN_VIEWPORT_RATIO = 9f;
    protected final float MAX_VIEWPORT_RATIO = 16f;
    protected final float TO_CENTER = -2f;
    protected InputMultiplexer inputMultiplexer;
    protected OrthographicCamera cCamera;

    protected Sprite cBackgroundSprite;

    protected ScreenLogic cScreen;

    protected boolean cIsLandscape;

    protected final String DEFAULT_LANDSCAPE_BACKGROUND_PATH = "default_landscape_background.jpg";
    protected final String DEFAULT_PORTRAIT_BACKGROUND_PATH = "default_portrait_background.jpg";


    @Override
    public void show() {
        initDefaults();
        captureInput();
        initBackgroundAndUI();
    }

    /**
     * Initializes the necessary components for this class to function.
     */
    protected void initDefaults(){
        cCamera = new OrthographicCamera();
        cViewport = new ExtendViewport(MIN_VIEWPORT_RATIO, MIN_VIEWPORT_RATIO, MAX_VIEWPORT_RATIO, MAX_VIEWPORT_RATIO, cCamera);
        cBatch = new SpriteBatch();
        cStage = new Stage();
        cScreenStore = ScreenStore.getInstance();
    }

    /**
     * Sets the input to be captured by the stage and handles this WorldRenderer.
     */
    protected void captureInput(){
        Gdx.input.setCatchBackKey(true);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(cStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * Sets the default background and UI. First determines if the application is in Landscape or Portrait.
     * Then uses this to get the default background and set the initial UI scale.
     */
    protected void initBackgroundAndUI(){
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        cIsLandscape = width > height;
        setDefaultBackground();
        cScreenStore.getGameSkin().createUIElements(cIsLandscape ? height : width);
    }

    @Override
    public void render(float delta) {
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
     * @param width New width of the application.
     * @param height New height of the application.
     */
    @Override
    public void resize(int width, int height) {
        cViewport.update(width, height);
        if(width > height != cIsLandscape){
            cIsLandscape = !cIsLandscape;
            setDefaultBackground();
        }
        cScreenStore.rebuild(cIsLandscape ? height : width);
        if(cScreen != null){ setScreen(cScreen); }
    }

    /**
     * This method will use the current orientation to determine which default background to set.
     * Calls SetBackground() using the PATH to the proper background file.
     */
    protected void setDefaultBackground(){
        setBackground(cIsLandscape ? DEFAULT_LANDSCAPE_BACKGROUND_PATH : DEFAULT_PORTRAIT_BACKGROUND_PATH);
    }

    /**
     * Sets the current background using the PATH that is supplied.
     * @param filename PATH to the image.
     */
    public void setBackground(String filename){
        FileHandle fileHandle = Gdx.files.internal(filename);
        if(fileHandle.exists()){
            setBackground(fileHandle);
        }
    }

    /**
     * Discards previous background textures (if existing) and then sets the background to the file in the FileHandle.
     * If the new background does not fit the aspect ratio, the default background will be set.
     * @param fileHandle The FileHandle to the file.
     */
    public void setBackground(FileHandle fileHandle){
        if(cBackgroundSprite != null){
            cBackgroundSprite.getTexture().dispose();
        }
        Texture texture = new Texture(fileHandle);
        if(texture.getWidth() > texture.getHeight() != cIsLandscape){
            setDefaultBackground();
        } else{
            setBackgroundSprite(texture);
        }

    }

    /**
     * Properly sets the backgroundSprite to the new texture (background image)
     * @param texture The texture to be used as background.
     */
    protected void setBackgroundSprite(Texture texture){
        cBackgroundSprite = new Sprite(texture);
        cBackgroundSprite.setSize(cIsLandscape ? MAX_VIEWPORT_RATIO : MIN_VIEWPORT_RATIO,
                cIsLandscape ? MIN_VIEWPORT_RATIO : MAX_VIEWPORT_RATIO);
        cBackgroundSprite.setOriginCenter();
        cBackgroundSprite.setPosition(cBackgroundSprite.getWidth() / TO_CENTER, cBackgroundSprite.getHeight() / TO_CENTER);
    }

    /**
     * Disposes the previous screen and sets the new given screen.
     * @param screen Screen to set the view to
     */
    public final void setScreen(final ScreenLogic screen) {
        inputMultiplexer.removeProcessor(cStage);
        cScreen = screen;
        cStage.dispose();
        cStage = new Stage();
        cStage.addActor(cScreen.getWidgetGroup());
        inputMultiplexer.addProcessor(cStage);
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
        cBatch.dispose();
        cStage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            String previousScreenName = cScreen.getPreviousScreenName();
            if(previousScreenName == null){
                Gdx.app.exit();
            } else{
                cScreenStore.setScreen(previousScreenName);
            }
        }
        return false;
    }
}


///**
// * Class that handles the viewport and swapping of Actors and Sprites.
// * @author Jurgen van Schagen
// */
//public class WorldRenderer extends InputAdapter implements Screen{
//
//    /**
//     * Spritebatch for the background of the screen.
//     */
//    SpriteBatch cBatch;
//
//    /**
//     * The background sprite that will be rendered
//     */
//    Sprite cBackgroundSprite;
//
//    /**
//     * The stage in which actors are placed. Stage does not change.
//     */
//    Stage cStage;
//
//    /**
//     * Different screens can be 'built' by assigning the Screen in the WorldRenderer.
//     */
//    ScreenLogic cScreen;
//
//    /**
//     * The camera that captures what can be seen.
//     */
//    OrthographicCamera cCamera;
//
//    /**
//     * The visible viewport of the game. Aspect ratios between 16:9 and 4:3 are supported.
//     */
//    Viewport cViewPort;
//
//    protected ScreenStore cScreenStore;
//
//    /**
//     * The world is seen in a 4:3 ratio. This sets the width to also fit with 16:9 ratio.
//     */
//    static final float GAME_WORLD_WIDTH = 12f;
//
//    /**
//     * Specifies the max viewport width allowed.
//     */
//    static final float MAX_VIEWPORT_WIDTH = 16f;
//
//    /**
//     * The world is seen in a 4:3 ratio. This sets the height to also fit with 16:9 ratio.
//     */
//    static final float GAME_WORLD_HEIGHT = 9f;
//
//    /**
//     * User to reposition something to center.
//     */
//    static final float CENTERER = -2f;
//
//    /**
//     * Initializes everything for the camera and viewport. Also creates a SpriteBatch and Stage.
//     */
//    protected final void initCameraAndViewport(){
//        cBatch = new SpriteBatch();
//        cStage = new Stage();
//        StandUp.getInstance().getGameSkin().init();
//        cScreenStore = StandUp.getInstance().getScreenStore();
//        InputMultiplexer multiplexer = new InputMultiplexer();
//        multiplexer.addProcessor(cStage);
//        multiplexer.addProcessor(this);
//        Gdx.input.setCatchBackKey(true);
//        Gdx.input.setInputProcessor(multiplexer);
//
//        cViewPort = new ExtendViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, MAX_VIEWPORT_WIDTH, GAME_WORLD_HEIGHT, cCamera);
//        setBackground("default_background.jpg");
//    }
//
//    /**
//     * Disposes the previous screen and sets the new given screen.
//     * @param screen Screen to set the view to
//     */
//    public final void setScreen(final ScreenLogic screen) {
//        cScreen = screen;
//        cStage.clear();
//        cStage.addActor(cScreen.getWidgetGroup());
//    }
//
//    /**
//     * Calls setBackground with a FileHandle after finding the specified file.
//     * @param fileName Path to the file relative to assets folder.
//     */
//    public final void setBackground(final String fileName){
//        setBackground(Gdx.files.internal(fileName));
//    }
//
//    /**
//     * Sets the background to fit properly in the viewport.
//     * @param file The background FileHandle
//     */
//    public final void setBackground(final FileHandle file) {
//        if (cBackgroundSprite != null) {
//            cBackgroundSprite.getTexture().dispose();
//        }
//        cBackgroundSprite = new Sprite(new Texture(file));
//        cBackgroundSprite.setSize(
//                (cBackgroundSprite.getWidth() / cBackgroundSprite.getHeight()) * GAME_WORLD_HEIGHT,
//                    GAME_WORLD_HEIGHT);
//        cBackgroundSprite.setPosition(cBackgroundSprite.getWidth() / CENTERER, cBackgroundSprite.getHeight() / CENTERER);
//    }
//
//    /**
//     * Creates everything that is needed to properly display the game and sets the screen to the HomeScreen.
//     */
//    @Override
//    public final void show() {
//        initCameraAndViewport();
//    }
//
//    @Override
//    public final void render(float delta) {
//        cViewPort.apply();
//        renderDefaults();
//        cBatch.begin();
//        cBackgroundSprite.draw(cBatch);
//        cBatch.end();
//        cStage.act();
//        cStage.draw();
//    }
//
//    /**
//     * Does the very basic task of 'cleaning' the screen so a new render can happen.
//     */
//    protected final void renderDefaults(){
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        cCamera.update();
//        cBatch.setProjectionMatrix(cCamera.combined);
//    }
//
//    @Override
//    public final void resize(final int width, final int height) {
//        cViewPort.update(width, height);
//    }
//
//    @Override
//    public final void pause() {
//
//    }
//
//    @Override
//    public final void resume() {
//
//    }
//
//    @Override
//    public final void hide() {
//
//    }
//
//    @Override
//    public final void dispose() {
//        cBackgroundSprite.getTexture().dispose();
//    }
//
//    @Override
//    public boolean keyDown (int keycode) {
//        switch (keycode) {
//            case Input.Keys.BACK:
//                String previousScreenName = cScreen.getPreviousScreenName();
//                if(previousScreenName == null) {
//                    Gdx.app.exit();
//                } else{
//                    cScreenStore.setScreen(previousScreenName);
//                }
//                break;
//        }
//        return false;
//    }
//}
