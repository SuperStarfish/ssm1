package cg.group4.exceptions;

import com.badlogic.gdx.Gdx;

/**
 * Exception caused by the unavailability of local store.
 * Can be caused by (but is not limited to):
 *   - Gdx was not initialized yet. Gdx.files.* can only be accessed after its initialization.
 *   - Incorrect write or read permissions of the local storage folder.
 *
 * @see com.badlogic.gdx.Gdx
 */
public class LocalStoreUnavailableException extends Exception {

    /**
     * Redirect exception to super class containing a default exception message.
     */
    public LocalStoreUnavailableException() {
        super(new StringBuilder().append("Local storage seems unavailable.")
                .append(System.lineSeparator())
                .append("Local storage path: ")
                .append(Gdx.files.getLocalStoragePath())
                .toString());
    }

}
