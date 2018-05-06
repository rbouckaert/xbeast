package xbeast;

import java.io.Serializable;

import dr.inference.model.ModelListener;
import dr.inference.model.Variable;

public interface Model extends Identifiable, Serializable {

//	/**
//	 * Adds a listener that is notified when the this model changes.
//	 */
//	void addModelListener(ModelListener listener);
//
//    /**
//     * Remove a listener previously addeed by addModelListener
//     * @param listener
//     */
//    void removeModelListener(ModelListener listener);
//
//	/**
//	 * This function should be called to store the state of the
//	 * entire model. This makes the model state invalid until either
//	 * an acceptModelState or restoreModelState is called.
//	 */
//	void storeModelState();
//
//	/**
//	 * This function should be called to restore the state of the entire model.
//	 */
//	void restoreModelState();

	/**
	 * This function should be called to accept the state of the entire model
	 */
	default void acceptModelState() {
		accept();
	}
	default void accept() {
		try {
		acceptModelState();
		} catch (StackOverflowError e) {
			System.err.println("Programmer error: accept() is not implemented in class " + this.getClass().getName()+ "!");
			throw e;
 		}
	}
	
//	/**
//	 * @return whether this model is in a valid state
//	 */
//	boolean isValidState();
//
//	/**
//	 * @return the total number of sub-models
//	 */
//	int getModelCount();
//
//	/**
//	 * @return the ith sub-model
//	 */
//	Model getModel(int i);
//
//	/**
//	 * @return the total number of variable in this model
//	 */
//	int getVariableCount();
//
//	/**
//	 * @return the ith variable
//	 */
//	Variable getVariable(int i);
//
//	/**
//	 * @return the variable of the component with a given name
//	 */
//	//Parameter getParameter(String name);
//
//	/**
//	 * @return the name of this model
//	 */
//	String getModelName();
//
//    /**
//     * is the model being listened to by another or by a likelihood?
//     * @return
//     */
//    boolean isUsed();

}
