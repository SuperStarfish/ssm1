package cg.group4.rewards;

/**
 * Stores the data of the items of a collection in a file.
 */
public interface CollectionWriter {

    /**
     * Stores a Collection object into another object or file.
     * @param collection collection to write.
     */
    void store(Collection collection);
}
