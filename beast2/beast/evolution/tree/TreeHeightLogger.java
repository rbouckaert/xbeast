package beast.evolution.tree;



import java.io.PrintStream;

import xbeast.core.CalculationNode;
import xbeast.core.Description;
import xbeast.core.Function;
import xbeast.core.Input;
import xbeast.core.Input.Validate;
import xbeast.core.Loggable;


@Description("Logger to report height of a tree -- deprecated: use TreeStatLogger instead")
@Deprecated
public class TreeHeightLogger extends CalculationNode implements Loggable, Function {
    final public Input<Tree> treeInput = new Input<>("tree", "tree to report height for.", Validate.REQUIRED);
    
    @Override
    public void initAndValidate() {
        // nothing to do
    }

    @Override
    public void init(PrintStream out) {
        final Tree tree = treeInput.get();
        if (getId() == null || getId().matches("\\s*")) {
            out.print(tree.getId() + ".height\t");
        } else {
            out.print(getId() + "\t");
        }
    }

    @Override
    public void log(long sample, PrintStream out) {
        final Tree tree = treeInput.get();
        out.print(tree.getRoot().getHeight() + "\t");
    }

	@Override
    public void close(PrintStream out) {
        // nothing to do
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public double getArrayValue() {
        return treeInput.get().getRoot().getHeight();
    }

    @Override
    public double getArrayValue(int dim) {
        return treeInput.get().getRoot().getHeight();
    }
}
