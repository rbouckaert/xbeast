package xbeast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import beast.core.Description;
import beast.core.Distribution;
import beast.core.util.Evaluator;

@Description("Proposes a move in state space.")
public interface Operator extends Identifiable {
	
//    @Override
//    default String getID() {
//    	return getClass().getName() + hashCode();
//    }

    /**
     * Implement this for proposing a new State.
     * The proposal is responsible for keeping the State valid,
     * and if the State becomes invalid (e.g. a parameter goes out
     * of its range) Double.NEGATIVE_INFINITY should be returned.
     * <p>
     * If the operator is a Gibbs operator, hence the proposal should
     * always be accepted, the method should return Double.POSITIVE_INFINITY.
     *
     * @param evaluator An evaluator object that can be use to repetitively
     *                  used to evaluate the distribution returned by getEvaluatorDistribution().
     * @return log of Hastings Ratio, or Double.NEGATIVE_INFINITY if proposal
     * should not be accepted (because the proposal is invalid) or
     * Double.POSITIVE_INFINITY if the proposal should always be accepted
     * (for Gibbs operators).
     */
    default public double proposal(final Evaluator evaluator) {
        return proposal();
    }

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
    
    // 0 like finite  -1 like -inf -2 operator failed
    default public void reject(final int reason) {
    	reject();
    }
    
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
    
    default void setAcceptCountForCorrection(long acceptCount) {}
    default long getAcceptCountForCorrection() {return 0;}
    default long getRejectCountInvalid() {return 0;}
    default long getRejectCountOperator() {return 0;}
    default void setRejectCountForCorrection(long rejectCount) {}
    default long getRejectCountForCorrection() {return 0;}
	default void setRejectCountOperator(long rejectCount) {}
	default void setRejectCountInvalid(long rejectCount) {}

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
    
    /**
     * indicates that the state needs to be initialised so that
     * BEASTObjects can be identified that need updating. This
     * almost always needs to happen, except for cases where the
     * operator already initialised the state, e.g. for delayed
     * acceptance operators.
     */
    default public boolean requiresStateInitialisation() {
        return true;
    }

    /**
     * @return value changed through automatic operator optimisation
     */
    default public double getCoercableParameterValue() {
        return Double.NaN;
    }

    /**
     * set value that changed through automatic operator optimisation
     *
     * @param value
     */
    default public void setCoercableParameterValue(final double value) {
    }

    /**
     * called after every invocation of this operator to see whether
     * a parameter can be optimised for better acceptance hence faster
     * mixing
     *
     * @param logAlpha difference in posterior between previous state & proposed state + hasting ratio
     */
    default public void optimize(final double logAlpha) {
        // must be overridden by operator implementation to have an effect
    }


    /**
     * Store to state file, so on resume the parameter tuning is restored.
     * By default, it stores information in JSON for example
     * <p>
     * {"id":"kappaScaler", "p":0.5, "accept":39, "reject":35, "acceptFC":0, "rejectFC":0}
     * <p>
     * Meta-operators (operators that have one or more operators as inputs)
     * need to override this method to store the tuning information associated
     * with their sub-operators by generating nested JSON, for example
     * <p>
     * {"id":"metaOperator", "p":0.5, "accept":396, "reject":355, "acceptFC":50, "rejectFC":45,
     * operators [
     * {"id":"kappaScaler1", "p":0.5, "accept":39, "reject":35, "acceptFC":0, "rejectFC":0}
     * {"id":"kappaScaler2", "p":0.5, "accept":39, "reject":35, "acceptFC":0, "rejectFC":0}
     * ]
     * }
     * *
     */
    default public void storeToFile(final PrintWriter out) {
    	try {
	        JSONStringer json = new JSONStringer();
	        json.object();
	
	        if (getID()==null)
	           setID("unknown");
	
	        json.key("id").value(getID());
	
	        double p = getCoercableParameterValue();
	        if (Double.isNaN(p)) {
	            json.key("p").value("NaN");
	        } else if (Double.isInfinite(p)) {
	        	if (p > 0) {
	        		json.key("p").value("Infinity");
	        	} else {
	        		json.key("p").value("-Infinity");
	        	}
	        } else {
	            json.key("p").value(p);
	        }
	        json.key("accept").value(getAcceptCount());
	        json.key("reject").value(getRejectCount());
	        json.key("acceptFC").value(getAcceptCountForCorrection());
	        json.key("rejectFC").value(getRejectCountForCorrection());
	        json.key("rejectIv").value(getRejectCountInvalid());
	        json.key("rejectOp").value(getRejectCountOperator());
	        json.endObject();
	        out.print(json.toString());
    	} catch (JSONException e) {
    		// failed to log operator in state file
    		// report and continue
    		e.printStackTrace();
    	}
    }

    /**
     * Restore tuning information from file
     * Override this method for meta-operators (see also storeToFile).
     */
    default public void restoreFromFile(JSONObject o) {
    	try {
	        if (!Double.isNaN(o.getDouble("p"))) {
	            setCoercableParameterValue(o.getDouble("p"));
	        }
	        setAcceptCount(o.getInt("accept"));
	        setRejectCount(o.getInt("reject"));
	        setAcceptCountForCorrection(o.getInt("acceptFC"));
	        setRejectCountForCorrection(o.getInt("rejectFC"));
	
	        setRejectCountInvalid(o.has("rejectIv") ? o.getInt("rejectIv") : 0);
	        setRejectCountOperator(o.has("rejectOp") ? o.getInt("rejectOp") : 0);
    	} catch (JSONException e) {
    		// failed to restore from state file
    		// report and continue
    		e.printStackTrace();
    	}
    }

    default public List<xbeast.StateNode> listStateNodes() {
        return new ArrayList<>();
    }

    default public void setOperatorSchedule(Object o) {}
    
    /**
     * Implement this for proposing new states based on evaluations of
     * a distribution. By default it returns null but can be overridden
     * to implement more complex proposals.
     *
     * @return a distribution or null if not required
     */
    default public Distribution getEvaluatorDistribution() {
        return null;
    }
}
