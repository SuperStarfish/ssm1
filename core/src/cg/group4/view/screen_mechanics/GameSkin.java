package cg.group4.view.screen_mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
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
     * Default width for the cursor in TextFields.
     */
    protected final float cCursorWidth = 3f;
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
    protected String cDefaultFont = "fonts/BuxtonSketch.ttf";
    /**
     * Default border width.
     */
    protected int cDefaultBorderWidth = 2;
    /**
     * Container for all the assets.
     */
    protected Assets cAssets;

    /**
     * Initializes the skin.
     */
    public GameSkin() {
        cAssets = Assets.getInstance();
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
        this.add("default_selectboxStyle", generateDefaultSelectboxStyle());
        this.add("default_listStyle", generateDefaultListStyle());
        this.add("default_textFieldStyle", generateDefaultTextFieldStyle());
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
     * The default text button skin.
     *
     * @return TextButtonStyle
     */
    protected final TextButton.TextButtonStyle generateDefaultTextButtonStyle() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = Color.GREEN;
        buttonStyle.font = this.get("default_font", BitmapFont.class);
        Sprite sprite = new Sprite(cAssets.getTexture("images/wooden_sign.png"));
        Sprite disabled = new Sprite(cAssets.getTexture("images/wooden_sign_gray.png"));
        final float scalar = 0.42f;
        sprite.setSize(sprite.getWidth() * scalar * cUiScalar, sprite.getHeight() * scalar * cUiScalar);
        disabled.setSize(sprite.getWidth() * scalar * cUiScalar, sprite.getHeight() * scalar * cUiScalar);
        buttonStyle.up = new SpriteDrawable(sprite);
        sprite = new Sprite(sprite);
        sprite.setColor(.8f, .8f, .8f, 1);
        buttonStyle.over = new SpriteDrawable(sprite);
        sprite = new Sprite(sprite);
        sprite.setColor(.6f, .6f, .6f, 1);
        buttonStyle.down = new SpriteDrawable(sprite);
        buttonStyle.disabled = new SpriteDrawable(disabled);
        return buttonStyle;
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
     * The default Checkbox style.
     *
     * @return CheckBoxStyle
     */
    protected final CheckBox.CheckBoxStyle generateDefaultCheckboxStyle() {
    	CheckBox.CheckBoxStyle checkboxStyle = new CheckBox.CheckBoxStyle();
    	checkboxStyle.checkboxOff = new TextureRegionDrawable(
    			new TextureRegion(cAssets.getTexture("images/CheckBoxOff.png")));
    	checkboxStyle.checkboxOn = new TextureRegionDrawable(
    			new TextureRegion(cAssets.getTexture("images/CheckBoxOn.png")));

    	checkboxStyle.font = this.get("default_font", BitmapFont.class);
    	checkboxStyle.fontColor = Color.GREEN;

    	return checkboxStyle;
    }

    /**
     * The default Selectbox style.
     *
     * @return SelectBoxStyle
     */
    protected final SelectBox.SelectBoxStyle generateDefaultSelectboxStyle() {
        SelectBox.SelectBoxStyle selectboxStyle = new SelectBox.SelectBoxStyle();
        selectboxStyle.font = this.get("default_font", BitmapFont.class);
        selectboxStyle.fontColor = Color.GREEN;
        selectboxStyle.listStyle = this.generateDefaultListStyle();
        selectboxStyle.scrollStyle = new ScrollPane.ScrollPaneStyle();
        selectboxStyle.background = new TextureRegionDrawable(
                new TextureRegion(cAssets.getTexture("images/wooden_sign.png")));
        selectboxStyle.background.setLeftWidth(10);
        return selectboxStyle;
    }

    /**
     * The default List style.
     *
     * @return ListStyle
     */
    protected final List.ListStyle generateDefaultListStyle() {
    	List.ListStyle listStyle = new List.ListStyle();
    	listStyle.font = this.get("default_font", BitmapFont.class);
    	listStyle.fontColorSelected = Color.GREEN;
    	listStyle.fontColorUnselected = Color.WHITE;
        listStyle.background = new TextureRegionDrawable(
                new TextureRegion(cAssets.getTexture("images/wooden_sign.png")));
        listStyle.selection = new TextureRegionDrawable(
                new TextureRegion(cAssets.getTexture("images/wooden_sign.png"))).tint(new Color(0, 0, 0, .3f));

        listStyle.selection.setLeftWidth(10);
        listStyle.selection.setRightWidth(10);
        return listStyle;
    }

    /**
     * Creates a default TextField Style.
     *
     * @return The style for the TextField.
     */
    protected final TextField.TextFieldStyle generateDefaultTextFieldStyle() {
        BaseDrawable empty = new BaseDrawable();
        BitmapFont font = this.get("default_font", BitmapFont.class);
        Color color = Color.GREEN;
        Drawable cursor = new SpriteDrawable(new Sprite(cAssets.getTexture("images/blackpixel.jpg")));
        cursor.setMinWidth(cCursorWidth * cUiScalar);
        Drawable background = new SpriteDrawable(new Sprite(cAssets.getTexture("images/debugpixel.png")));
        return new TextField.TextFieldStyle(font, color, cursor, background, empty);
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
     * Easy method to return the default ListStyle as a proper class.
     *
     * @return ListStyle object
     */
    public final List.ListStyle getDefaultListStyle() {
        return get("default_listStyle", List.ListStyle.class);
    }

    /**
     * Easy method to return the default SelectBox as a proper class.
     *
     * @return SelectBox object
     */
    public final SelectBox.SelectBoxStyle getDefaultSelectboxStyle() {
        return get("default_selectboxStyle", SelectBox.SelectBoxStyle.class);
    }

    /**
     * Generates a TextField using default settings and supplied text.
     *
     * @param initialText The text to initially display.
     * @return A TextField object.
     */
    public final TextField generateDefaultTextField(final String initialText) {
        return new TextField(initialText, getDefaultTextFieldStyle());
    }

    /**
     * Easy method to get the TextField style.
     *
     * @return TextField style used.
     */
    public final TextField.TextFieldStyle getDefaultTextFieldStyle() {
        return this.get("default_textFieldStyle", TextField.TextFieldStyle.class);
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

    /**
     * Used to generate lists using the default list style.
     *
     * @param <T> Objects or primitives
     * @return List
     */
    public final <T> List<T> generateDefaultList() {
        return new List<T>(generateDefaultListStyle());
    }

    /**
     * Used to generate selectboxes using the default selectbox style.
     *
     * @param <T> Objects or primitives
     * @return SelectBox.
     */
    public final <T> SelectBox<T> generateDefaultSelectbox() {
        return new SelectBox<T>(this.get("default_selectboxStyle", SelectBox.SelectBoxStyle.class));
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
