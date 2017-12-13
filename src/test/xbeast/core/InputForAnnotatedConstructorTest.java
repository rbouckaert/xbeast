package test.xbeast.core;

import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;
import test.xbeast.core.PrimitiveBeastObject.Enumeration;
import xbeast.core.Input;
import xbeast.util.XMLParser;
import xbeast.util.XMLParserException;
import xbeast.util.XMLProducer;

public class InputForAnnotatedConstructorTest extends TestCase {

	
	@Test
	public void testPrimitiveInput() {
		PrimitiveBeastObject o = new PrimitiveBeastObject(1, Enumeration.one);
		
		// test type
		List<Input<?>> inputs = o.listInputs();
		Input<?> input = inputs.get(0);		
		assertEquals("int", input.getType().toGenericString());
		
		// test value
		assertEquals(1, input.get());
		
		// test setting a value
		input.set(2);
		assertEquals(2, input.get());
		
		// test setting a value by primitive
		int i = 3;
		input.set(i);
		assertEquals(3, input.get());
		
		// test setting a value by object
		Integer j = 4;
		input.set(j);
		assertEquals(4, input.get());

		Short k = 5;
		// fails -- don't know how to convert from Short to int
//		input.set(k);
//		assertEquals(5, input.get());
	}
	
	@Test
	public void testEnumerationInput() {
		PrimitiveBeastObject o = new PrimitiveBeastObject(1, Enumeration.one);
		assertEquals("one", o.getE().toString());
		
		o.setE(Enumeration.two);
		assertEquals("two", o.getE().toString());

		PrimitiveBeastObject o2 = new PrimitiveBeastObject(0, Enumeration.two);
		assertEquals("two", o2.getE().toString());
	}
	
	
	@Test
	public void testXML() throws XMLParserException {
		String xml = "<input id='testObject' spec='test.xbeast.core.PrimitiveBeastObject' i='3' e='two'/>";
		XMLParser parser = new XMLParser();
		Object o = parser.parseBareFragment(xml, false);
		PrimitiveBeastObject po = (PrimitiveBeastObject) o;
		assertEquals(3, po.getI());
		
		XMLProducer producer = new XMLProducer();
		String xml2 = producer.toRawXML(po).trim();
		System.out.println(xml + "\n" + xml2);
		assertEquals(xml, xml2);
	}	
	
}
