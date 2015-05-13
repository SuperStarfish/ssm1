package cg.group4;

import cg.group4.view.StrollScreen;

import com.badlogic.gdx.Game;

/**
 * The StandUp class serves as an input point for the LibGDX application.
 * This class handles all the cycles that LibGDX goes through and thus
 * serves as the backbone of the entire application.
 */
public class Launcher extends Game {

    @Override
    public void create() {
        setScreen(new StrollScreen());
    }
}
