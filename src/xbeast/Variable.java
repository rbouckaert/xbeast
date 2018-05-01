package xbeast;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

import dr.inference.model.Bounds;
import dr.inference.model.VariableListener;

public interface Variable<V> extends Statistic {

    /**
     * @return the name of this variable.
     */
    default public String getVariableName() {
    	return getID();
    }

    public V getValue(int index);

    public void setValue(int index, V value);

    public V[] getValues();

    /**
     * @return the size of this variable - i.e. the length of the vector
     */
    default public int getSize() {
    	return getDimension();
    }
    
   	default public int getDimension() {
   		try {
   			return getSize();
   		} catch (StackOverflowError e) {
   			System.err.println("Programmer error: getDimension() is not implemented in class " + this.getClass().getName()+ "!");
   			throw e;
   		} 
    }
   	
    /**
     * adds a parameter listener that is notified when this parameter changes.
     *
     * @param listener the listener
     */
    void addVariableListener(VariableListener listener);
    
    Collection<VariableListener> getVariableListeners();
    
    default boolean requireRecalculation() {
    	for (VariableListener listener : getVariableListeners()) {
    		listener.variableChangedEvent((dr.inference.model.Variable) this, 0, 
    				dr.inference.model.Variable.ChangeType.ALL_VALUES_CHANGED);
    	}
    	return true;
    }

    /**
     * removes a parameter listener.
     *
     * @param listener the listener
     */
    void removeVariableListener(VariableListener listener);

    /**
     * stores the state of this parameter for subsequent restore
     */
    default void storeVariableValues() {
    	store();
    }

    default void store() {
    	try {
    		storeVariableValues();
    	} catch (StackOverflowError e) {
   			System.err.println("Programmer error: store() is not implemented in class " + this.getClass().getName()+ "!");
   			throw e;
   	  	}
    }

    /**
     * restores the stored state of this parameter
     */
    default void restoreVariableValues() {
    	restore();
    }
    
    default void restore() {
    	try {
    		restoreVariableValues();
    	} catch (StackOverflowError e) {
   			System.err.println("Programmer error: restore() is not implemented in class " + this.getClass().getName()+ "!");
   			throw e;
   	  	}
    }

    /**
     * accepts the stored state of this parameter
     */
    default void acceptVariableValues() {
    }
    

    /**
     * @return the bounds on this parameter
     */
    default Bounds<V> getBounds() {
    	return new DefaultBounds<V>(getLower(), getUpper(), getDimension());
    }
    
    default V getLower() {
    	return getBounds().getLowerLimit(0);
    }
    default V getUpper() {
    	return getBounds().getUpperLimit(0);
    }

    class DefaultBounds<V> implements Bounds<V> {

    	@SuppressWarnings("unchecked")
        public DefaultBounds(V upper, V lower, int dimension) {

        	this.uppers = (V[]) Array.newInstance(upper.getClass(), dimension);
            this.lowers = (V[]) Array.newInstance(lower.getClass(), dimension);
            for (int i = 0; i < dimension; i++) {
                uppers[i] = upper;
                lowers[i] = lower;
            }
        }

        public V getUpperLimit(int i) {
            return uppers[i];
        }

        public V getLowerLimit(int i) {
            return lowers[i];
        }

        public int getBoundsDimension() {
            return uppers.length;
        }

        public boolean isConstant() {
            return true;
        }

        private final V[] uppers, lowers;
    }   
    
    /** set(?) bounds **/ 
    default void addBounds(Bounds<V> bounds) {
    	setUpper(bounds.getUpperLimit(0));
    	setLower(bounds.getLowerLimit(0));
    }

    default void setUpper(V upper) {   	
    	try{
    		DefaultBounds<V> bounds = new DefaultBounds<V>(upper, getBounds().getLowerLimit(0), getDimension());
    		addBounds(bounds);
    	} catch (StackOverflowError e) {
    		System.err.println("Programmer error: setUpper(upper) is not implemented in class " + this.getClass().getName()+ "!");
    		throw e;
    	}
    }

    default void setLower(V lower) {    	
    	try {
    		DefaultBounds<V> bounds = new DefaultBounds<V>(getBounds().getUpperLimit(0), lower, getDimension());
    		addBounds(bounds);	
    	} catch (StackOverflowError e) {
			System.err.println("Programmer error: setLower(lower) is not implemented in class " + this.getClass().getName()+ "!");
			throw e;
	  	}
   }
    
}