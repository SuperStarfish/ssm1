package cg.group4.rewards;

import cg.group4.exceptions.LocalStoreUnavailableException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

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
        serialize(cCollection);
        Gdx.app.log(cTag, "Storing new save file at: " + Gdx.files.getLocalStoragePath() + cLocalFile);

    }

    /**
     * Serializes the collection and stores this data as a local save file.
     * @param collection collection object to be serialized
     */
    private void serialize(final Collection collection) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = null;

        try {
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(collection);
            outputStream.flush();
            byte[] raw = byteArrayOutputStream.toByteArray();
            writeByteFile(raw);
        } catch (ObjectStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
                outputStream.close();
            }
            catch (Exception e) {}
        }
    }

    private void writeByteFile(byte[] bytes) {
        FileHandle fileHandle = null;
        try {
            fileHandle = CollectionUtil.localFileHandle(cLocalFile);
            fileHandle.writeBytes(bytes, false);
        } catch (LocalStoreUnavailableException e) {
            e.printStackTrace();
        }
    }

}
