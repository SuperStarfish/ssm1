package cg.group4.game_logic.stroll.events.multiplayer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CraneHitbox extends Image {

    public CraneHitbox(Texture texture) {
        super(texture);
    }

    public boolean collidesWith(SmallFish fish) {
//        float x1 = Math.max(this.getX(), fish.getX());
//        float x2 = Math.max(this.getY(), fish.getY());
//        float x3 = Math.min(this.getX() + this.getWidth(), fish.getX() + fish.getWidth());
//        float x4 = Math.min(this.getY() + this.getHeight(), fish.getY() + fish.getHeight());
//        return x1 >= x3 || x2 >= x4;

        double maxRadiusBox = Math.sqrt(Math.pow(this.getWidth(), 2) + Math.pow(this.getHeight(), 2));
        double maxRadiusFish = Math.sqrt(Math.pow(fish.getWidth(), 2) + Math.pow(fish.getHeight(), 2));
        double distanceX = Math.abs(this.getCenterX() - fish.getCenterX());
        double distanceY = Math.abs(this.getCenterY() - fish.getCenterY());
        double distanceBetween = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        return distanceBetween < maxRadiusBox + maxRadiusFish;
    }

    public float getCenterX() {
        return this.getX() + this.getWidth() / 2;
    }

    public float getCenterY() {
        return this.getY() + this.getHeight() / 2;
    }

}
