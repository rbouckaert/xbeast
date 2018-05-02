package test.xbeast.core;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import beast.core.Logger;
import beast.core.MCMC;
import beast.math.distributions.Normal;
import beast.math.distributions.Prior;
import dr.inference.model.Parameter;
import dr.inference.operators.ScaleOperator;
import junit.framework.TestCase;

public class ParameterMCMCTest extends TestCase {
	
	@Test
	public void testMCMC() throws IOException, SAXException, ParserConfigurationException {
		// run MCMC 2 on parameter 1 with operator 1 and prior 2
    	Parameter.Default x1 = new Parameter.Default(new double[]{2.0});
    	Normal normal = new Normal();
    	normal.initByName("mean","1.0","sigma","1");
    	Prior prior = new Prior();
    	prior.initByName("x", x1, "distr", normal);

    	ScaleOperator operator = new ScaleOperator(x1, 0.75);
    	x1.addBounds(0.0, Double.POSITIVE_INFINITY);
    	operator.proposal();
    	double v = x1.getArrayValue();
    	assertNotSame(v, 2.0);
    	operator.accept();
    	operator.proposal();
    	assertNotSame(x1.getValue(0), v);

    	Logger logger = new Logger();
    	logger.initByName("log", x1, "log", prior, "logEvery", 100, "mode", Logger.LOGMODE.compound);
    	
    	MCMC mcmc = new MCMC();
    	mcmc.initByName("chainLength", 1000L, "operator", operator, "distribution", prior, "logger", logger);
    	mcmc.run();
	}

}
