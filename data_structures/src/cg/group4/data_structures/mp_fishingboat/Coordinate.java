package cg.group4.data_structures.mp_fishingboat;

public class Coordinate {
    protected float cX,cY;

    public Coordinate(float x, float y) {
        cX = x;
        cY = y;
    }

    public float getX() {
        return cX;
    }

    public void setX(float x) {
        cX = x;
    }

    public float getY() {
        return cY;
    }

    public void setY(float y) {
        cY = y;
    }
}
