package xbeast.core;

import java.lang.reflect.InvocationTargetException;

public class VirtualBEASTObject extends BEASTObject {
	Object o;

	public VirtualBEASTObject(Object o) {
		this.o = o;
	}

	
	@Override
	public Object getInputValue(String name) {
		try {
			return BEASTObjectStore.INSTANCE.getInputValue(o, name);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void setInputValue(String name, Object value) {
		try {
			BEASTObjectStore.INSTANCE.setInputValue(o, name, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getId() {
		String id;
		try {
			id = (String) BEASTObjectStore.INSTANCE.getInputValue(o, "id");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		return id;
	}
	
	@Override
	public void setId(String ID) {
		try {
			BEASTObjectStore.INSTANCE.setInputValue(o, "id", ID);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
