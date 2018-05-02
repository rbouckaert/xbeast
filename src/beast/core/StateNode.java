package beast.core;

import java.io.PrintStream;

import org.w3c.dom.Node;

import beast.core.Operator;

/**
 * This class represents a node of the state. Concrete classes include Parameters and Trees.
 * StateNodes differ from CalculationNodes in that they
 * 1. Do not calculate anything, with the exception of initialisation time
 * 2. can be changed by Operators
 *
 * @author Alexei Drummond
 */
@Description("A node that can be part of the state.")
public abstract class StateNode extends CalculationNode implements Loggable, Cloneable, Function, xbeast.StateNode {
    /**
     * Flag to indicate the StateNode is not constant.
     * This is particularly useful for Beauti *
     */
    final public Input<Boolean> isEstimatedInput = new Input<>("estimate", "whether to estimate this item or keep constant to its initial value", true);
    
    
    @Override
    public void setIndex(int i) {
    	this.index = i;
    }
    
    @Override
    public void setState(xbeast.State state) {
    	this.state = state;
    }


    /**
     * @param operator explain here why operator is useful
     * @return StateNode for an operation to do its magic on.
     *         The State will make a copy first, if there is not already
     *         one available.
     */
    public StateNode getCurrentEditable(final Object operator) {
        startEditing(operator);
        return this;
    }

    /**
     * Getting/setting global dirtiness state for this StateNode.
     * Every StateNode has a flag (somethingIsDirty) that represents whether anything
     * in the state has changed. StateNode implementations like Parameters and Trees
     * have their own internal flag to represent which part of a StateNode (e.g.
     * an element in an array, or a node in a tree) has changed.
     * *
     */
    public boolean somethingIsDirty() {
        return this.hasStartedEditing;
    }

    public void setSomethingIsDirty(final boolean isDirty) {
        this.hasStartedEditing = isDirty;
    }

    /**
     * mark every internal element of a StateNode as isDirty.
     * So both the global flag for this StateNode (somethingIsDirty) should be set as
     * well as all the local flags.
     *
     * @param isDirty
     */
    abstract public void setEverythingDirty(final boolean isDirty);

    /**
     * @return a deep copy of this node in the state.
     *         This will generally be called only for stochastic nodes.
     */
    public abstract StateNode copy();

    /**
     * other := this
     * Assign all values of this to other
     * NB: Should only be used for initialisation!
     */
    public abstract void assignTo(StateNode other);

    /**
     * this := other
     * Assign all values of other to this
     * NB: Should only be used for initialisation!
     */
    public abstract void assignFrom(StateNode other);

    /**
     * As assignFrom, but without copying the ID
     * NB: Should only be used for initialisation!
     */
    public void assignFromWithoutID(StateNode other) {
        final String id = getId();
        assignFrom(other);
        setId(id);
    }

    /**
     * As assignFrom, but only those parts are assigned that
     * are variable, for instance for parameters bounds and dimension
     * do not need to be copied.
     */
    public abstract void assignFromFragile(StateNode other);

    /**
     * for storing a state *
     */
    final public void toXML(PrintStream out) {
        out.print("<statenode id='" + normalise(getId()) + "'>");
        out.print(normalise(toString()));
        out.print("</statenode>\n");
    }




//    /**
//     * @return true if this node is acting as a random variable, false if this node is fixed and effectively data.
//     */
//    public final boolean isStochastic() {
//        return this.isStochastic;
//    }
//
//    /**
//     * @param isStochastic true if this need should be treated as stochastic, false if this node should be fixed
//     *                     and treated as data
//     */
//    final void setStochastic(boolean isStochastic) {
//        this.isStochastic = isStochastic;
//    }
//
//    boolean isStochastic = true;

    /**
     * Scale StateNode with amount scale and
     *
     * @param scale scaling factor
     * @return the number of degrees of freedom used in this operation. This number varies
     *         for the different types of StateNodes. For example, for real
     *         valued n-dimensional parameters, it is n, for a tree it is the
     *         number of internal nodes being scaled.
     * @throws IllegalArgumentException when StateNode become not valid, e.g. has
     *                   values outside bounds or negative branch lengths.
     */
    abstract public int scale(double scale);

    /**
     * Pointer to state, null if not part of a State.
     */
    protected xbeast.State state = null;

    public xbeast.State getState() {
        return state;
    }

    /**
     * flag to indicate some value has changed after operation is performed on state
     * For multidimensional parameters, there is an internal flag to indicate which
     * dimension is dirty
     */
    protected boolean hasStartedEditing = false;

    /**
     * The index of the parameter for identifying this StateNode
     * in the State.
     */
    public int index = -1;

    public int getIndex() {
        return index;
    }

    /**
     * should be called before an Operator proposes a new State *
     *
     * @param operator
     */
    public void startEditing(final Object operator) {
        assert (isCalledFromOperator(4));
        if (hasStartedEditing) {
            // we are already editing
            return;
        }
        hasStartedEditing = true;
        // notify the state

        if (state != null) state.getEditableStateNode(this.index, operator);

        store();
    }

    private boolean isCalledFromOperator(int level) {
        // TODO: sun.reflect.Reflection.getCallerClass is not available in JDK7
        // and alternative methods are really slow according to
        // http://stackoverflow.com/questions/421280/in-java-how-do-i-find-the-caller-of-a-method-using-stacktrace-or-reflection

//    	Class<?> caller = sun.reflect.Reflection.getCallerClass(level);
//    	while (caller != null) {
//    		if (Operator.class.isAssignableFrom(caller)) {
//    			return true;
//    		}
//    		caller = sun.reflect.Reflection.getCallerClass(++level);
//    	}
//    	return false;

        return true;
    }


} // class StateNode
