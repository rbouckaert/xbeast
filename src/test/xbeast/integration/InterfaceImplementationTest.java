package test.xbeast.integration;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import beast.util.PackageManager;
import junit.framework.TestCase;

public class InterfaceImplementationTest extends TestCase {
	
	{
		System.setProperty("beast.is.junit.testing", "true");
	}
	
	
	class MisFit implements xbeast.Identifiable {
		
	}
	
	/**
	 *  Test that in case getID and setID has not been implemented properly a StackOverflowError is thrown
	 */
    public void testGetAndSetIDsMisFit() {
    	MisFit o = new MisFit();
    	try {
    		o.setId("test");
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.setID("test");
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.getID();
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}

    	try {
    		o.getID();
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	
    }
    
    /**
     * Check all plug-ins have a proper setID and getID implementation
     */
    @Test
    public void testGetAndSetIDs() {
        final List<String> objectNames = PackageManager.find(xbeast.Identifiable.class, PackageManager.IMPLEMENTATION_DIR);
        final List<String> objectsWithoutGetID = new ArrayList<String>();
        final List<String> objectsWithoutSetID = new ArrayList<String>();
        for (final String beastObjectName : objectNames) {
            try {
                final Class<?> objectClass = beast.util.PackageManager.forName(beastObjectName);
                try {
                	xbeast.Identifiable o = (xbeast.Identifiable) objectClass.newInstance();
                	try {
                		o.setId("test");
                		o.setID("test");
                	} catch (StackOverflowError e) {
                		objectsWithoutSetID.add(beastObjectName);
                	}
                	try {
                		o.getId();
                		o.getID();
                	} catch (StackOverflowError e) {
                		objectsWithoutGetID.add(beastObjectName);
                	}
                } catch (InstantiationException | IllegalAccessException e) {
                	// ignore -- these classes cannot be tested
                }
            } catch (Exception e) {
            }
        }
        assertTrue("No proper getID() implementation for: " + objectsWithoutGetID.toString() +
        		"\nNo proper SetID() implementation for: " + objectsWithoutSetID.toString(), 
        		objectsWithoutSetID.size() + objectsWithoutGetID.size() == 0);
    } // testGetAndSetIDs



	class OperatorMisFit implements xbeast.Operator {

		@Override
		public void accept(double deviation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void reject() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void reset() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public long getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getAcceptCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setAcceptCount(long acceptCount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public long getRejectCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setRejectCount(long rejectCount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public double getTargetAcceptanceProbability() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getPerformanceSuggestion() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public double getWeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setWeight(double weight) {
			// TODO Auto-generated method stub
			
		}
		
	}

    public void testGetAndSetOperatorsMisFit() {
    	OperatorMisFit o = new OperatorMisFit();
    	try {
    		o.operate();
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.proposal();
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.getName();
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}

    	try {
    		o.getOperatorName();
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}    	
    }

    /**
     * Check all plug-ins have a proper setID and getID implementation
     */
    @Test
    public void testOperatorMethods() {
        final List<String> objectNames = PackageManager.find(xbeast.Identifiable.class, PackageManager.IMPLEMENTATION_DIR);
        final List<String> objectsWithoutProposal = new ArrayList<String>();
        final List<String> objectsWithoutGetName = new ArrayList<String>();
        for (final String beastObjectName : objectNames) {
            try {
                final Class<?> objectClass = beast.util.PackageManager.forName(beastObjectName);
                try {
                	xbeast.Operator o = (xbeast.Operator) objectClass.newInstance();
                	try {
                		o.getOperatorName();
                		o.getName();
                	} catch (StackOverflowError e) {
                		objectsWithoutGetName.add(beastObjectName);
                	}
                	try {
                		o.proposal();
                		o.operate();
                	} catch (StackOverflowError e) {
                		objectsWithoutProposal.add(beastObjectName);
                	}
                } catch (InstantiationException | IllegalAccessException e) {
                	// ignore -- these classes cannot be tested
                }
            } catch (Exception e) {
            }
        }
        assertTrue("No proper proposal() implementation for: " + objectsWithoutProposal.toString() +
        		"\nNo proper getName() implementation for: " + objectsWithoutGetName.toString(), 
        		objectsWithoutGetName.size() + objectsWithoutProposal.size() == 0);
    } // testOperatorMethods

} // class InterfaceImplementationTest
