package xbeast;

/**
 * A StatNode represents a node of the state. Concrete classes include Parameters and Trees.
 * StateNodes differ from CalculationNodes in that they
 * 1. Do not calculate anything, with the exception of initialisation time
 * 2. can be changed by Operators
 *
 * @author Alexei Drummond
 */

public interface StateNode extends Identifiable {

}
