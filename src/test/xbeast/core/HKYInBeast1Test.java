package test.xbeast.core;



import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Test;

import dr.evolution.alignment.Patterns;
import dr.evolution.alignment.SimpleAlignment;
import dr.evomodel.coalescent.CoalescentSimulator;
import dr.evolution.datatype.Nucleotides;
import dr.evolution.sequence.Sequence;
import dr.evolution.util.Taxa;
import dr.evolution.util.Taxon;
import dr.evolution.util.Units.Type;
import dr.evomodel.branchmodel.BranchModel;
import dr.evomodel.branchmodel.HomogeneousBranchModel;
import dr.evomodel.branchratemodel.DefaultBranchRateModel;
import dr.evomodel.coalescent.ConstantPopulationModel;
import dr.evomodel.operators.ExchangeOperator;
import dr.evomodel.operators.SubtreeSlideOperator;
import dr.evomodel.operators.WilsonBalding;
import dr.evomodel.siteratemodel.GammaSiteRateModel;
import dr.evomodel.substmodel.FrequencyModel;
import dr.evomodel.substmodel.nucleotide.HKY;
import dr.evomodel.tree.RootHeightLogger;
import dr.evomodel.tree.TreeLogger;
import dr.evomodel.tree.TreeModel;
import dr.evomodel.treelikelihood.BeagleTreeLikelihood;
import dr.evomodel.treelikelihood.PartialsRescalingScheme;
import dr.inference.loggers.Loggable;
import dr.inference.loggers.MCLogger;
import dr.inference.mcmc.MCMC;
import dr.inference.model.CompoundLikelihood;
import dr.inference.model.Likelihood;
import dr.inference.model.Parameter;
import dr.inference.operators.CoercionMode;
import dr.inference.operators.MCMCOperator;
import dr.inference.operators.ScaleOperator;
import dr.inference.operators.SimpleOperatorSchedule;
import dr.inference.operators.UniformOperator;
import junit.framework.TestCase;
import xbeast.core.BEASTInterface;
import xbeast.core.BEASTObject;
import xbeast.util.JSONParser;
import xbeast.util.JSONParserException;
import xbeast.util.JSONProducer;
import xbeast.util.XMLParser;
import xbeast.util.XMLParserException;
import xbeast.util.XMLProducer;

public class HKYInBeast1Test extends TestCase {

	
	
