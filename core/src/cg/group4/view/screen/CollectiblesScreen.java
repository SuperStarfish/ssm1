package cg.group4.view.screen;

import java.util.ArrayList;

import cg.group4.rewards.CollectibleDrawer;
import cg.group4.rewards.collectibles.Collectible;
import cg.group4.rewards.collectibles.collectibleSorters.CollectibleSorter;
import cg.group4.rewards.collectibles.collectibleSorters.SortByRarity;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class CollectiblesScreen extends ScreenLogic {
	
	protected Table cContentTable, cContainer;
	protected ScrollPane cScrollPane;
	protected Collection cCollection;
	protected TextButton cBackButton;
	protected final int cItemsOnScreen = 10;
	protected CollectibleDrawer cDrawer;
	protected CollectibleSorter cSorter;
	
	public CollectiblesScreen(Collection collection) {
		cCollection = collection;
		cSorter = new SortByRarity();
	}

	@Override
	protected WidgetGroup createWidgetGroup() {
		final int screenWidth = Gdx.graphics.getWidth();
		final int screenHeight = Gdx.graphics.getHeight();
		cDrawer = new CollectibleDrawer();
		
		cContainer = new Table();
		cContainer.setFillParent(true);
		
		cBackButton = cGameSkin.generateDefaultMenuButton("Back");
		cBackButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                ScreenStore.getInstance().setScreen("Home");
            }
        });
		
		cContainer.add(cBackButton).expandX().height(screenHeight / cItemsOnScreen);
		
		cContentTable = new Table();
		cContentTable.setWidth(screenWidth);
		cScrollPane = new ScrollPane(cContentTable);
		cScrollPane.setForceScroll(false, true);
		
		cContainer.add(cGameSkin.generateDefaultLabel("CHECKBUTTONSSSSS"));
		cContainer.row();
		cContainer.add(cScrollPane).colspan(2);
		
		constructContents(screenWidth, screenHeight);
		
		return cContainer.debugAll();
	}

	@Override
	protected void rebuildWidgetGroup() {
		this.getWidgetGroup();
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();

		cContentTable.clear();
		constructContents(screenWidth, screenHeight);
		
	}

	@Override
	protected String setPreviousScreenName() {
		return "Home";
	}
	
	protected void constructContents(int screenWidth, int screenHeight) {
		ArrayList<Collectible> sortedList = cSorter.sortCollectibles(cCollection.getCollection());
		System.out.println(sortedList.size());
		
		for (Collectible c : sortedList) {
			cContentTable.row().height(screenHeight / cItemsOnScreen).width(screenWidth);
			cContentTable.add(cGameSkin.generateDefaultLabel(Double.toString(c.getRarity())));
		}
	}

}
