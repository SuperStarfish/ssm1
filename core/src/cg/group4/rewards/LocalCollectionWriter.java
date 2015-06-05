package cg.group4.rewards;

import cg.group4.exceptions.LocalStoreUnavailableException;
import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

/**
 * Stores the collection data on the local storage.
 * Reason for not using the internal storage: is read only.
 */
public class LocalCollectionWriter implements CollectionWriter {

    /**
     * Debug tag containing the simple class name. Used for debugging purposes.
     */
    protected String cTag = this.getClass().getSimpleName();

    /**
     * Initializes local collection storer.
     * The local collection storer stores a collection object as a serialized save file.
     * Uses the java built in ObjectOutputStream for this purpose.
     */
    public LocalCollectionWriter() {
    }


    @Override
    public void store(final Collection collection) {
        serialize(collection);
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
            writeByteFile(raw, collection.getId());
        } catch (ObjectStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
                outputStream.close();
            } catch (Exception e) { }
        }
    }

    /**
     * Helper method to write raw bytes to a file.
     * @param bytes raw data to be written
     * @param id identifier used as filename
     */
    private void writeByteFile(final byte[] bytes, final String id) {
        FileHandle fileHandle = null;

        final String ext = ".save";

        try {
            fileHandle = CollectionUtil.localFileHandle(id + ext);
            fileHandle.writeBytes(bytes, false);
        } catch (LocalStoreUnavailableException e) {
            e.printStackTrace();
        }
    }

}
