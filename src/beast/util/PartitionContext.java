package beast.util;


import beast.core.BEASTInterface;
import beast.evolution.likelihood.GenericTreeLikelihood;

public class PartitionContext {
	public String partition;
	public String siteModel;
	public String clockModel;
	public String tree;

	public PartitionContext() {}
	
	public PartitionContext(String partition) {
		this.partition = partition;
		siteModel = partition;
		clockModel = partition;
		tree = partition;
	}
	
	public PartitionContext(String partition,
			String siteModel,
			String clockModel,
			String tree
			) {
		this.partition = partition;
		this.siteModel = siteModel;
		this.clockModel = clockModel;
		this.tree = tree;
	}
	
	public PartitionContext(GenericTreeLikelihood treeLikelihood) {
		String id = treeLikelihood.dataInput.get().getId();
		id = parsePartition(id);
		this.partition = id;
		if (treeLikelihood.branchRateModelInput.get() != null) {
			id = treeLikelihood.branchRateModelInput.get().getId();
			id = parsePartition(id);
		}
		this.clockModel = id;
		id = ((BEASTInterface) treeLikelihood.siteModelInput.get()).getId();
		id = parsePartition(id);
		this.siteModel = id;
		id = treeLikelihood.treeInput.get().getId();
		id = parsePartition(id);
		this.tree = id;
	}
	
    static public String parsePartition(String id) {
        String partition = id.substring(id.indexOf('.') + 1);
        if (partition.indexOf(':') >= 0) {
            partition = partition.substring(partition.indexOf(':') + 1);
        }
        return partition;
    }

	@Override
	public String toString() {
		return partition + "," + siteModel + "," + clockModel + "," + tree;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof PartitionContext) {
			PartitionContext other = (PartitionContext) obj;
			return other.partition.equals(partition) &&
				other.siteModel.equals(siteModel) &&
				other.clockModel.equals(clockModel) &&
				other.tree.equals(tree);
		}
		return false;
	}
}
