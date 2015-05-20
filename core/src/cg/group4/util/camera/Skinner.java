package cg.group4.util.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Skinner extends Skin {
    protected static Skinner instance;
    public static Skinner getInstance(){
        if(instance == null){
            instance = new Skinner();
        }
        return instance;
    }
    protected FreeTypeFontGenerator fontGenerator;
    protected String DEFAULT_FONT = "fonts/blow.ttf";
    protected int DEFAULT_BORDER_WIDTH = 2;
    protected int DEFAULT_FONT_SIZE = 42;
    final float DEV_HEIGHT = 720;
    final float UIScalar;
    float UI_HEIGHT;

    public Skinner(){
        UI_HEIGHT = Gdx.graphics.getHeight();
        UIScalar = UI_HEIGHT / DEV_HEIGHT;
        System.out.println(UIScalar);
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(DEFAULT_FONT));
        this.add("default_font", generateDefaultFont());
        this.add("default_textButtonStyle", generateDefaultTextButton());
    }

    protected TextButton.TextButtonStyle generateDefaultTextButton(){
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = Color.GREEN;
        buttonStyle.font = this.get("default_font", BitmapFont.class);

        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("images/wooden_sign.png")));
        float scalar = 0.42f;
        sprite.setSize(sprite.getWidth() * scalar * UIScalar, sprite.getHeight() * scalar * UIScalar);

        SpriteDrawable spriteDrawable = new SpriteDrawable(sprite);
        buttonStyle.up = spriteDrawable;

        return buttonStyle;
    }

    protected BitmapFont generateDefaultFont(){
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.borderColor = Color.BLACK;
        fontParameter.borderWidth = (int)(DEFAULT_BORDER_WIDTH * UIScalar);
        fontParameter.color = Color.WHITE;
        fontParameter.size = (int)(DEFAULT_FONT_SIZE * UIScalar);
        System.out.println(fontParameter.size);
        return fontGenerator.generateFont(fontParameter);
    }

    public TextButton generateDefaultMenuButton(String label){
        TextButton button = new TextButton(label, this.get("default_textButtonStyle", TextButton.TextButtonStyle.class));
        return button;
    }

    @Override
    public void dispose(){
        fontGenerator.dispose();
        super.dispose();
    }

}
