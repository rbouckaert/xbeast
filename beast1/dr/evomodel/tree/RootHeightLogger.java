package dr.evomodel.tree;


import dr.inference.loggers.LogColumn;
import dr.inference.loggers.Loggable;
import dr.inference.model.Parameter;
import dr.util.Identifiable;
import xbeast.core.Param;

public class RootHeightLogger implements Loggable, Identifiable {
	
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

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	TreeModel tree;
    Parameter rootHeight;
    String id;
}
