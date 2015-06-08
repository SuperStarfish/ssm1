package cg.group4.groups;

/**
 * Sample implementation of a group (for prototyping / testing).
 */
public class ConcreteGroup implements Group {

    @Override
    public int id() {
        return 100;
    }

    @Override
    public String name() {
        return "MyGroup";
    }

    @Override
    public int ownerId() {
        return 1;
    }
}
