package cg.group4;

import cg.group4.collection.Collection;

import java.io.Serializable;

/**
 * Contains all the data of the user.
 */
public class Player implements Serializable {

    /**
     * The id belonging to this user.
     */
    protected String cId;

    /**
     * The username for this user.
     */
    protected String cUsername;

    /**
     * The last time a stroll was started.
     */
    protected long cStrollTimeStamp;

    /**
     * The last time an interval was started.
     */
    protected long cIntervalTimeStamp;

    /**
     * The id of the group the member is from.
     */
    protected String cGroupId;

    /**
     * The collection of the player.
     */
    protected Collection cCollection;

    /**
     * A new UserData object containing the id for the current user.
     *
     * @param id The ID that belongs to the user.
     */
    public Player(final String id) {
        cId = id;
        cCollection = new Collection(cId);
        cUsername = "Anonymous";
    }

    /**
     * Gets the current user id.
     *
     * @return The ID that belongs to the user.
     */
    public String getId() {
        return cId;
    }

    /**
     * Sets the userID to the new id.
     *
     * @param id The new id.
     */
    public void setId(final String id) {
        cId = id;
    }

    /**
     * Gets the current username.
     *
     * @return The username that belongs to the user.
     */
    public String getUsername() {
        return cUsername;
    }

    /**
     * Sets the username to the new username.
     *
     * @param username The new username.
     */
    public void setUsername(final String username) {
        cUsername = username;
    }

    /**
     * Gets the current users last stroll time.
     *
     * @return The timestamp for the stroll.
     */
    public long getStrollTimeStamp() {
        return cStrollTimeStamp;
    }

    /**
     * Sets the stroll timestamp to the new timestamp.
     *
     * @param strollTimeStamp The new timestamp;
     */
    public void setStrollTimeStamp(final long strollTimeStamp) {
        cStrollTimeStamp = strollTimeStamp;
    }

    /**
     * Gets the current users last interval time.
     *
     * @return The timestamp for the interval.
     */
    public long getIntervalTimeStamp() {
        return cIntervalTimeStamp;
    }

    /**
     * Sets the interval timestamp to the new timestamp.
     *
     * @param intervalTimeStamp The new timestamp.
     */
    public void setIntervalTimeStamp(final long intervalTimeStamp) {
        cIntervalTimeStamp = intervalTimeStamp;
    }

    /**
     * Gets the group id.
     *
     * @return The group id
     */
    public String getGroupId() {
        return cGroupId;
    }

    /**
     * Sets the group id.
     *
     * @param cGroupId The group id.
     */
    public void setGroupId(String cGroupId) {
        this.cGroupId = cGroupId;
    }

    /**
     * Gets the players collection.
     *
     * @return The players collection.
     */
    public Collection getCollection() {
        return cCollection;
    }

    /**
     * Sets the players collection.
     *
     * @param cCollection The players collection.
     */
    public void setCollection(Collection cCollection) {
        this.cCollection = cCollection;
    }
}
