package xbeast;


//The state represents the current point in the state space, and  
//maintains values of a set of StateNodes, such as parameters and trees.  
//Furthermore, the state manages which parts of the model need to be stored/restored  
//and notified that recalculation is appropriate.
public interface State {
	
	StateNode getStateNode(int index);

    /**
     * Return StateNode that can be changed, but later restored
     * if necessary. If there is no copy stored already, a copy is
     * made first, and the StateNode is marked as being dirty.
     * <p/>
     * NB This should only be called from an Operator that wants to
     * change the particular StateNode through the Input.get(Operator)
     * method on the input associated with this StateNode.
     */
    default xbeast.StateNode getEditableStateNode(int _id, Object operator) {
        return null;
    }

    default public void checkCalculationNodesDirtiness() {
    	throw new RuntimeException("Not implemented yet");
    }
}
