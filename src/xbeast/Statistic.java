package xbeast;

public interface Statistic extends Identifiable {

    /**
     * @return the name of this statistic
     */
    default String getStatisticName() {
    	return getID();
    }

    /**
     * @param dim the dimension to return name of
     * @return the statistic's name for a given dimension
     */
    default String getDimensionName(int dim) {
    	if (getDimension() > 0) {
    		return getID() + dim;
    	} 
    	return getID();
    }

    /**
     * Set the names of the dimensions (optional, by default they are named after the statistic).
     * @param names
     */
    default void setDimensionNames(String[] names) {
    	System.err.println(this.getClass().getName()+"/setDimensionNames(names) not implemented yet");
    	System.err.println("Hope this does not cause any trouble...");
    }

    /**
     * @return the number of dimensions that this statistic has.
     */
    int getDimension();

    /**
     * @param dim the dimension to return value of
     * @return the statistic's scalar value in the given dimension
     */
    default double getStatisticValue(int dim) {
    	return getArrayValue(dim);
    }
    
	default double getArrayValue() {
		return getArrayValue(0);
	}
	
	default double getArrayValue(int dim) {
		try {
			return getStatisticValue(dim);
		} catch (StackOverflowError e) {
			System.err.println("Programmer error: getArrayValue(dim) is not implemented in class " + this.getClass().getName()+ "!");
			throw e;
		} 
	}

    
    
    /**
     * @return the sum of all the elements
     */
    default double getValueSum() {
    	double sum = 0;
    	for (int i = 0; i < getDimension(); i++) {
    		sum += getStatisticValue(i);
    	}
    	return sum;
    }

}
