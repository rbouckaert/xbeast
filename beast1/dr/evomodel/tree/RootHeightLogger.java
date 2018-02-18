package dr.evomodel.tree;

import dr.inference.loggers.LogColumn;
import dr.inference.loggers.Loggable;
import dr.inference.model.Parameter;
import xbeast.core.BEASTObject;
import xbeast.core.Param;

public class RootHeightLogger extends BEASTObject implements Loggable {
	
	public RootHeightLogger(@Param(name="tree") TreeModel tree) {		
		this.tree = tree;
		rootHeight = tree.getRootHeightParameter();
	}

    public TreeModel getTree() {return tree;}
    public void setTree(TreeModel tree) {throw new RuntimeException("Not implemented yet");}

    @Override
	public LogColumn[] getColumns() {
    	return rootHeight.getColumns();
	}

    TreeModel tree;
    Parameter rootHeight;
}
