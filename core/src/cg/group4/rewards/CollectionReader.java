package cg.group4.rewards;

/**
 * Reads the stored data of the items of a collection from a file.
 */
public interface CollectionReader {

    /**
     * Reads a stored collection into a Collection object.
     * @return The created collection object.
     */
    Collection read();
}

