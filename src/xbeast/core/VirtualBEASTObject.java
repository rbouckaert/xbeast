package xbeast.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import xbeast.core.util.Log;

public class VirtualBEASTObject extends BEASTObject {
	Object o;

	public VirtualBEASTObject(Object o) {
		this.o = o;
		// this initialises inputs
		getInputs();
	}
		
	@Override
	public String getId() {
		String id;
		try {
			Method getter = o.getClass().getMethod("getId");
			id = (String) getter.invoke(o);
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			Log.err.println("Method getId() could not be found on " + o.getClass().getName());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return id;
	}
	
	@Override
	public void setId(String ID) {
		try {
			Method setter = o.getClass().getMethod("setId");
			setter.invoke(o, ID);
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			Log.err.println("Method setId() could not be found on " + o.getClass().getName());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Input<?>> listInputs() {
        final List<Input<?>> inputs = new ArrayList<>();        
        Map<String, Input> inputNames = new LinkedHashMap<>();
        listAnnotatedInputs(o, inputs, inputNames);
		return inputs; 
	}
}
