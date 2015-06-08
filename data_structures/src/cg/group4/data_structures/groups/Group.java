package cg.group4.data_structures.groups;

import cg.group4.data_structures.collection.Collection;

import java.io.Serializable;

/**
 * Representation of a group.
 */
public class Group implements Serializable {

    /**
     * group data.
     */
    protected GroupData cGroupData;

    /**
     * Group collection
     */
    protected Collection cCollection;

    public Group(final String id) {
        cCollection = new Collection(id);
    }

    public Group(final String id, final GroupData groupData) {
        cCollection = new Collection(id);
        cGroupData = groupData;
    }

    // group data
    public GroupData getGroupData() {
        return cGroupData;
    }

    public void setGroupData(final GroupData cGroupData) {
        this.cGroupData = cGroupData;
    }

    // collections
    public Collection getCollection() {
        return cCollection;
    }

    public void setCollection(final Collection cCollection) {
        this.cCollection = cCollection;
    }



}
