package test.xbeast.core;

import xbeast.core.BEASTObject;
import xbeast.core.Description;
import xbeast.core.Param;

@Description("Class to test the behaviour of primitive inputs")
public class PrimitiveBeastObject extends BEASTObject {
	private int i;

	public PrimitiveBeastObject(@Param(name="i", description="input of primitive type") int i) {
		this.i = i;
	}

	@Override
	public void initAndValidate() {
	}

	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
}
