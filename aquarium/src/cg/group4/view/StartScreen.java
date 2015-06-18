package cg.group4.view;

import cg.group4.aquarium.Aquarium;
import cg.group4.view.screen_mechanics.GameSkin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import org.lwjgl.util.vector.Vector3f;

import java.util.Observable;
import java.util.Observer;

/**
 * Screen which is shown at startup of the aquarium application.
 * Gives the possibility to fill in a group cId so your aquarium will be shown.
 *
 * Layout overview:
 *
 * Your group cId:                label
 *  (   ...   )                  textfield
 *  [Show aquarium]              button
 *
 */
public class StartScreen implements Screen, Observer {

    /**
     * Gameskin for the layout items.
     */
    protected GameSkin cGameSkin;

    /**
     * Stage for the table.
     */
    protected Stage cStage;

    /**
     * Table for the layout.
     */
    protected Table cContainer;

    /**
     * Layout with information text about the groupId (text field).
     */
    protected Label cGroupIdLabel;

    /**
     * Label which will display status text upon a click on the cButton.
     */
    protected Label cStatusLabel;

    /**
     * Input text field for the group cId.
     */
    protected TextField cGroupIdTextField;

    /**
     * Button which upon successfully checking the text field for legal information will start the interactions,
     * to go to the aquarium screen.
     */
    protected Button cButton;

    /**
     * Status of the server connection.
     */
    protected boolean cServerConnectionFailure;

    /**
     * Initializes the start screen.
     */
    public StartScreen() {
        cGameSkin = new GameSkin();
        cStage = new Stage();
        cContainer = new Table();

        final int textScale = 800;
        cGameSkin.createUIElements(textScale);
    }

    /**
     * Creates the group cId info label.
     */
    public void createGroupIdLabel() {
        final String welcomeText = "Fill in your Group Id to show your group aquarium: ";
        cGroupIdLabel = new Label(welcomeText, cGameSkin.getDefaultLabelStyle());
        cContainer.add(cGroupIdLabel);
        cContainer.row();
    }

    /**
     * Creates the status label.
     */
    public void createStatusLabel() {
        final String statusText = "";
        cStatusLabel = new Label(statusText, cGameSkin.getDefaultLabelStyle());
        cContainer.add(cStatusLabel);
        cContainer.row();
    }

    /**
     * Creates the group cId input text field.
     */
    public void createGroupIdTextField() {
        final String defaultText = "7";
        cGroupIdTextField = cGameSkin.generateDefaultTextField(defaultText);
        cGroupIdTextField.setAlignment(Align.center);
        cGroupIdTextField.setWidth(cGroupIdTextField.getWidth());
        cContainer.add(cGroupIdTextField);
        cContainer.row();
    }

    /**
     * Creates the start button which will set the AquariumScreen.
     */
    public void createButton() {
        final String text = "Show Aquarium";
        cButton = cGameSkin.generateDefaultMenuButton(text);
        cButton.addListener(startScreenButtonListener());
        cContainer.add(cButton);
        cContainer.row();
    }

    /**
     * Returns the ChangeListener which starts the Aquarium Screen.
     * @return ChangeListener
     */
    public ChangeListener startScreenButtonListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String input =  cGroupIdTextField.getText();

                if (input.length() <= 0) {
                    cStatusLabel.setText("Input insufficient (group cId can not be empty)");
                } else if (!input.matches("\\d+")) {
                    cStatusLabel.setText("Input insufficient (group cId has to be a natural number)");
                } else if (cServerConnectionFailure) {
                    cStatusLabel.setText("Connection with the server failed!");
                } else {
                    cStatusLabel.setText("Connecting...");
                    Aquarium.getInstance().initAquarium(cGroupIdTextField.getText());
                }

            }
        };
    }

    @Override
    public void show() {
        cContainer.setFillParent(true);

        createGroupIdLabel();
        createGroupIdTextField();
        createButton();
        createStatusLabel();

        cStage.addActor(cContainer);
        Gdx.input.setInputProcessor(cStage);
    }

    @Override
    public void render(float delta) {
        cStage.act(delta);
        final Vector3f colour = new Vector3f(0.4f, 0.7f, 0.1f);
        final float alpha = 1f;

        Gdx.gl.glClearColor(colour.getX(), colour.getY(), colour.getZ(), alpha);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cStage.draw();
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
    public void update(Observable o, Object arg) {
        boolean result = (boolean) arg;

        cServerConnectionFailure = !result;
    }
}
