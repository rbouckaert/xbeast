package xbeast;

import java.io.Serializable;

/**
 * An interface for supplying an Id = ID for a data object. 
 * Since BEAST 1 and 2 use different notation (Id and ID respectively) there are 
 * two variants provided, and only one needs to be implemented.
 * If neither g/setID or g/setId is implemented a stackoverflow rapidly follows.
 * 
 * TODO: add integration test that calls getID/setID on each object to verify
 * at least one is implemented. 
 */
public interface Identifiable extends Serializable {
    /**
     * @return the id as a string.
     */
	default String getID() {
		try {
			return getId();
		} catch (StackOverflowError e) {
			System.err.println("Programmer error: getID() is not implemented!");
			throw e;
 		}
	}
	
	default String getId() {
		return getID();
	}

	/**
     * set the id as a string.
     */
	default void setID(String ID) {
		try {
			setId(ID);
		} catch (StackOverflowError e) {
			System.err.println("Programmer error: setID() is not implemented!");
			throw e;
 		}
	}
	
	default void setId(String Id) {setID(Id);}
}
