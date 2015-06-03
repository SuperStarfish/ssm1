package cg.group4.rewards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

/**
 * Stores the collection data on the local storage.
 * Reason for not using the internal storage: is read only.
 */
public class LocalCollectionStorer implements CollectionStorer {

    protected String cTag = this.getClass().getSimpleName();
    protected Collection cCollection;
    protected boolean cEncrypt;
    protected Json cJson;
    protected final String cLocalFile;

    /**
     *
     * @param collection
     */
    public LocalCollectionStorer(Collection collection) {
        cCollection = collection;
        cEncrypt = false;
        cJson = new Json();
        cJson.setOutputType(JsonWriter.OutputType.json);
        cLocalFile = "starfish.json";
    }

    @Override
    public void store() {
        if (!isLocalStorageAvailable()) {
            // rescue
        }

        // Currently, does not check for an existing file, thus just overwrites existing data.
        if (!getFile().exists()) {
            Gdx.app.log(cTag, "Storing new file at: " + Gdx.files.getLocalStoragePath());
        }
        getFile().writeString(cJson.prettyPrint(cCollection), false);

    }

    /**
     * Helper method, which checks the availability of local storage.
     * Should be checked only after the Gdx initialization has been run.
     * @return Availability of local storage
     */
    private boolean isLocalStorageAvailable() {
        return Gdx.files.isLocalStorageAvailable();
    }


    /**
     * Returns the a FileHandle based on the {#code cLocalFile}
     * @return
     */
    private FileHandle getFile() {
        return Gdx.files.local(cLocalFile);
    }
}
