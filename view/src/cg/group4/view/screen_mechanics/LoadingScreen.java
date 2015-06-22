package cg.group4.view.screen_mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Loading screen that is displayed while Assets are being loaded.
 */
public class LoadingScreen implements Screen {
    /**
     * Scale is based on largest screen size we developed on.
     */
    protected static final float SCALE_SIZE = 1440;
    /**
     * The assets that have to be loaded.
     */
    protected Assets cAssets;
    /**
     * The launcher, used to tell the assets are done.
     */
    protected AssetsLoadingHandler cLauncher;
    /**
     * The SpriteBatch used to draw the logo.
     */
    protected SpriteBatch cBatch;
    /**
     * The logo that is to be drawn.
     */
    protected Sprite cLogo;
    /**
     * Scales to logo to fit different resolutions.
     */
    protected float cLogoScalar;
    /**
     * The width of the game.
     */
    protected int cScreenWidth;

    /**
     * The height of the game.
     */
    protected int cScreenHeight;

    /**
     * Creates a new LoadingScreen with a reference back to the launcher.
     *
     * @param launcher Reference back to the launcher.
     */
    public LoadingScreen(final AssetsLoadingHandler launcher) {
        cLauncher = launcher;
        cAssets = Assets.getInstance();
    }

    /**
     * Initializes everything that is needed to draw the logo.
     */
    @Override
    public void show() {
        cScreenWidth = Gdx.graphics.getWidth();
        cScreenHeight = Gdx.graphics.getHeight();
        if (cScreenWidth > cScreenHeight) {
            setScalar(cScreenHeight);
        } else {
            setScalar(cScreenWidth);
        }

        cBatch = new SpriteBatch();
        cLogo = new Sprite(new Texture(Gdx.files.internal("images/logo.png")));
        setLogo();
    }

    /**
     * Properly sets the scalar for the logo so it fits different resolutions.
     *
     * @param lowestRatio The lowest resolution, either height or width.
     */
    protected void setScalar(final int lowestRatio) {
        cLogoScalar = lowestRatio / SCALE_SIZE;
    }

    /**
     * Positions the logo in the center of the screen.
     */
    protected void setLogo() {
        cLogo.setOriginCenter();
        cLogo.setSize(fitSize(cLogo.getWidth()), fitSize(cLogo.getHeight()));
        cLogo.setPosition(cScreenWidth / 2 - (cLogo.getWidth() / 2), cScreenHeight / 2 - (cLogo.getHeight() / 2));
    }

    /**
     * Used to fit the width and height of the logo Sprite to proper position.
     *
     * @param originalSize The original width.
     * @return The width scaled against the screen resolution.
     */
    protected float fitSize(final float originalSize) {
        return originalSize * cLogoScalar;
    }

    @Override
    public void render(final float delta) {
        if (cAssets.update()) {
            cLauncher.assetsDone();
        } else {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            cBatch.begin();
            cLogo.draw(cBatch);
            cBatch.end();
        }
    }

    @Override
    public void resize(final int width, final int height) {

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
        cBatch.dispose();
        cLogo.getTexture().dispose();
    }
}
