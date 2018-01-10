package test.xbeast.core;

import xbeast.core.BEASTObject;
import xbeast.core.Description;
import xbeast.core.Param;

@Description("Class to test the behaviour of primitive inputs")
public class PrimitiveBeastObject extends BEASTObject {
	public enum Enumeration {none, one, two};
	
	private int i;
	private Enumeration e = Enumeration.none;
	double [] a = null;
	Double [] b = null;
	String [] s = null;

	public PrimitiveBeastObject() {
	}
	
	public PrimitiveBeastObject(@Param(name="i", description="input of primitive type") int i,
			@Param(name="e", description="input of primitive type", optional=true, defaultValue="one") Enumeration e) {
		this.i = i;
		this.e = e;
	}


	public PrimitiveBeastObject(@Param(name="e", description="input of primitive type", optional=true, defaultValue="one") Enumeration e) {
		this(0, e);
	}

	public PrimitiveBeastObject(@Param(name="a", description="input of array of primitive type") double [] a) {
		this.a = a.clone();
	}

	public PrimitiveBeastObject(@Param(name="b", description="input of array of objects") Double [] b) {
		this.b = b.clone();
	}

	public PrimitiveBeastObject(@Param(name="s", description="input of array of string objects") String [] s) {
		this.s = s.clone();
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

	public double[] getA() {
		if (a == null) {
			return null;
		}
		return a.clone();
	}

	public void setA(double[] a) {
		this.a = a.clone();
	}

	public Double[] getB() {
		if (b == null) {
			return null;
		}
		return b.clone();
	}

	public void setS(String[] s) {
		this.s = s.clone();
	}
	
	public String[] getS() {
		if (s == null) {
			return null;
		}
		return s.clone();
	}

	public void setB(Double[] b) {
		this.b = b.clone();
	}

	public class InnerClass extends PrimitiveBeastObject {

		public InnerClass() {}
		
		public InnerClass(@Param(name="i", description="input of primitive type") int i,
				@Param(name="e", description="input of primitive type", optional=true, defaultValue="one") Enumeration e) {
			super(i, e);
		}

		public InnerClass(@Param(name="e", description="input of primitive type", optional=true, defaultValue="one") Enumeration e) {
			this(0, e);
		}

		public InnerClass(@Param(name="a", description="input of primitive type") double[] a) {
			super(a);
		}
	}
}
