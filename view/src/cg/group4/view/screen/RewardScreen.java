package cg.group4.view.screen;

import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.data_structures.collection.collectibles.collectible_comparators.RarityComparator;
import cg.group4.view.rewards.CollectibleDrawer;
import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.text.DecimalFormat;

/**
 * Screen to display the rewards that have been gathered during an event.
 */
public final class RewardScreen extends ScreenLogic {

    /**
     * Button to return to the HomeScreen.
     */
    protected TextButton cHomeButton;
    /**
     * Label containing the reward.
     */
    protected Label cRewardLabel;
    /**
     * cContentTable contains the collectibles of the collection.
     */
    protected Table cContentTable;
    /**
     * ScrollPane displaying the collectibles of the collection.
     */
    protected ScrollPane cScrollPane;

    /**
     * The collection to display.
     */
    protected Collection cCollection;

    /**
     * Creates a new reward screen.
     *
     * @param collection The rewards to be displayed on the reward screen.
     */
    public RewardScreen(final Collection collection) {
        cCollection = collection;
    }

    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }

    @Override
    protected void rebuildWidgetGroup() {
        cHomeButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cRewardLabel.setStyle(cGameSkin.getDefaultLabelStyle());
        fillDisplay(cCollection);
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        Table table = new Table();
        table.setFillParent(true);

        cRewardLabel = new Label("Rewards", cGameSkin.getDefaultLabelStyle());
        table.row().expandY();
        table.add(cRewardLabel);

        createDisplay();
        fillDisplay(cCollection);
        table.row().expandY();
        table.add(cScrollPane).fill().expandY();

        cHomeButton = cGameSkin.generateDefaultMenuButton("Main Menu");
        cHomeButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                cScreenStore.setScreen("Home");
            }
        });
        table.row().expandY();
        table.add(cHomeButton);

        return table;
    }

    /**
     * Fills the drawer and table of the screen.
     */
    protected void createDisplay() {
        cContentTable = new Table();
        cScrollPane = new ScrollPane(cContentTable);
    }

    /**
     * Constructs the display of the rewards of the reward screen.
     *
     * @param collection the collection of the rewards collected during the stroll.
     */
    protected void fillDisplay(final Collection collection) {
        DecimalFormat format = new DecimalFormat("0.##");
        java.util.List<Collectible> list = collection.sort(new RarityComparator());
        cContentTable.clear();
        for (final Collectible collectible : list) {
            cContentTable.add(new Image(CollectibleDrawer.drawCollectible(collectible))).space(30);
        }
        cContentTable.row();
        for (final Collectible collectible : list) {
            cContentTable.add(cGameSkin.generateDefaultLabel(format.format(collectible.getRarity())));
        }
    }
}
