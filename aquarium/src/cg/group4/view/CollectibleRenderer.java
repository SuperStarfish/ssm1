package cg.group4.view;

import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.display_logic.DisplaySettings;
import cg.group4.view.util.rewards.CollectibleDrawer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

// TODO always move forward (1 move method)
// probability to turn a random degree between min max
//

/**
 * .
 */
public class CollectibleRenderer {
    protected CollectibleDrawer cCollectibleDrawer;
    protected Image cCollectibleActor;
    protected Stage cStage;
    protected int cCurrentDirection;

    public CollectibleRenderer(final Collectible collectible) {
        cStage = new Stage();
        setCollection(collectible);
        cStage.addActor(cCollectibleActor);
    }

    public void render() {
        move("ne");
        cStage.act();
        cStage.draw();
    }

    public void setCollection(final Collectible collectible) {
        cCollectibleDrawer = new CollectibleDrawer();
        final Texture collectibleTexture = cCollectibleDrawer.drawCollectible(collectible);
        cCollectibleActor = new Image(collectibleTexture);
        cCollectibleActor.setPosition(500f,500f);
    }

    public void move(String direction) {
        switch (direction) {
            case "west":
                moveWest();
                break;
            case "north":
                moveNorth();
                break;
            case "south":
                moveSouth();
                break;
            case "ne":
                moveNorthEast();
                break;
            case "se":
                moveSouthEast();
                break;
            case "nw":
                moveNorthWest();
                break;
            case "sw":
                moveSouthWest();
                break;
            default:
                moveEast();
                break;
        }
    }

    private void moveNorth() {
        setRotation(Rotation.north);

        float y = cCollectibleActor.getY();

        if (y + DisplaySettings.cSpeed <= DisplaySettings.screenMaxY - cCollectibleActor.getImageHeight()) {
            y += DisplaySettings.cSpeed;
        }

        cCollectibleActor.setPosition(cCollectibleActor.getX(), y);
    }

    private void moveSouth() {
        setRotation(Rotation.south);

        float y = cCollectibleActor.getY();

        if (y - DisplaySettings.cSpeed >= DisplaySettings.screenMinY + cCollectibleActor.getImageHeight()) {
            y -= DisplaySettings.cSpeed;
        }

        cCollectibleActor.setPosition(cCollectibleActor.getX(), y);
    }

    private void moveEast() {
        setRotation(Rotation.east);

        float x = cCollectibleActor.getX();

        if (x + DisplaySettings.cSpeed <= DisplaySettings.screenMaxX - cCollectibleActor.getImageWidth()) {
            x += DisplaySettings.cSpeed;
        }

        cCollectibleActor.setPosition(x, cCollectibleActor.getY());
    }

    private void moveWest() {
        setRotation(Rotation.west);

        float x = cCollectibleActor.getX();

        if (x - DisplaySettings.cSpeed >= DisplaySettings.screenMinX + cCollectibleActor.getImageWidth()) {
            x -= DisplaySettings.cSpeed;
        }

        cCollectibleActor.setPosition(x, cCollectibleActor.getY());
    }

    private void moveNorthEast() {
        setRotation(Rotation.northEast);
        moveNorth();
        moveEast();
    }

    private void moveSouthEast() {
        setRotation(Rotation.southEast);
        moveSouth();
        moveEast();
    }

    private void moveSouthWest() {
        setRotation(Rotation.southWest);
        moveSouth();
        moveWest();
    }

    private void moveNorthWest() {
        setRotation(Rotation.northWest);
        moveNorth();
        moveWest();
    }


    private void setRotation(Rotation rotation) {
        if (cCurrentDirection != rotation.degree()) {
            cCollectibleActor.setRotation(rotation.degree());
            cCurrentDirection = rotation.degree();
        }
    }

    public enum Rotation {
        north(0),
        northEast(45),
        east(90),
        southEast(135),
        south(180),
        southWest(225),
        west(270),
        northWest(315);

        protected int rotationLevel;

        Rotation(int degree) {
           this.rotationLevel = degree;
        }

        int degree() {
            return rotationLevel;
        }

    }



}
