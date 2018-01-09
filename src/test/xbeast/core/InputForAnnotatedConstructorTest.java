package test.xbeast.core;

import java.util.List;

import org.json.JSONException;
import org.junit.Test;

import junit.framework.TestCase;
import test.xbeast.core.PrimitiveBeastObject.Enumeration;
import xbeast.core.Input;
import xbeast.util.JSONParser;
import xbeast.util.JSONParserException;
import xbeast.util.JSONProducer;
import xbeast.util.XMLParser;
import xbeast.util.XMLParserException;
import xbeast.util.XMLProducer;

public class InputForAnnotatedConstructorTest extends TestCase {

	
	@Test
	public void testPrimitiveInput() {
		PrimitiveBeastObject o = new PrimitiveBeastObject(1, Enumeration.one);
		
		// test type
		List<Input<?>> inputs = o.listInputs();
		assertEquals(3, inputs.size());
		Input<?> input = null;
		for (Input i : inputs) {
			if (i.getName().equals("i")) {
				input = i;
				break;
			}
		}
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
	public void testArrayInput() {
		double [] a = new double[] {1.0, 3.0};
		PrimitiveBeastObject o3 = new PrimitiveBeastObject(a);
		assertEquals(1.0, o3.getA()[0]);
		assertEquals(3.0, o3.getA()[1]);
		a[0] = 5; 
		a[1] = 9;
		assertEquals(1.0, o3.getA()[0]);
		assertEquals(3.0, o3.getA()[1]);
		o3.setA(a);
		assertEquals(5.0, o3.getA()[0]);
		assertEquals(9.0, o3.getA()[1]);
		
//		JSONProducer producer = new JSONProducer();
//		String json2 = producer.toJSON(o3);
//		System.out.println(json2);
	}
	
	@Test
	public void testXML() throws XMLParserException {
		String xml = "<input id='testObject' spec='test.xbeast.core.PrimitiveBeastObject' e='two' i='3'/>";
		String  xml2;
		XMLParser parser = new XMLParser();
		XMLProducer producer = new XMLProducer();
		Object o;
		PrimitiveBeastObject po;
		
		o = parser.parseBareFragment(xml, false);
		po = (PrimitiveBeastObject) o;
		assertEquals(3, po.getI());
		assertEquals(Enumeration.two, po.getE());
		
		xml2 = producer.toRawXML(po).trim();
		assertEquals(xml, xml2);

		xml = "<input id='testObject' spec='test.xbeast.core.PrimitiveBeastObject' i='2'/>";
		o = parser.parseBareFragment(xml, false);
		po = (PrimitiveBeastObject) o;
		assertEquals(2, po.getI());
		assertEquals(Enumeration.one, po.getE());
		
		po.setE(Enumeration.one);
		xml2 = producer.toRawXML(po).trim();
		assertEquals(xml, xml2);
	}	
	
	@Test
	public void testJSON() throws JSONParserException, JSONException {
		List<Object> o;
		PrimitiveBeastObject po = null;
		
		String json = "{id: testObject, spec: test.xbeast.core.PrimitiveBeastObject, i: 3, e: two }";
		JSONParser parser = new JSONParser();
		o = parser.parseBareFragment(json, false);
		po = (PrimitiveBeastObject) o.get(0);
		assertEquals(3, po.getI());
		assertEquals(Enumeration.two, po.getE());
		
		JSONProducer producer = new JSONProducer();
		String json2 = producer.toJSON(po);
		json2 = json2.substring(json2.indexOf('[') + 1, json2.indexOf(']')).trim();
		assertEquals("{id: \"testObject\", spec: \"test.xbeast.core.PrimitiveBeastObject\", e: \"two\", i: \"3\" }", json2);
		
		json = "{id: testObject, spec: test.xbeast.core.PrimitiveBeastObject, i: 2}";
		parser = new JSONParser();
		o = parser.parseBareFragment(json, false);
		po = (PrimitiveBeastObject) o.get(0);
		assertEquals(2, po.getI());
		assertEquals(Enumeration.one, po.getE());
		
		json2 = producer.toJSON(po);
		json2 = json2.substring(json2.indexOf('[') + 1, json2.indexOf(']')).trim();
		assertEquals("{id: \"testObject\", spec: \"test.xbeast.core.PrimitiveBeastObject\", i: \"2\" }", json2);

	
		json = "{id: testObject, spec: test.xbeast.core.PrimitiveBeastObject, a: \"1.0 15.0 17.0\"}";
		parser = new JSONParser();
		o = parser.parseBareFragment(json, false);
		po = (PrimitiveBeastObject) o.get(0);
		assertEquals(3, po.getA().length);
		assertEquals(1.0, po.getA()[0]);
		assertEquals(15.0, po.getA()[1]);
		assertEquals(17.0, po.getA()[2]);

		json2 = producer.toJSON(po);
		json2 = json2.substring(json2.indexOf('[') + 1, json2.indexOf(']')).trim();
		assertEquals("{id: \"testObject\", spec: \"test.xbeast.core.PrimitiveBeastObject\", e: \"none\", i: \"0\", a: \"1.0 15.0 17.0\" }", json2);
	}	
}
