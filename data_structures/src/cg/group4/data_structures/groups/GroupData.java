package cg.group4.data_structures.groups;

import java.io.Serializable;

/**
 * Contains the data of a group.
 */
public class GroupData implements Serializable {
    /**
     * Defines the id of a group.
     * Used to uniquely identify the group.
     */
    protected int cGroupId;

    /**
     * Defines the name of a group.
     * This name is not necessarily unique.
     */
    protected String cName;

    /**
     * Defines the owner of the group.
     * The owner of the group is based on a player id, which is unique.
     */
    protected String cOwnerId;

    /**
     * Name of the owner
     */
    protected String cOwnerName;

    public GroupData() {
    }

    public GroupData(int groupId, String groupName, String ownerId, String ownerName) {
        cGroupId = groupId;
        cName = groupName;
        cOwnerId = ownerId;
        cOwnerName = ownerName;
    }

    public String getGroupId() {
        return Integer.toString(cGroupId);
    }

    public void setGroupId(final int groupId) {
        cGroupId = groupId;
    }

    @Override
    public String toString() {
        return cName;
    }

    public void setName(final String name) {
        cName = name;
    }
}
