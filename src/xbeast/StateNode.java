package xbeast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.w3c.dom.Node;


/**
 * A StatNode represents a node of the state. Concrete classes include Parameters and Trees.
 * StateNodes differ from CalculationNodes in that they
 * 1. Do not calculate anything, with the exception of initialisation time
 * 2. can be changed by Operators
 *
 * @author Alexei Drummond
 */

public interface StateNode extends Identifiable {

	/**
	 * the index is used to identify this stateNode in the State 
	 * and all its potential descendants 
	 */
	default void setIndex(int i) {}
	default int getIndex() {return -1;}
	
	/** 
	 * associate a State with this StateNode
	 */
	default void setState(State state) {}
	default State getState() {return null;}
	
	
    /**
     * @return this StateNode if it is not in the State.
     *         If it is in the State, return the version that is currently valid
     *         (i.e. not the stored one).
     */
    default public StateNode getCurrent() {
        if (getState() == null) {
            return this;
        }
        return getState().getStateNode(getIndex());
    }

    /**
     * stores a state node in XML format, to be restored by fromXML() *
     */
    default public String toXML() {
        return "<statenode id='" + normalise(getId()) + "'>" +
                normalise(toString()) +
                "</statenode>\n";
    }

    /** ensure XML identifiers get proper escape sequences **/
    default String normalise(String str) {
    	if (str == null) {
    		return null;
    	}
    	str = str.replaceAll("&", "&amp;");    	
    	str = str.replaceAll("'", "&apos;");
    	str = str.replaceAll("\"", "&quot;");
    	str = str.replaceAll("<", "&lt;");
    	str = str.replaceAll(">", "&gt;");
    	return str;
    }

    /**
     * for restoring a state that was stored using toXML() above
     * from a DOM Node. *
     */
    default public void fromXML(Node node) {
    	throw new RuntimeException("Not implemented yet");
    }

    /**
     * As assignFrom, but only those parts are assigned that
     * are variable, for instance for parameters bounds and dimension
     * do not need to be copied.
     */
    default void assignFromFragile(StateNode other) {
    }
    
    /**
     * Store internal calculations. Called before a calculation node
     * is asked to perform any calculations, but after some part of the
     * state has changed through a operator proposal.
     * <p/>
     * This is not meant to be used to calculate anything, just store
     * intermediate results of calculations. Input values should not
     * be accessed because some StateNodes may have been changed.
     */
	public void store();

    /**
     * Restore internal calculations
     * <p/>
     * This is called when a proposal is rejected
     */
	public void restore();

    /**
     * @return a deep copy of this node in the state.
     *         This will generally be called only for stochastic nodes.
     */
    default public StateNode copy() {
    	StateNode obj = null;
        try {
        	State state = this.getState();
        	setState(null);
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
            obj = (StateNode) in.readObject();
            
            obj.setIndex(getIndex());
            obj.setState(state);
            setState(state);
        } catch(IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (StateNode) obj;    
    }

    /**
     * mark every internal element of a StateNode as isDirty.
     * So both the global flag for this StateNode (somethingIsDirty) should be set as
     * well as all the local flags.
     *
     * @param isDirty
     */
    default void setEverythingDirty(final boolean isDirty) {
    	throw new RuntimeException("Not implemented yet");
    }


}