	@Test
	public void testHKYInBeast1() throws JSONException, IOException, XMLParserException {
		Parameter.Default kappaParameter = new Parameter.Default(2.0);
		kappaParameter.setId("kappa");
		Parameter.Default frequencyParameter = new Parameter.Default(new Double[]{0.25,0.25,0.25,0.25});
		dr.evolution.datatype.DataType dataType = Nucleotides.INSTANCE;
		FrequencyModel freqModel = new FrequencyModel(dataType, frequencyParameter);
		freqModel.setId("frequencies");
		HKY hky = new HKY(kappaParameter, freqModel);
		hky.setId("HKY");
				
        Parameter mu = new Parameter.Default(1, 0.5);
        Parameter alpha = new Parameter.Default(1, 0.5);
        GammaSiteRateModel siteModel = new GammaSiteRateModel(hky, mu, alpha, 4, null);
        siteModel.setSubstitutionModel(hky);
        
        DefaultBranchRateModel branchRateModel = new DefaultBranchRateModel();

        Taxon human = new Taxon("human");
        Taxon chimp = new Taxon("chimp");
        Taxon bonobo = new Taxon("bonobo");
        List<Taxon> list = new ArrayList<>();
        list.add(human); list.add(chimp); list.add(bonobo);
        Taxa taxa = new Taxa(list);
        
        Sequence seq1 = new Sequence(human,  "AGAAATATGTCTGATAAAAGAGTTACTTTGATAGAGTAAATAATAGGAGCTTAAACCCCCTTATTTCTACTAGGACTATGAGAATCGAACCCATCCCTGAGAATCCAAAATTCTCCGTGCCACCTATCACACCCCATCCTAAGTAAGGTCAGCTAAATAAGCTATCGG");
        Sequence seq2 = new Sequence(chimp,  "AGAAATATGTCTGATAAAAGAATTACTTTGATAGAGTAAATAATAGGAGTTCAAATCCCCTTATTTCTACTAGGACTATAAGAATCGAACTCATCCCTGAGAATCCAAAATTCTCCGTGCCACCTATCACACCCCATCCTAAGTAAGGTCAGCTAAATAAGCTATCGG");
        Sequence seq3 = new Sequence(bonobo, "AGAAATATGTCTGATAAAAGAATTACTTTGATAGAGTAAATAATAGGAGTTTAAATCCCCTTATTTCTACTAGGACTATGAGAGTCGAACCCATCCCTGAGAATCCAAAATTCTCCGTGCCACCTATCACACCCCATCCTAAGTAAGGTCAGCTAAATAAGCTATCGG");
        List<Sequence> seqs = new ArrayList<>();
        seqs.add(seq1); seqs.add(seq2); seqs.add(seq3);
        SimpleAlignment alignment = new SimpleAlignment(seqs, Nucleotides.INSTANCE);
        
        Patterns patternList = new Patterns(alignment);
        patternList.setId("patterns");
        
        Parameter.Default popSizeParameter = new Parameter.Default(0.0001);
        
        ConstantPopulationModel constantSize = new ConstantPopulationModel(popSizeParameter, Type.YEARS);
        CoalescentSimulator.Base coalescentSimulator = new CoalescentSimulator().new Base(taxa, constantSize);
		
//        Parameter.Default rootHeight = new Parameter.Default(1.0);
//        Parameter.Default internalNodes = new Parameter.Default(1.0);
//        Parameter.Default allInternalNodeHeights = new Parameter.Default(1.0);
        
        
        TreeModel treeModel = new TreeModel("treeModel", coalescentSimulator);
        treeModel.setId("tree");
        //Parameter rootHeight = treeModel.getRootHeightParameter();        
        //rootHeight.setId("rootHeight");
        //Parameter internalNodes = treeModel.createNodeHeightsParameter(false, true, false);
        //Parameter allInternalNodeHeights = treeModel.createNodeHeightsParameter(true, true, false);

        
        BranchModel branchModel = new HomogeneousBranchModel(hky, freqModel);

       
        BeagleTreeLikelihood treeLikelihood = new BeagleTreeLikelihood(patternList,
        		treeModel,
        		branchModel, // branchModel,
                siteModel,
                branchRateModel,
                //null, // tipStateModel
                false,
                PartialsRescalingScheme.DEFAULT,
                true);

        ScaleOperator kappaScaleOperator = new ScaleOperator(kappaParameter, 0.5, CoercionMode.DEFAULT, 1.0);
        ScaleOperator rootScaleOperator = new ScaleOperator(treeModel, 0.5, CoercionMode.DEFAULT, 1.0);
        UniformOperator internalNodeUniformOperator = new UniformOperator(treeModel, 10);
        SubtreeSlideOperator subtreeSlideOperator = new SubtreeSlideOperator(treeModel, 5.0, 1.0);
        ExchangeOperator narrowExchangeOperator = new ExchangeOperator(0, treeModel, 1.0);        
        ExchangeOperator wideExchangeOperator = new ExchangeOperator(1, treeModel, 1.0);
        WilsonBalding wilsonBaldingOperator = new WilsonBalding(treeModel, 1.0);
        
        List<MCMCOperator> operators = new ArrayList<>();
        operators.add(kappaScaleOperator);
        operators.add(rootScaleOperator);
        operators.add(internalNodeUniformOperator);
        operators.add(subtreeSlideOperator);
        operators.add(narrowExchangeOperator);
        operators.add(wideExchangeOperator);
        operators.add(wilsonBaldingOperator);
        SimpleOperatorSchedule operatorSchedule = new SimpleOperatorSchedule(1000, 0.0, operators);
        
        
        List<Likelihood> likelihoods = new ArrayList<>();
        likelihoods.add(treeLikelihood);
        CompoundLikelihood compoundLikelihood = new CompoundLikelihood(likelihoods);
        compoundLikelihood.setId("likelihood");
        
        
//        LogColumn likelihoodColumn = new CompoundLikelihood.LikelihoodColumn(likelihoods);
//        LogColumn rootHeightColumn = new Statistic.Abstract.StatisticColumn(rootHeight);
//        LogColumn kappaColumn = new Statistic.Abstract.StatisticColumn(kappaParameter);        
//        List<LogColumn> columns = new ArrayList<>();
//        columns.add(likelihoodColumn);
//        columns.add(rootHeightColumn);
//        columns.add(kappaColumn);
        
        
        RootHeightLogger rootHeightLogger = new RootHeightLogger(treeModel);
        rootHeightLogger.setId("rootHeightLogger");
        
        
        List<Loggable> screenLog = new ArrayList<>();
        screenLog.add(compoundLikelihood);
        screenLog.add(kappaParameter);
        screenLog.add(rootHeightLogger);
        MCLogger screenLogger = new MCLogger(1000, screenLog);
        
        List<Loggable> traceLog = new ArrayList<>();
        traceLog.add(compoundLikelihood);
        traceLog.add(kappaParameter);
        traceLog.add(rootHeightLogger);
        MCLogger traceLogger = new MCLogger("testMCMC.log", 1000, traceLog);
        
        TreeLogger treeLogger = new TreeLogger("testMCMC.trees", 1000, treeModel, true);

        List<MCLogger> loggers = new ArrayList<>();
        loggers.add(screenLogger);
        loggers.add(traceLogger);
        loggers.add(treeLogger);
        
        MCMC mcmc = new MCMC(10000000, compoundLikelihood, operators.toArray(new MCMCOperator[]{}), 
        		loggers.toArray(new MCLogger[]{}));
        
        
        
        
        
        XMLProducer xmlp = new XMLProducer();
		String xml = xmlp.toXML(mcmc);
		System.err.println(xml);
        		
        FileWriter outfile = new FileWriter("/tmp/x.xml");
        outfile.write(xml);
        outfile.close();

		XMLParser parser = new XMLParser();
		BEASTInterface o = parser.parseFragment(xml, false);
		xml = xmlp.toXML((BEASTObject) o);
		System.err.println(xml);

		outfile = new FileWriter("/tmp/y.xml");
        outfile.write(xml);
        outfile.close();
		
		
//		JSONProducer producer = new JSONProducer();
//		String json = producer.toJSON(mcmc);
//		
//        FileWriter outfile = new FileWriter("/tmp/x.xml");
//        outfile.write(json);
//        outfile.close();
//
//		
//		System.err.println(json);
//		
//		JSONParser parser = new JSONParser();
//		List<Object> o = parser.parseFragment(json, false);
//		json = producer.toJSON((BEASTObject) o.get(0));
//		System.err.println(json);
//
//		outfile = new FileWriter("/tmp/y.xml");
//        outfile.write(json);
//        outfile.close();

	}
}
