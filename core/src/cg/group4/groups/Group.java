package cg.group4.groups;

/**
 * Representation of a group.
 */
public interface Group {

    /**
     * Defines the id of a group.
     * Used to uniquely identify the group.
     *
     * @return Id
     */
    int id();

    /**
     * Defines the name of a group.
     * This name is not necessarily unique.
     *
     * @return Name
     */
    String name();

    /**
     * Defines the owner of the group.
     * The owner of the group is based on a player id, which is unique.
     *
     * @return owner id
     */
    int ownerId();

}
