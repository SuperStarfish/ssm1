package cg.group4.rewards;

import cg.group4.exceptions.LocalStoreUnavailableException;
import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;

/**
 * Reads the collection data from the local storage.
 * Reason for not using the internal storage: is read only.
 */
public class LocalCollectionReader implements CollectionReader {

    /**
     * Debug tag containing the simple class name. Used for debugging purposes.
     */
    protected String cTag = this.getClass().getSimpleName();

    /**
     * Initialization of the local collection reader.
     */
    public LocalCollectionReader() {
    }


    @Override
    public Collection read(final String id) {
        final Collection res = deserialize(id);
        return res;
    }

    /**
     * Uses java's built in object input stream to deserialize an earlier serialized local collection save file.
     * @param id collection id (used for uniqueness of storage).
     *           Note: the id is not validated here!
     * @return Collection from file
     */
    private Collection deserialize(final String id) {
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream inputStream = null;

        Collection res = null;

        try {
            final byte[] raw = readByteFile(id);
            byteArrayInputStream = new ByteArrayInputStream(raw);
            inputStream = new ObjectInputStream(byteArrayInputStream);
            res = (Collection) inputStream.readObject();

        } catch (ObjectStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayInputStream.close();
                inputStream.close();
            } catch (Exception e) { }
        }

        return res;
    }

    /**
     * Helper method to write raw bytes to a file.
     * @param id Unique id used to determine the filename
     * @return raw data read from cLocalFile
     */
    private byte[] readByteFile(final String id) {
        FileHandle fileHandle;
        byte[] res = null;

        final String ext = ".save";

        try {
            fileHandle = CollectionUtil.localFileHandle(id + ext);
            res = fileHandle.readBytes();
        } catch (LocalStoreUnavailableException e) {
            e.printStackTrace();
        }

        return res;
    }

}
