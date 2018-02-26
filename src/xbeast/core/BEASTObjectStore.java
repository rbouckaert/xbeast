package xbeast.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	public static String getClassName(Object beastObject) {
		if (beastObject instanceof VirtualBEASTObject) {
			return ((VirtualBEASTObject) beastObject).getClassName();
		}
		return beastObject.getClass().getName();
	}
	

}
