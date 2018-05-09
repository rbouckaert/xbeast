package xbeast;

public interface Likelihood extends Model {

    /**
     * @return the normalised probability (density) for this distribution.
     *         Note that some efficiency can be gained by testing whether the
     *         Distribution is dirty, and if not, call getCurrentLogP() instead
     *         of recalculating.
     */
	default double calculateLogP() {
		try {
			return getLogLikelihood();
		} catch (StackOverflowError e) {
			System.err.println("Programmer error: calculateLogP() is not implemented in class " + this.getClass().getName()+ "!");
			throw e;
		}
	}
	
    /**
     * get result from last known calculation, useful for logging
     *
     * @return log probability
     */
	default double getCurrentLogP() {
		try {
			return getLogLikelihood();
		} catch (StackOverflowError e) {
			System.err.println("Programmer error: getCurrentLogP() is not implemented in class " + this.getClass().getName()+ "!");
			throw e;
		}
	}

    /**
     * Get the log likelihood.
     *
     * @return the log likelihood.
     */
    default double getLogLikelihood() {
    	if (isDirtyCalculation()) {
    		return calculateLogP();
    	} else {
    		return getCurrentLogP();
    	}
    }

    /**
     * Forces a complete recalculation of the likelihood next time getLikelihood is called
     */
    void makeDirty();
    
    default boolean isDirtyCalculation() {
    	try {
    		return getLikelihoodKnown();
		} catch (StackOverflowError e) {
			System.err.println("Programmer error: isDirtyCalculation() is not implemented in class " + this.getClass().getName()+ "!");
			throw e;
 		}
    }

	default boolean getLikelihoodKnown() {
		return isDirtyCalculation();
	}
//    /**
//     * @return A detailed name of likelihood for debugging.
//     */
//    String prettyName();
//
//    /**
//     * Get the set of sub-component likelihoods that this likelihood uses
//     *
//     * @return
//     */
//    Set<Likelihood> getLikelihoodSet();

    /**
     * @return is the likelihood used in the MCMC?
     */
    boolean isUsed();

    void setUsed();

    /**
//     * @return true if this likelihood should be evaluated early (if for example it may return a zero likelihood
//     * and could terminate the evaluation early or is a required conditioning for another likelihood.
//     */
//    boolean evaluateEarly();

}
