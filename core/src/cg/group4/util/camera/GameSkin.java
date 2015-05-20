package cg.group4.util.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * The default skin details for the application.
 *
 * @author Jurgen van Schagen
 * @author Martijn Gribnau
 */
public class GameSkin extends Skin {
    /**
     * Font generator for the skin.
     */
    protected FreeTypeFontGenerator fontGenerator;

    /**
     * Path to the default font.
     */
    protected String DEFAULT_FONT = "fonts/blow.ttf";

    /**
     * Default border width.
     */
    protected int DEFAULT_BORDER_WIDTH = 2;

    /**
     * Default font size.
     */
    final protected int DEFAULT_FONT_SIZE = 42;

    /**
     * Default dev height.
     * It is used to scale against the height used to develop the skin for.
     */
    final float DEV_HEIGHT = 720;

    /**
     * UI Scalar used to scale UI components.
     * This is needed because otherwise components will always have the same size. Higher resolution devices
     * will have a tiny UI if not scaled properly.
     * It is calculated by taking the height of the screen divided by the default height (DEV_HEIGHT).
     */
    final float UI_SCALAR;

    /**
     * The height of the screen.
     */
    float UI_HEIGHT;

    /**
     * Initializes the skin.
     */
    public GameSkin() {
        UI_HEIGHT = Gdx.graphics.getHeight();
        UI_SCALAR = UI_HEIGHT / DEV_HEIGHT;
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(DEFAULT_FONT));

        addFonts();
    }

    /**
     * Adds different fonts and their styles to the skin, to choose from.
     */
    protected final void addFonts() {
        this.add("default_font", generateDefaultFont());
        this.add("default_textButtonStyle", generateDefaultTextButton());
        this.add("default_titleFont", generateDefaultTitleFont());
        this.add("default_labelStyle", generateDefaultLabelStyle());
    }

    /**
     * The default text button skin.
     * @return TextButtonStyle
     */
    protected final TextButton.TextButtonStyle generateDefaultTextButton() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = Color.GREEN;
        buttonStyle.font = this.get("default_font", BitmapFont.class);

        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("images/wooden_sign.png")));
        final float scalar = 0.42f;
        sprite.setSize(sprite.getWidth() * scalar * UI_SCALAR, sprite.getHeight() * scalar * UI_SCALAR);

        buttonStyle.up = new SpriteDrawable(sprite);

        return buttonStyle;
    }

    /**
     * The default title font.
     * @return BitmapFont; Downside of the BitmapFont is that it does not scale by default. However, a ui scalar
     *         has been used to fix this issue.
     */
    protected final BitmapFont generateDefaultTitleFont() {
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.borderColor = Color.BLACK;
        fontParameter.borderWidth = (int) (DEFAULT_BORDER_WIDTH * UI_SCALAR);
        fontParameter.color = Color.WHITE;

        final float scale = 1.2f;
        fontParameter.size = (int) (DEFAULT_FONT_SIZE * UI_SCALAR * scale);
        return fontGenerator.generateFont(fontParameter);
    }

    /**
     * The default label style.
     * @return LabelStyle
     */
    protected final Label.LabelStyle generateDefaultLabelStyle() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = this.get("default_titleFont", BitmapFont.class);
        return labelStyle;
    }

    /**
     * The default text font.
     * @return BitmapFont; Downside of the BitmapFont is that it does not scale by default. However, a ui scalar
     *         has been used to fix this issue.
     */
    protected final BitmapFont generateDefaultFont() {
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.borderColor = Color.BLACK;
        fontParameter.borderWidth = (int) (DEFAULT_BORDER_WIDTH * UI_SCALAR);
        fontParameter.color = Color.WHITE;
        fontParameter.size = (int) (DEFAULT_FONT_SIZE * UI_SCALAR);
        return fontGenerator.generateFont(fontParameter);
    }

    /**
     * Used to generate text buttons using the default text button style.
     * @param label label text of the text button
     * @return TextButton
     */
    public TextButton generateDefaultMenuButton(final String label) {
        return new TextButton(label, this.get("default_textButtonStyle", TextButton.TextButtonStyle.class));
    }

    /**
     * Disposes data of the game skin.
     */
    @Override
    public void dispose() {
        fontGenerator.dispose();
        super.dispose();
    }

}
