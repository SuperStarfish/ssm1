package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.client.query.Data;
import cg.group4.client.query.NoReply;
import cg.group4.client.query.UserData;
import cg.group4.game_logic.StandUp;
import cg.group4.rewards.Collection;
import cg.group4.rewards.collectibles.FishA;
import cg.group4.rewards.collectibles.FishB;
import cg.group4.rewards.collectibles.FishC;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

public final class NetworkScreen extends ScreenLogic{
    Table cTable;
    TextButton cChangeUsername, cBack;
    Label cMessage;

    @Override
    protected WidgetGroup createWidgetGroup() {
        Client client = Client.getInstance();
        client.connectToServer();

        ScreenStore screenStore = ScreenStore.getInstance();
        screenStore.addScreen("Change-Username", new ChangeUsernameScreen());

        cTable = new Table();
        cTable.setFillParent(true);

        if(client.isConnected()) {
            addLabel("Connected");

        } else {
            addLabel("Not Connected!");
        }

        addChangeUserName();

        addBackButton();

        return cTable;
    }

    protected void addChangeUserName() {
        cChangeUsername = cGameSkin.generateDefaultMenuButton("Username");
        cChangeUsername.addListener(usernameBehaviour());
        cTable.row().expandY();
        cTable.add(cChangeUsername);
    }

    protected void addLabel(String text) {
        cMessage = cGameSkin.generateDefaultLabel(text);
        cTable.row().expandY();
        cTable.add(cMessage);
    }

    protected void addBackButton() {
        cBack = cGameSkin.generateDefaultMenuButton("Back");
        cBack.addListener(backBehaviour());
        cTable.row().expandY();
        cTable.add(cBack);
    }

    protected ChangeListener usernameBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenStore.getInstance().setScreen("Change-Username");
            }
        };
    }

    protected ChangeListener backBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenStore.getInstance().setScreen(getPreviousScreenName());

            }
        };
    }

    @Override
    protected void rebuildWidgetGroup() {
        getWidgetGroup();

        cBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    @Override
    protected String setPreviousScreenName() {
        return "Settings";
    }
}
