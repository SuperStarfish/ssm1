package cg.group4.client.query;

import java.io.Serializable;

/**
 * UserData containing all the data needed for that user.
 */
public final class UserData extends Data implements Serializable {

    /**
     * The id belonging to this user.
     */
    protected String cID;

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
     * A new empty UserData object.
     */
    public UserData() { }

    /**
     * A new UserData object containing the id for the current user.
     * @param id The ID that belongs to the user.
     */
    public UserData(final String id) {
        cID = id;
    }

    /**
     * Gets the current user id.
     * @return The ID that belongs to the user.
     */
    public String getcID() {
        return cID;
    }

    /**
     * Sets the userID to the new id.
     * @param id The new id.
     */
    public void setcID(final String id) {
        cID = id;
    }

    /**
     * Gets the current username.
     * @return The username that belongs to the user.
     */
    public String getcUsername() {
        return cUsername;
    }

    /**
     * Sets the username to the new username.
     * @param username The new username.
     */
    public void setcUsername(final String username) {
        cUsername = username;
    }

    /**
     * Gets the current users last stroll time.
     * @return The timestamp for the stroll.
     */
    public long getcStrollTimeStamp() {
        return cStrollTimeStamp;
    }

    /**
     * Sets the stroll timestamp to the new timestamp.
     * @param strollTimeStamp The new timestamp;
     */
    public void setcStrollTimeStamp(final long strollTimeStamp) {
        cStrollTimeStamp = strollTimeStamp;
    }

    /**
     * Gets the current users last interval time.
     * @return The timestamp for the interval.
     */
    public long getcIntervalTimeStamp() {
        return cIntervalTimeStamp;
    }

    /**
     * Sets the interval timestamp to the new timestamp.
     * @param intervalTimeStamp The new timestamp.
     */
    public void setcIntervalTimeStamp(final long intervalTimeStamp) {
        cIntervalTimeStamp = intervalTimeStamp;
    }
}
