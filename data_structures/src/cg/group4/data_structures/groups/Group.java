package cg.group4.data_structures.groups;

import cg.group4.data_structures.collection.Collection;

/**
 * Representation of a group.
 */
public class Group {

    /**
     * Defines the id of a group.
     * Used to uniquely identify the group.
     */
    protected String cGroupId;

    /**
     * Defines the name of a group.
     * This name is not necessarily unique.
     */
    protected String cName;

    /**
     * Defines the owner of the group.
     * The owner of the group is based on a player id, which is unique.
     *
     */
    protected String cOwnerId;

    /**
     * Group collection
     */
    protected Collection cCollection;

    public Group(final String id) {
        cCollection = new Collection(id);
    }

    public String getcGroupId() {
        return cGroupId;
    }

    public void setcGroupId(String cGroupId) {
        this.cGroupId = cGroupId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcOwnerId() {
        return cOwnerId;
    }

    public void setcOwnerId(String cOwnerId) {
        this.cOwnerId = cOwnerId;
    }

    public Collection getcCollection() {
        return cCollection;
    }

    public void setcCollection(Collection cCollection) {
        this.cCollection = cCollection;
    }
}
