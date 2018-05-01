package test.xbeast.integration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import beast.core.parameter.RealParameter;
import beast.util.PackageManager;
import dr.inference.loggers.LogColumn;
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
        final List<String> objectNames = PackageManager.find(xbeast.Operator.class, PackageManager.IMPLEMENTATION_DIR);
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

    
    
    class LoggableMisFit2 implements beast.core.Loggable {

		@Override
		public void init(PrintStream out) {
			out.print("ok\tok2\t");
		}

		@Override
		public void close(PrintStream out) {
		}
    	
    }
    
    public void testLoggableRealParameterParameter() {
    	RealParameter p = new RealParameter("3.0 14.0");
    	p.setID("oki");
    	p.log(0l, System.out);
    	System.out.println();
    	for (LogColumn c : p.getColumns()) {
    		System.out.print(c.getLabel());
    		System.out.print("\t");
    	}
    	System.out.println();
    	for (LogColumn c : p.getColumns()) {
    		System.out.print(c.getFormatted());
    		System.out.print("\t");
    	}
    	System.out.println();
    }
    
    public void testLoggableMisFit() {
    	LoggableMisFit2 o2 = new LoggableMisFit2();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = null;
		try {
			out = new PrintStream(baos, true, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	try {
    		o2.init(out);
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o2.log(0l, out);
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o2.getColumns();
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    }

    /**
     * Check all plug-ins have a proper setID and getID implementation
     */
    @Test
    public void testLoggableMethods() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = null;
		try {
			out = new PrintStream(baos, true, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

        final List<String> objectNames = PackageManager.find(xbeast.Loggable.class, PackageManager.IMPLEMENTATION_DIR);
        final List<String> objectsWithoutInit = new ArrayList<String>();
        final List<String> objectsWithoutLog = new ArrayList<String>();
        final List<String> objectsWithoutGetColumns = new ArrayList<String>();
        for (final String beastObjectName : objectNames) {
            try {
                final Class<?> objectClass = beast.util.PackageManager.forName(beastObjectName);
                try {
                	xbeast.Loggable o = (xbeast.Loggable) objectClass.newInstance();
                	try {
                		o.init(out);
                	} catch (StackOverflowError e) {
                		objectsWithoutInit.add(beastObjectName);
                	}
                	try {
                		o.log(0l, out);
                	} catch (StackOverflowError e) {
                		objectsWithoutLog.add(beastObjectName);
                	}
                	try {
                		o.getColumns();
                	} catch (StackOverflowError e) {
                		objectsWithoutGetColumns.add(beastObjectName);
                	}
                } catch (InstantiationException | IllegalAccessException e) {
                	// ignore -- these classes cannot be tested
                }
            } catch (Exception e) {
            }
        }
        assertTrue("No proper init(PrintStream) implementation for: " + objectsWithoutInit.toString() +
        		"\nNo proper log(sampleNr,PrintStream) implementation for: " + objectsWithoutLog.toString() +
        		"\nNo proper getColumns() implementation for: " + objectsWithoutGetColumns.toString(), 
        		objectsWithoutGetColumns.size() + objectsWithoutInit.size() + objectsWithoutLog.size() == 0);
    } // testOperatorMethods

} // class InterfaceImplementationTest
