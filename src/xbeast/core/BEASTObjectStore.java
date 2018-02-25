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
	

}
