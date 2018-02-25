package xbeast.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class BEASTObjectStore {
	
	public static BEASTObjectStore INSTANCE = new BEASTObjectStore();
	
	Map<Object, VirtualBEASTObject> objectStore;
	Map<Class<?>, Map<String, Method>> getterMap;
	Map<Class<?>, Map<String, Method>> setterMap;
	
	public BEASTObjectStore() {
		getterMap = new LinkedHashMap<>();
		setterMap = new LinkedHashMap<>();
		objectStore = new LinkedHashMap<>();
	}
	
	public BEASTObject addObject(Object o) {
		if (o instanceof BEASTObject) {
			return (BEASTObject) o;
		}
		VirtualBEASTObject vbo = new VirtualBEASTObject(o);
		
		// TODO: find getters and setters with @Param annotation
		return vbo;
	}
	
	public BEASTObject getBEASTObject(Object o) {
		BEASTObject vbo = objectStore.get(o);
		if (vbo == null) {
			vbo = addObject(o);
		}
		return vbo;
	}
	
	public Object getInputValue(Object o, String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (o instanceof BEASTInterface) {
			return ((BEASTInterface) o).getInputValue(name);
		}
		
		Map<String, Method> methodMap = getterMap.get(o.getClass());
		if (methodMap == null) {
			throw new IllegalArgumentException("Object " + o + " not found in ObjectStore::getterMap");
		}
		name = name.substring(0,1).toUpperCase() + name.substring(1);
		Method getter = methodMap.get(name);
		if (getter == null) {
			throw new IllegalArgumentException("Method get" + name + "() not found in ObjectStore::getterMap");
		}
		Object result = getter.invoke(o);
		return result;
	}

	public void setInputValue(Object o, String name, Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (o instanceof BEASTInterface) {
			((BEASTInterface) o).setInputValue(name, value);
			return;
		}
		
		Map<String, Method> methodMap = setterMap.get(o.getClass());
		if (methodMap == null) {
			throw new IllegalArgumentException("Object " + o + " not found in ObjectStore::setterMap");
		}
		name = name.substring(0,1).toUpperCase() + name.substring(1);
		Method setter = methodMap.get(name);
		if (setter == null) {
			throw new IllegalArgumentException("Method set" + name + "() not found in ObjectStore::getterMap");
		}
		setter.invoke(o, value);
	}
	
	public Map<String, Method> getterMethods(Object o) {
		Map<String, Method> methodMap = getterMap.get(o.getClass());
		return methodMap;
	}
	
	public Map<String, Method> setterMethods(Object o) {
		Map<String, Method> methodMap = setterMap.get(o.getClass());
		return methodMap;
	}
	

}
