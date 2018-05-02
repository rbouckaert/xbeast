package xbeast;

import beast.core.Description;

@Description("Proposes a move in state space.")
public interface Operator extends Identifiable {
	
	 /**
     * Implement this for proposing a new State.
     * The proposal is responsible for keeping the State valid,
     * and if the State becomes invalid (e.g. a parameter goes out
     * of its range) Double.NEGATIVE_INFINITY should be returned.
     * <p>
     * If the operator is a Gibbs operator, hence the proposal should
     * always be accepted, the method should return Double.POSITIVE_INFINITY.
     *
     * @return log of Hastings Ratio, or Double.NEGATIVE_INFINITY if proposal
     * should not be accepted (because the proposal is invalid) or
     * Double.POSITIVE_INFINITY if the proposal should always be accepted
     * (for Gibbs operators).
     */
    default double proposal() {
		try {
			return operate();
		} catch (StackOverflowError e) {
			System.err.println("Programmer error: operate() is not implemented in class " + this.getClass().getName()+ "!");
			throw e;
 		}
    }

    default double operate() {
    	return proposal();
    }

    /**
     * Called to tell operator that operation was accepted
     *
     * @param deviation the log ratio accepted on
     */
    void accept(double deviation);

    default void accept() {
    	accept(0);
    }

    /**
     * Called to tell operator that operation was rejected
     */
    void reject();

    /**
     * Reset operator acceptance records.
     */
    void reset();

    /**
     * @return the total number of operations since last call to reset().
     */
    long getCount();

    /**
     * @return the number of acceptances since last call to reset().
     */
    long getAcceptCount();

    /**
     * Set the number of acceptances since last call to reset(). This is used
     * to restore the state of the operator
     *
     * @param acceptCount number of acceptances
     */
    void setAcceptCount(long acceptCount);

    /**
     * @return the number of rejections since last call to reset().
     */
    long getRejectCount();

    /**
     * Set the number of rejections since last call to reset(). This is used
     * to restore the state of the operator
     *
     * @param rejectCount number of rejections
     */
    void setRejectCount(long rejectCount);

    /**
     * @return the mean deviation in log posterior per accepted operations.
     */
    default double getMeanDeviation() {
    	return 0.0;
    }

    default double getSumDeviation() {
    	return 0.0;
    }

    //double getSpan(boolean reset);

    // void setSumDeviation(double sumDeviation);

    /**
     * @return the optimal acceptance probability
     */
    double getTargetAcceptanceProbability();

    /**
     * @return the minimum acceptable acceptance probability
     */
    default double getMinimumAcceptanceLevel() {
    	return 0.0;
    }

    /**
     * @return the maximum acceptable acceptance probability
     */
    default double getMaximumAcceptanceLevel() {
    	return 1.0;
    }

    /**
     * @return the minimum good acceptance probability
     */
    default double getMinimumGoodAcceptanceLevel() {
    	return 0.0;
    }

    /**
     * @return the maximum good acceptance probability
     */
    default  double getMaximumGoodAcceptanceLevel() {
    	return 1.0;
    }

    /**
     * @return a short descriptive message of the performance of this operator.
     */
    String getPerformanceSuggestion();

    /**
     * @return the relative weight of this operator.
     */
    double getWeight();

    /**
     * sets the weight of this operator. The weight
     * determines the proportion of time spent using
     * this operator. This is relative to a 'standard'
     * operator weight of 1.
     *
     * @param weight the relative weight of this parameter - should be positive.
     */
    void setWeight(double weight);

    /**
     * @return the name of this operator
     */
    default String getName() {
		try {
	    	return getOperatorName();
		} catch (StackOverflowError e) {
			System.err.println("Programmer error: getName() is not implemented in class " + this.getClass().getName()+ "!");
			throw e;
 		}
    }

    default String getOperatorName() {
    	return getName();
    }
    
}
