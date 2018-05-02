/*
* File Operator.java
*
* Copyright (C) 2011 BEAST2 Core Team
*
* This file is part of BEAST2.
* See the NOTICE file distributed with this work for additional
* information regarding copyright ownership and licensing.
*
* BEAST is free software; you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
*  BEAST is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with BEAST; if not, write to the
* Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
* Boston, MA  02110-1301  USA
*/
package beast.core;



import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
//import org.json.JSONWriter;
import org.json.JSONStringer;

import beast.core.Input.Validate;
import beast.core.util.Evaluator;

@Description("Proposes a move in state space.")
public abstract class Operator extends BEASTObject implements xbeast.Operator {
    final public Input<Double> m_pWeight = new Input<>("weight", "weight with which this operator is selected", Validate.REQUIRED);

    private final String STANDARD_OPERATOR_PACKAGE = "beast.evolution.operators";

    /**
     * the schedule used for auto optimisation *
     */
    OperatorSchedule operatorSchedule;

    @Override
    public void setOperatorSchedule(final Object operatorSchedule) {
        this.operatorSchedule = (OperatorSchedule) operatorSchedule;
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
     * @param evaluator An evaluator object that can be use to repetitively
     *                  used to evaluate the distribution returned by getEvaluatorDistribution().
     * @return log of Hastings Ratio, or Double.NEGATIVE_INFINITY if proposal
     * should not be accepted (because the proposal is invalid) or
     * Double.POSITIVE_INFINITY if the proposal should always be accepted
     * (for Gibbs operators).
     */
    public double proposal(final Evaluator evaluator) {
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
    abstract public double proposal();

    /**
     * @return the relative weight which determines the probability this proposal is chosen
     * from among all the available proposals
     */
    @Override
    public double getWeight() {
        return m_pWeight.get();
    }
    
    @Override
    public void setWeight(double weight) {
    	m_pWeight.setValue(weight, this);    	
    }

    public String getName() {

        String className = this.getClass().getName();
        if (className.startsWith(STANDARD_OPERATOR_PACKAGE)) {
            className = className.substring(STANDARD_OPERATOR_PACKAGE.length() + 1);
        }
        return className + "(" + (getID() != null ? getID() : "") + ")";
    }

    /**
     * keep statistics of how often this operator was used, accepted or rejected *
     */
    protected long m_nNrRejected = 0;
    protected long m_nNrAccepted = 0;
    protected long m_nNrRejectedForCorrection = 0;
    protected long m_nNrAcceptedForCorrection = 0;

    private final boolean detailedRejection = false;
    // rejected because likelihood is infinite
    protected long m_nNrRejectedInvalid = 0;
    // rejected because operator failed (sub-group of above)
    protected long m_nNrRejectedOperator = 0;

    @Override
    public void accept(double deviation) {
        m_nNrAccepted++;
        if (operatorSchedule.autoOptimizeDelayCount >= operatorSchedule.autoOptimizeDelay) {
            m_nNrAcceptedForCorrection++;
        }
    }

    @Override
    public void reject() {
        reject(0); // silly hack
    }
    
    @Override
    public long getAcceptCount() {
    	return m_nNrAccepted;
    }

    @Override
    public void setAcceptCount(long acceptCount) {
    	m_nNrAccepted = acceptCount;
    }

    @Override
    public long getRejectCount() {
    	return m_nNrRejected;
    }
    
    @Override
    public void setRejectCount(long rejectCount) {
    	m_nNrRejected = rejectCount;    	
    }
    
    // 0 like finite  -1 like -inf -2 operator failed
    public void reject(final int reason) {
        m_nNrRejected++;
        if (reason < 0) {
            ++m_nNrRejectedInvalid;
            if (reason == -2) {
                ++m_nNrRejectedOperator;
            }
        }
        if (operatorSchedule.autoOptimizeDelayCount >= operatorSchedule.autoOptimizeDelay) {
            m_nNrRejectedForCorrection++;
        }
    }

    @Override
    public void reset() {
    	m_nNrAccepted = 0;
    	m_nNrAcceptedForCorrection = 0;
    	m_nNrRejected = 0;
    	m_nNrRejectedForCorrection = 0;
    	m_nNrRejectedInvalid = 0;
    	m_nNrRejectedOperator = 0;
    }
    
    @Override
    public long getCount() {
    	return m_nNrAccepted + m_nNrRejected;
    }

    @Override
    public void setAcceptCountForCorrection(long acceptCount) {
    	m_nNrAcceptedForCorrection = acceptCount;
    }
    @Override
    public long getAcceptCountForCorrection() {
    	return m_nNrAcceptedForCorrection;
    }
    @Override
    public long getRejectCountInvalid() {
    	return m_nNrRejectedInvalid;
    }
    @Override
    public long getRejectCountOperator() {
    	return m_nNrRejectedOperator;
    }
    @Override
    public void setRejectCountForCorrection(long rejectCount) {
    	m_nNrRejectedForCorrection = rejectCount;
    }
    @Override
    public long getRejectCountForCorrection() {
    	return m_nNrRejectedForCorrection;
    }
    @Override
    public void setRejectCountInvalid(long rejectCount) {
    	m_nNrRejectedInvalid = rejectCount;
    }
    @Override
    public void setRejectCountOperator(long rejectCount) {
    	m_nNrRejectedOperator = rejectCount;
    }

    /**
     * @param logAlpha difference in posterior between previous state & proposed state + hasting ratio
     * @return change of value of a parameter for MCMC chain optimisation
     */
    protected double calcDelta(final double logAlpha) {
        return operatorSchedule.calcDelta(this, logAlpha);
    } // calcDelta

    /**
     * @return target for automatic operator optimisation
     */
    public double getTargetAcceptanceProbability() {
        return 0.234;
    }


    /**
     * return directions on how to set operator parameters, if any *
     *
     * @return
     */
    public String getPerformanceSuggestion() {
        return "";
    }

    /**
     * return list of state nodes that this operator operates on.
     * state nodes that are input to the operator but are never changed
     * in a proposal should not be listed
     */
    public List<xbeast.StateNode> listStateNodes() {
        // pick up all inputs that are stateNodes that are estimated
        final List<xbeast.StateNode> list = new ArrayList<>();
        for (BEASTInterface o : listActiveBEASTObjects()) {
            if (o instanceof StateNode) {
                final StateNode stateNode = (StateNode) o;
                if (stateNode.isEstimatedInput.get()) {
                    list.add(stateNode);
                }
            }
        }
        return list;
    }

    @Override
	public String toString() {
        return OperatorSchedule.prettyPrintOperator(this, 70, 10, 4, 0.0, detailedRejection);
    }



} // class Operator
