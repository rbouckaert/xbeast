package test.xbeast.integration;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import beast.core.parameter.RealParameter;
import beast.math.distributions.Normal;
import beast.math.distributions.Prior;
import beast.util.PackageManager;
import dr.inference.loggers.LogColumn;
import dr.inference.model.Parameter;
import dr.inference.model.VariableListener;
import dr.inference.operators.ScaleOperator;
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
    
    public void testLoggableRealParameter() {
    	// log a beast 2 parameter with beast 1
    	RealParameter p2 = new RealParameter("3.0 14.0");
    	p2.setID("oki");
    	p2.log(0l, System.out);
    	System.out.println();
    	for (LogColumn c : p2.getColumns()) {
    		System.out.print(c.getLabel());
    		System.out.print("\t");
    	}
    	System.out.println();
    	for (LogColumn c : p2.getColumns()) {
    		System.out.print(c.getFormatted());
    		System.out.print("\t");
    	}
    	System.out.println();
    	
    	
    	// log a beast 1 parameter with beast 2
    	Parameter.Default p1 = new Parameter.Default(new double[]{14.0, 3.0});
    	p1.setId("iko");
    	p1.init(System.out);
    	System.out.println();
    	p1.log(0l, System.out);
    	System.out.println();
    	
    	
    	// beast 1 parameter with beast 2 prior
    	Parameter.Default x1 = new Parameter.Default(new double[]{2.0});
    	Normal normal = new Normal();
    	normal.initByName("mean","1.0","sigma","1");
    	Prior prior = new Prior();
    	prior.initByName("x", x1, "distr", normal);
    	double logP = prior.calculateLogP();
    	assertEquals(logP, -1.4189385332046727, 1e-10);

    	// beast 1 parameter with beast 1 operator, but beast 2 methods
    	ScaleOperator operator = new ScaleOperator(x1, 0.75);
    	x1.addBounds(0.0, Double.POSITIVE_INFINITY);
    	operator.proposal();
    	double v = x1.getArrayValue();
    	assertNotSame(v, 2.0);
    	operator.accept();
    	operator.proposal();
    	assertNotSame(x1.getValue(0), v);
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
//    	try {
//    		o2.init(out);
//    		// should never get here
//    		assert(false);
//    	} catch (StackOverflowError e) {
//    		// OK this is expected
//    	}
    	try {
    		o2.log(0l, out);
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
//    	try {
//    		o2.getColumns();
//    		assert(false);
//    	} catch (StackOverflowError e) {
//    		// OK this is expected
//    	}
    }

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
    
    
    
    class VariableMisFit implements xbeast.Variable<Double> {

//		@Override
//		public int getDimension() {
//			// TODO Auto-generated method stub
//			return 0;
//		}

		@Override
		public Double getValue(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setValue(int index, Double value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Double[] getValues() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addVariableListener(VariableListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Collection<VariableListener> getVariableListeners() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void removeVariableListener(VariableListener listener) {
			// TODO Auto-generated method stub
			
		}
    	
    }

    public void testVariableMisFit() {
    	VariableMisFit o = new VariableMisFit();
    	try {
    		o.getDimension();
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.store();
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.restore();
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.setUpper(0.0);
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.setLower(0.0);
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    }

    @Test
    public void testVariableMethods() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = null;
		try {
			out = new PrintStream(baos, true, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

        final List<String> objectNames = PackageManager.find(xbeast.Variable.class, PackageManager.IMPLEMENTATION_DIR);
        final List<String> objectsWithoutGetDimension = new ArrayList<String>();
        final List<String> objectsWithoutStore = new ArrayList<String>();
        final List<String> objectsWithoutRestore = new ArrayList<String>();
        final List<String> objectsSetUpper = new ArrayList<String>();
        final List<String> objectsSetLower = new ArrayList<String>();
        for (final String beastObjectName : objectNames) {
            try {
                final Class<?> objectClass = beast.util.PackageManager.forName(beastObjectName);
                try {
                	xbeast.Variable o = (xbeast.Variable) objectClass.newInstance();
                	try {
                		o.getDimension();
                	} catch (StackOverflowError e) {
                		objectsWithoutGetDimension.add(beastObjectName);
                	}
                	try {
                		o.store();
                	} catch (StackOverflowError e) {
                		objectsWithoutStore.add(beastObjectName);
                	}
                	try {
                		o.restore();
                	} catch (StackOverflowError e) {
                		objectsWithoutRestore.add(beastObjectName);
                	}
                	try {
                		o.setUpper(0);
                	} catch (StackOverflowError e) {
                		objectsSetUpper.add(beastObjectName);
                	}
                	try {
                		o.setLower(0);
                	} catch (StackOverflowError e) {
                		objectsSetLower.add(beastObjectName);
                	}
                } catch (InstantiationException | IllegalAccessException e) {
                	// ignore -- these classes cannot be tested
                }
            } catch (Exception e) {
            }
        }
        assertTrue("No proper getDimension() implementation for: " + objectsWithoutGetDimension.toString() +
        		"\nNo proper store() implementation for: " + objectsWithoutStore.toString() +
        		"\nNo proper restore() implementation for: " + objectsWithoutRestore.toString() + 
        		"\nNo proper setUpper(V) implementation for: " + objectsSetUpper.toString() +
        		"\nNo proper setLower(V) implementation for: " + objectsSetLower.toString(),
        		objectsWithoutGetDimension.size() + objectsWithoutStore.size() + objectsWithoutRestore.size() +
        		objectsSetUpper.size() + objectsSetLower.size() == 0
        		);
    } // testVariableMethods
    

    class ModelMisfit implements xbeast.Model {
    	
    }
    
    public void testModelMisfit() {
    	ModelMisfit o = new ModelMisfit();
    	try {
    		o.store();
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.restore();
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.accept();
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    }

    @Test
    public void testModelMethods() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = null;
		try {
			out = new PrintStream(baos, true, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

        final List<String> objectNames = PackageManager.find(xbeast.Model.class, PackageManager.IMPLEMENTATION_DIR);
        final List<String> objectsWithoutAccept = new ArrayList<String>();
        final List<String> objectsWithoutStore = new ArrayList<String>();
        final List<String> objectsWithoutRestore = new ArrayList<String>();
        for (final String beastObjectName : objectNames) {
            try {
                final Class<?> objectClass = beast.util.PackageManager.forName(beastObjectName);
                try {
                	xbeast.Model o = (xbeast.Model) objectClass.newInstance();
                	try {
                		o.accept();
                	} catch (StackOverflowError e) {
                		objectsWithoutAccept.add(beastObjectName);
                	}
                	try {
                		o.store();
                	} catch (StackOverflowError e) {
                		objectsWithoutStore.add(beastObjectName);
                	}
                	try {
                		o.restore();
                	} catch (StackOverflowError e) {
                		objectsWithoutRestore.add(beastObjectName);
                	}
                } catch (InstantiationException | IllegalAccessException e) {
                	// ignore -- these classes cannot be tested
                }
            } catch (Exception e) {
            }
        }
        assertTrue("No proper accept() implementation for: " + objectsWithoutAccept.toString() +
        		"\nNo proper store() implementation for: " + objectsWithoutStore.toString() +
        		"\nNo proper restore() implementation for: " + objectsWithoutRestore.toString(),
        		objectsWithoutAccept.size() + objectsWithoutStore.size() + objectsWithoutRestore.size() == 0
        		);
    } // testModelMethods
    
    
    class LikelihoodMisFit implements xbeast.Likelihood {

		@Override
		public void makeDirty() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isUsed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setUsed() {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
    public void testLikelihoodMisFit() {
    	LikelihoodMisFit o = new LikelihoodMisFit();
    	try {
    		o.calculateLogP();
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.getCurrentLogP();
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    	try {
    		o.isDirtyCalculation();
    		// should never get here
    		assert(false);
    	} catch (StackOverflowError e) {
    		// OK this is expected
    	}
    }

    @Test
    public void testLikelihoodMethods() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = null;
		try {
			out = new PrintStream(baos, true, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

        final List<String> objectNames = PackageManager.find(xbeast.Likelihood.class, PackageManager.IMPLEMENTATION_DIR);
        final List<String> objectsWithoutGetCurrentLogP = new ArrayList<String>();
        final List<String> objectsWithoutCalculateLogP = new ArrayList<String>();
        final List<String> objectsWithoutIsDirtyCalculation = new ArrayList<String>();
        for (final String beastObjectName : objectNames) {
            try {
                final Class<?> objectClass = beast.util.PackageManager.forName(beastObjectName);
                try {
                	xbeast.Likelihood o = (xbeast.Likelihood) objectClass.newInstance();
                	try {
                		o.getCurrentLogP();
                	} catch (StackOverflowError e) {
                		objectsWithoutGetCurrentLogP.add(beastObjectName);
                	}
                	try {
                		o.calculateLogP();
                	} catch (StackOverflowError e) {
                		objectsWithoutCalculateLogP.add(beastObjectName);
                	}
                	try {
                		o.isDirtyCalculation();
                	} catch (StackOverflowError e) {
                		objectsWithoutIsDirtyCalculation.add(beastObjectName);
                	}
                } catch (InstantiationException | IllegalAccessException e) {
                	// ignore -- these classes cannot be tested
                }
            } catch (Exception e) {
            }
        }
        assertTrue("No proper getCurrentLogP() implementation for: " + objectsWithoutGetCurrentLogP.toString() +
        		"\nNo proper calculateLogP() implementation for: " + objectsWithoutIsDirtyCalculation.toString() +
        		"\nNo proper isDirtyCalculation() implementation for: " + objectsWithoutIsDirtyCalculation.toString(),
        		objectsWithoutGetCurrentLogP.size() + objectsWithoutIsDirtyCalculation.size() + 
        		objectsWithoutIsDirtyCalculation.size() == 0
        		);
    } // testLikelihoodMethods
    
    
    
    
    
} // class InterfaceImplementationTest
