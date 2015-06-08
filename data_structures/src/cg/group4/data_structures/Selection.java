package cg.group4.data_structures;

import cg.group4.data_structures.groups.GroupData;

/**
 * Created by Ben on 8-6-2015.
 */
public class Selection {

    protected String cName;
    protected String cValue;

    public Selection(GroupData groupData) {
        cName = groupData.getName();
        cValue = Integer.toString(groupData.getGroupId());
    }

    public Selection(PlayerData playerData) {
        cName = playerData.getUsername();
        cValue = playerData.getId();
    }

    public String getValue() {
        return cValue;
    }

    public String toString() {
        return cName;
    }
}
