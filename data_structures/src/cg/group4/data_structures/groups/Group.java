package cg.group4.data_structures.groups;

import cg.group4.data_structures.collection.Collection;

import java.io.Serializable;

/**
 * Representation of a group.
 */
public class Group implements Serializable {

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
     * Name of the owner
     */
    protected String cOwnerName;

    /**
     * Group collection
     */
    protected Collection cCollection;

    public Group(final String id) {
        cGroupId = id;
        cCollection = new Collection(id);
    }

    public String getGroupId() {
        return cGroupId;
    }

    public void setGroupId(final String cGroupId) {
        this.cGroupId = cGroupId;
    }

    public String getName() {
        return cName;
    }

    public void setName(final String cName) {
        this.cName = cName;
    }

    public String getOwnerId() {
        return cOwnerId;
    }

    public void setOwnerId(final String cOwnerId) {
        this.cOwnerId = cOwnerId;
    }

    public Collection getCollection() {
        return cCollection;
    }

    public void setCollection(final Collection cCollection) {
        this.cCollection = cCollection;
    }

    public String getOwnerName() {
        return cOwnerName;
    }

    public void setOwnerName(final String cOwnerName) {
        this.cOwnerName = cOwnerName;
    }

}
