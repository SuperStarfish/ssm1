package cg.group4.client.query;

import cg.group4.rewards.Collection;

public class CollectionWrapper extends Data {
    protected Collection cCollection;
    protected UserData cUserData;

    public CollectionWrapper(Collection collection, UserData userData) {
        cCollection = collection;
        cUserData = userData;
    }

    public Collection getcCollection() {
        return cCollection;
    }

    public void setcCollection(Collection collection) {
        cCollection = collection;
    }

    public UserData getcUserData() {
        return cUserData;
    }

    public void setcUserData(UserData userData) {
        cUserData = userData;
    }
}
