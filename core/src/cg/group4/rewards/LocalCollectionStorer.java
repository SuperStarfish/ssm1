package cg.group4.rewards;

import cg.group4.exceptions.LocalStoreUnavailableException;
import com.badlogic.gdx.Gdx;

import java.io.*;

/**
 * Stores the collection data on the local storage.
 * Reason for not using the internal storage: is read only.
 */
public class LocalCollectionStorer implements CollectionStorer {

    /**
     * Debug tag containing the simple class name. Used for debugging purposes.
     */
    protected String cTag = this.getClass().getSimpleName();

    /**
     * Collection to be serialized.
     */
    protected Collection cCollection;

    /**
     * Path to the file in which the serialized Collection object will be stored.
     */
    protected final String cLocalFile;

    /**
     * Initializes local collection storer.
     * The local collection storer stores a collection object as a serialized save file.
     * Uses the java built in ObjectOutputStream for this purpose.
     * @param collection collection to be serialized and store to a save file
     */
    public LocalCollectionStorer(final Collection collection) {
        cCollection = collection;
        cLocalFile = "starfish.save";
    }

    @Override
    public void store() {

        try {
            serialize(cCollection, CollectionUtil.getLocalFile(cLocalFile));
            Gdx.app.log(cTag, "Storing new save file at: " + Gdx.files.getLocalStoragePath() + cLocalFile);
        } catch (LocalStoreUnavailableException e) {
            e.printStackTrace();
        }

    }

    /**
     * Serializes the collection and stores this data as a local save file.
     * @param collection collection object to be serialized
     * @param fileStorage path to local save file.
     */
    private void serialize(final Collection collection, final String fileStorage) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream outputStream;

        try {
            fileOutputStream = new FileOutputStream(new File(fileStorage));
            outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(collection);

            outputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ObjectStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
