package cg.group4.rewards;

import cg.group4.exceptions.LocalStoreUnavailableException;
import com.badlogic.gdx.Gdx;
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
     * Path to the file which contains the collection data to be deserialized.
     */
    protected final String cLocalFile;

    /**
     * Initialization of the local collection reader.
     */
    public LocalCollectionReader() {
        cLocalFile = "starfish.save";
    }

    /**
     * Initialization of the local collection reader.
     * @param saveFileName name of the file from which the collection game save will be read.
     */
    public LocalCollectionReader(final String saveFileName) {
        cLocalFile = saveFileName;
    }

    @Override
    public Collection read() {

        Collection res = null;
        res = deserialize();
        Gdx.app.log(cTag, "Reading save file from: " + Gdx.files.getLocalStoragePath() + cLocalFile);
        return res;
    }

    /**
     * Uses java's built in object input stream to deserialize an earlier serialized local collection save file.
     * @see {@link cg.group4.rewards.LocalCollectionStorer }
     * @return Collection from file
     */
    private Collection deserialize() {
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream inputStream = null;

        Collection res = null;

        try {
            final byte[] raw = readByteFile();
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
     * @return raw data read from cLocalFile
     */
    private byte[] readByteFile() {
        FileHandle fileHandle;
        byte[] res = null;

        try {
            fileHandle = CollectionUtil.localFileHandle(cLocalFile);
            res = fileHandle.readBytes();
        } catch (LocalStoreUnavailableException e) {
            e.printStackTrace();
        }

        return res;
    }

}
