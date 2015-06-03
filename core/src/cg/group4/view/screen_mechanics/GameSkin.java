package cg.group4.view.screen_mechanics;

import java.awt.Checkbox;

import cg.group4.rewards.collectibles.collectible_sorters.CollectibleSorter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

/**
 * The default skin details for the application.
 *
 * @author Jurgen van Schagen
 * @author Martijn Gribnau
 */
public class GameSkin extends Skin {
    /**
     * Default font size.
     */
    protected final int cDefaultFontSize = 42;
    /**
     * Default dev size.
     * It is used to scale the current screen size against the size used to develop the skin for.
     */
    protected final float cDevSize = 720;
    /**
     * UI Scalar used to scale UI components.
     * This is needed because otherwise components will always have the same size. Higher resolution devices
     * will have a tiny UI if not scaled properly.
     * It is calculated by taking the height of the screen divided by the default size (cDevSize).
     */
    protected float cUiScalar;
    /**
     * Font generator for the skin.
     */
    protected FreeTypeFontGenerator cFontGenerator;
    /**
     * Path to the default font.
     */
    protected String cDefaultFont = "fonts/blow.ttf";
    /**
     * Default border width.
     */
    protected int cDefaultBorderWidth = 2;

    /**
     * Initializes the skin.
     */
    public GameSkin() {
        cFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(cDefaultFont));
    }

    /**
     * Sets the UI Scalar and creates the UI elements based on this scale.
     *
     * @param newSize New size of the game window.
     */
    public final void createUIElements(final int newSize) {
        cUiScalar = newSize / cDevSize;
        addDefaults();
    }

    /**
     * Adds different fonts and their styles to the skin, to choose from.
     */
    protected final void addDefaults() {
        this.add("default_font", generateDefaultFont());
        this.add("default_textButtonStyle", generateDefaultTextButtonStyle());
        this.add("default_titleFont", generateDefaultTitleFont());
        this.add("default_labelStyle", generateDefaultLabelStyle());
        this.add("default_checkboxStyle", generateDefaultCheckboxStyle());
    }

    /**
     * Easy method to return the default TextButtonStyle as a proper class.
     *
     * @return TextButtonStyle object
     */
    public final TextButton.TextButtonStyle getDefaultTextButtonStyle() {
        return get("default_textButtonStyle", TextButton.TextButtonStyle.class);
    }

    /**
     * Easy method to return the default LabelStyle as a proper class.
     *
     * @return LabelStyle object
     */
    public final Label.LabelStyle getDefaultLabelStyle() {
        return get("default_labelStyle", Label.LabelStyle.class);
    }
    
    /**
     * Easy method to return the default CheckboxStyle as a proper class.
     * 
     * @return CheckboxStyle object
     */
    public final CheckBox.CheckBoxStyle getDefaultCheckboxStyle() {
    	return get("default_checkboxStyle", CheckBox.CheckBoxStyle.class);
    }

    /**
     * The default text button skin.
     *
     * @return TextButtonStyle
     */
    protected final TextButton.TextButtonStyle generateDefaultTextButtonStyle() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = Color.GREEN;
        buttonStyle.font = this.get("default_font", BitmapFont.class);

        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("images/wooden_sign.png")));
        final float scalar = 0.42f;
        sprite.setSize(sprite.getWidth() * scalar * cUiScalar, sprite.getHeight() * scalar * cUiScalar);

        buttonStyle.up = new SpriteDrawable(sprite);

        return buttonStyle;
    }

    protected final CheckBox.CheckBoxStyle generateDefaultCheckboxStyle() {
    	CheckBox.CheckBoxStyle checkboxStyle = new CheckBox.CheckBoxStyle();
    	checkboxStyle.checkboxOff = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/CheckBoxOff.png"))));
    	checkboxStyle.checkboxOn = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/CheckBoxOn.png"))));
    	checkboxStyle.font = this.get("default_font", BitmapFont.class);
    	checkboxStyle.fontColor = Color.GREEN;
    	
    	return checkboxStyle;
    }

    /**
     * The default title font.
     *
     * @return BitmapFont; Downside of the BitmapFont is that it does not scale by default. However, a ui scalar
     * has been used to fix this issue.
     */
    protected final BitmapFont generateDefaultTitleFont() {
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.borderColor = Color.BLACK;
        fontParameter.borderWidth = (int) (cDefaultBorderWidth * cUiScalar);
        fontParameter.color = Color.WHITE;

        final float scale = 1.2f;
        fontParameter.size = (int) (cDefaultFontSize * cUiScalar * scale);
        return cFontGenerator.generateFont(fontParameter);
    }

    /**
     * The default label style.
     *
     * @return LabelStyle
     */
    protected final Label.LabelStyle generateDefaultLabelStyle() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = this.get("default_titleFont", BitmapFont.class);
        return labelStyle;
    }

    /**
     * The default text font.
     *
     * @return BitmapFont; Downside of the BitmapFont is that it does not scale by default. However, a ui scalar
     * has been used to fix this issue.
     */
    protected final BitmapFont generateDefaultFont() {
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.borderColor = Color.BLACK;
        fontParameter.borderWidth = (int) (cDefaultBorderWidth * cUiScalar);
        fontParameter.color = Color.WHITE;
        fontParameter.size = (int) (cDefaultFontSize * cUiScalar);
        return cFontGenerator.generateFont(fontParameter);
    }

    /**
     * Used to generate text buttons using the default text button style.
     *
     * @param label label text of the text button
     * @return TextButton
     */
    public final TextButton generateDefaultMenuButton(final String label) {
        return new TextButton(label,
                this.get("default_textButtonStyle", TextButton.TextButtonStyle.class));
    }

    /**
     * Used to generate labels using the default label style.
     *
     * @param text label text of the text button
     * @return TextButton
     */
    public final Label generateDefaultLabel(final String text) {
    	Label result = new Label(text, this.get("default_labelStyle", Label.LabelStyle.class));
    	result.setAlignment(Align.center);
        return result;
    }
    
    public final CheckBox generateDefaultCheckbox(final String text) {
    	return new CheckBox(text, this.get("default_checkboxStyle", CheckBox.CheckBoxStyle.class));
 
    }

    /**
     * Disposes data of the game skin.
     */
    @Override
    public final void dispose() {
        cFontGenerator.dispose();
        super.dispose();
    }

}
