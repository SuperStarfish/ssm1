package cg.group4.view.screen;

import cg.group4.game_logic.StandUp;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public final class NetworkScreen extends ScreenLogic{
    Table cTable;
    TextButton cConnect, cDisconnect, cBack;

    @Override
    protected WidgetGroup createWidgetGroup() {
        cTable = new Table();
        cTable.setFillParent(true);

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
                StandUp.getInstance().getClient().connectToServer();
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
