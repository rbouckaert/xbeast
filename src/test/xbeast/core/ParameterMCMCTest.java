package test.xbeast.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import beast.core.Logger;
import beast.core.MCMC;
import beast.math.distributions.Normal;
import beast.math.distributions.Prior;
import beast.util.Randomizer;
import beast.util.XMLParser;
import beast.util.XMLParserException;
import beast.util.XMLProducer;
import dr.inference.model.Parameter;
import dr.inference.operators.ScaleOperator;
import junit.framework.TestCase;

public class ParameterMCMCTest extends TestCase {
	
	@Test
	public void testMCMC() {
		try {
			// run MCMC 2 on parameter 1 with operator 1 and prior 2
	    	Parameter.Default x1 = new Parameter.Default(new double[]{2.0});
	    	x1.setID("x1");
	    	Normal normal = new Normal();
	    	normal.setID("normal");
	    	normal.initByName("mean","1.0","sigma","1");
	    	Prior prior = new Prior();
	    	prior.setID("prior");
	    	prior.initByName("x", x1, "distr", normal);
	
	    	ScaleOperator operator = new ScaleOperator(x1, 0.75);
	    	operator.setID("scaleOperator");
	        x1.addBounds(0.0, Double.POSITIVE_INFINITY);
//	    	operator.proposal();
//	    	double v = x1.getArrayValue();
//	    	assertNotSame(v, 2.0);
//	    	operator.accept();
//	    	operator.proposal();
//	    	assertNotSame(x1.getValue(0), v);
//	    	operator.accept();
	
	    	Logger logger = new Logger();
	    	logger.setID("screenlog");
	    	logger.initByName("log", x1, "log", prior, "logEvery", 100, "mode", Logger.LOGMODE.compound,
	    			"fileName", "/tmp/x.log"
	    			);
	    	
	    	Randomizer.setSeed(123);	
	    	Logger.FILE_MODE = Logger.FILE_MODE.overwrite;
	    	MCMC mcmc = new MCMC();
	    	mcmc.setID("mcmc");
	    	mcmc.initByName("chainLength", 100000L, "operator", operator, "distribution", prior, "logger", logger);
	
	//    	XMLProducer producer = new XMLProducer();
	//        FileWriter outfile = new FileWriter(new File("/tmp/testMCMC.xml"));
	//        outfile.write(producer.toXML(mcmc));
	//        outfile.close();
	//
	//        XMLParser parser = new XMLParser();
	//        mcmc = (MCMC) parser.parseFile(new File("/tmp/testMCMC.xml"));
	
	    	mcmc.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
