package cg.group4.game_logic;

/**
 * Created by Ben on 20-5-2015.
 */
public abstract class GameMechanic {

    public GameMechanic() {
        StandUp.getInstance().subscribe(this);
    }

    public abstract void update();

    public void dispose() {
        StandUp.getInstance().unSubscribe(this);
    }
}
