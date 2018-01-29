package beast.core.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import xbeast.core.BEASTObject;
import xbeast.core.CalculationNode;
import xbeast.core.Description;
import xbeast.core.Function;
import xbeast.core.Input;
import xbeast.core.Input.Validate;
import xbeast.core.Loggable;
import beast.core.parameter.BooleanParameter;
import beast.core.parameter.IntegerParameter;



@Description("calculates sum of a valuable")
public class Sum extends CalculationNode implements Function, Loggable {
    final public Input<List<Function>> functionInput = new Input<>("arg", "argument to be summed", new ArrayList<>(), Validate.REQUIRED);

    enum Mode {integer_mode, double_mode}

    Mode mode;

    boolean needsRecompute = true;
    double sum = 0;
    double storedSum = 0;

    @Override
    public void initAndValidate() {
        List<Function> valuable = functionInput.get();
        mode = Mode.integer_mode;
        for (Function v : valuable) {
	        if (!(v instanceof IntegerParameter || v instanceof BooleanParameter)) {
	            mode = Mode.double_mode;
	        }
        }
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public double getArrayValue() {
        if (needsRecompute) {
            compute();
        }
        return sum;
    }

    /**
     * do the actual work, and reset flag *
     */
    void compute() {
        sum = 0;
        for (Function v : functionInput.get()) {
	        for (int i = 0; i < v.getDimension(); i++) {
	            sum += v.getArrayValue(i);
	        }
        }
        needsRecompute = false;
    }

    @Override
    public double getArrayValue(int dim) {
        if (dim == 0) {
            return getArrayValue();
        }
        return Double.NaN;
    }

    /**
     * CalculationNode methods *
     */
    @Override
    public void store() {
        storedSum = sum;
        super.store();
    }

    @Override
    public void restore() {
        sum = storedSum;
        super.restore();
    }

    @Override
    public boolean requiresRecalculation() {
        needsRecompute = true;
        return true;
    }

    /**
     * Loggable interface implementation follows
     */
    @Override
    public void init(PrintStream out) {
        out.print("sum(" + ((BEASTObject) functionInput.get().get(0)).getId() + ")\t");
    }

    @Override
    public void log(int sampleNr, PrintStream out) {
        double sum = 0;
        for (Function v : functionInput.get()) {
	        for (int i = 0; i < v.getDimension(); i++) {
	            sum += v.getArrayValue(i);
	        }
        }
        if (mode == Mode.integer_mode) {
            out.print((int) sum + "\t");
        } else {
            out.print(sum + "\t");
        }
    }

    @Override
    public void close(PrintStream out) {
        // nothing to do
    }

} // class Sum
