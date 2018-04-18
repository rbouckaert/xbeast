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
    } // testDescriptions

} // class InterfaceImplementationTest
