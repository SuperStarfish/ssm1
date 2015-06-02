package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.game_logic.StandUp;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    TextButton cConnect, cDisconnect, cBack;
    TextField cIP, cPort;

    @Override
    protected WidgetGroup createWidgetGroup() {
        cTable = new Table();
        cTable.setFillParent(true);

        Drawable drawable = new SpriteDrawable(new Sprite(new Texture("images/debugpixel.png")));
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle(cGameSkin.getFont("default_font"), Color.WHITE, drawable, drawable, drawable);

        cIP = new TextField(Client.defaultIP, textFieldStyle);
        cIP.setAlignment(Align.center);
        cTable.row().expandY();
        cTable.add(cIP).fill();

        cPort = new TextField(Integer.toString(Client.defaultPort), textFieldStyle);
        cPort.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        cPort.setAlignment(Align.center);
        cTable.row().expandY();
        cTable.add(cPort).fill();

        cConnect = cGameSkin.generateDefaultMenuButton("Connect");
        cConnect.addListener(connectBehaviour());
        cTable.row().expandY();
        cTable.add(cConnect);

        cDisconnect = cGameSkin.generateDefaultMenuButton("Disconnect");
        cDisconnect.addListener(disconnectBehaviour());
        cTable.row().expandY();
        cTable.add(cDisconnect);

        cBack = cGameSkin.generateDefaultMenuButton("Back");
        cBack.addListener(backBehaviour());
        cTable.row().expandY();
        cTable.add(cBack);

        return cTable;
    }

    protected ChangeListener connectBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    StandUp.getInstance().getClient().connectToServer(cIP.getText(), Integer.parseInt(cPort.getText()));
                } catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        };
    }

    protected ChangeListener disconnectBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StandUp.getInstance().getClient().closeConnection();
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
        cConnect.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cDisconnect.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    @Override
    protected String setPreviousScreenName() {
        return "Settings";
    }
}
