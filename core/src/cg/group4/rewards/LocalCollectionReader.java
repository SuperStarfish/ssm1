package cg.group4.rewards;

import cg.group4.exceptions.LocalStoreUnavailableException;
import com.badlogic.gdx.Gdx;

import java.io.*;

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

    @Override
    public Collection read() {

        Collection res = null;

        try {
            res = deserialize(CollectionUtil.localFile(cLocalFile));
            Gdx.app.log(cTag, "Reading save file from: " + Gdx.files.getLocalStoragePath() + cLocalFile);
        } catch (LocalStoreUnavailableException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Uses java's built in object input stream to deserialize an earlier serialized local collection save file.
     * @see {@link cg.group4.rewards.LocalCollectionStorer }
     * @param fileStorage save file from which the Collection object will be serialized.
     * @return Collection from file
     */
    private Collection deserialize(final String fileStorage) {
        FileInputStream fileInputStream;
        ObjectInputStream inputStream;

        Collection res = null;

        try {
            fileInputStream = new FileInputStream(new File(fileStorage));
            inputStream = new ObjectInputStream(fileInputStream);
            res = (Collection) inputStream.readObject();

            fileInputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ObjectStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return res;
    }

}
