package beast.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import beast.util.XMLParser.NameValuePair;

public class BEASTObjectStore {
	
	public static BEASTObjectStore INSTANCE = new BEASTObjectStore();
	
	Map<Object, VirtualBEASTObject> objectStore;
	
	public BEASTObjectStore() {
		objectStore = new LinkedHashMap<>();
	}
	
	public BEASTObject addObject(Object o) {
		if (o instanceof BEASTObject) {
			return (BEASTObject) o;
		}
		VirtualBEASTObject vbo = new VirtualBEASTObject(o);
		objectStore.put(o, vbo);
		return vbo;
	}
	
	public BEASTObject getBEASTObject(Object o) {
		BEASTObject vbo = objectStore.get(o);
		if (vbo == null) {
			vbo = addObject(o);
		}
		return vbo;
	}
	
	public static List<BEASTInterface> listActiveBEASTObjects(Object beastObject) {
		if (!(beastObject instanceof BEASTInterface)) {
			beastObject = INSTANCE.getBEASTObject(beastObject);
		}
		return ((BEASTInterface) beastObject).listActiveBEASTObjects();
	}

	public static List<Input<?>> listInputs(Object beastObject) {
		if (!(beastObject instanceof BEASTInterface)) {
			beastObject = INSTANCE.getBEASTObject(beastObject);
		}
		return ((BEASTInterface) beastObject).listInputs();
	}

	public static String getId(Object beastObject) {
		if (!(beastObject instanceof BEASTInterface)) {
			beastObject = INSTANCE.getBEASTObject(beastObject);
		}
		return ((BEASTInterface) beastObject).getId();
	}

	public static void setId(Object beastObject, String id) {
		if (!(beastObject instanceof BEASTInterface)) {
			beastObject = INSTANCE.getBEASTObject(beastObject);
		}
		((BEASTInterface) beastObject).setId(id);
	}

	public static boolean isPrimitive(Object value) {			
		// The value is primitive if there are no @Param annotations, and 
		// no newInstance() method and has no @Description annotation. 
		// Any primitive object (int, short, boolean, etc) is certainly primitive.
		if (value.getClass().isPrimitive()) {
			return true;
		}
		
		List<Input<?>> inputs = INSTANCE.getBEASTObject(value).listInputs();
		if (inputs.size() != 0) {
			return false;
		}
		
//		try {
//			if (value.getClass().getMethod("newInstance") != null) {
//				return false;
//			}
//		} catch (NoSuchMethodException | SecurityException e) {
//			// ignore
//		}
		
		if (!INSTANCE.getBEASTObject(value).getDescriptionString().equals(BEASTInterface.DEFEAULT_DESCRIPTION)) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isPrimitiveType(String typeName) {
		try {
			Class clazz = Class.forName(typeName);

			if (clazz.isPrimitive()) {
				return true;
			}
			
			// has @Param annotation
		    Constructor<?>[] allConstructors = clazz.getDeclaredConstructors();
		    for (Constructor<?> ctor : allConstructors) {
		    	Annotation[][] annotations = ctor.getParameterAnnotations();
		    	for (Annotation [] a0 : annotations) {
			    	for (Annotation a : a0) {
			    		if (a instanceof Param) {
			    			return false;
			    		}
		    		}
		    	}	    	
		    }
		    
			// has @Description annotation
	        final Annotation[] classAnnotations = clazz.getAnnotations();
	        for (final Annotation annotation : classAnnotations) {
	            if (annotation instanceof Description) {
	                return false;
	            }
	        }		
			
			return true;
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Incorrect type name: " + e);
		}

	}


	public static String getClassName(Object beastObject) {
		if (beastObject instanceof VirtualBEASTObject) {
			return ((VirtualBEASTObject) beastObject).getClassName();
		}
		return beastObject.getClass().getName();
	}

	

}
