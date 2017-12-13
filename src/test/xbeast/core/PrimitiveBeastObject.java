package test.xbeast.core;

import xbeast.core.BEASTObject;
import xbeast.core.Description;
import xbeast.core.Param;

@Description("Class to test the behaviour of primitive inputs")
public class PrimitiveBeastObject extends BEASTObject {
	public enum Enumeration {one, two};
	
	private int i;
	private Enumeration e = Enumeration.one;

	public PrimitiveBeastObject(@Param(name="i", description="input of primitive type") int i,
			@Param(name="e", description="input of primitive type", defaultValue="one") Enumeration e) {
		this.i = i;
		this.e = e;
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
	public Enumeration getE() {
		return e;
	}
	public void setE(Enumeration e) {
		this.e = e;
	}

}
