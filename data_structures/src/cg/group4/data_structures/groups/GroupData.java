package cg.group4.data_structures.groups;

/**
 * Created by Martijn on 2015-06-08.
 */
public class GroupData {
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
     *
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

    public String getOwnerName() {
        return cOwnerName;
    }

    public void setOwnerName(final String cOwnerName) {
        this.cOwnerName = cOwnerName;
    }

    public int getGroupId() {
        return cGroupId;
    }

    public void setGroupId(final int cGroupId) {
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
}
