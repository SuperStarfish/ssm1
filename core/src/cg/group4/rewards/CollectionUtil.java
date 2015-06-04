package cg.group4.rewards;

import cg.group4.exceptions.LocalStoreUnavailableException;
import com.badlogic.gdx.Gdx;

/**
 * Utility class for collection.
 */
public final class CollectionUtil {

    /**
     * Helper method, which checks the availability of local storage.
     * Should be checked only after the Gdx initialization has been run.
     * @return Availability of local storage
     */
    private static boolean isLocalStorageAvailable() {
        return Gdx.files.isLocalStorageAvailable();
    }


    /**
     * Returns the a String based on the {#code cLocalFile}
     * @param localFile the file requested from the filesystem on the local file path
     * @return String representation of the local storage location based on the local storage path from Libgdx.
     * @see {@link com.badlogic.gdx.Gdx}
     */
    public static String localFile(final String localFile) throws LocalStoreUnavailableException {
        if (!isLocalStorageAvailable()) {
            throw new LocalStoreUnavailableException();
        }

        return Gdx.files.local(localFile).toString();
    }
}
