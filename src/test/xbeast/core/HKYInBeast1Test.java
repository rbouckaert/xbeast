package test.xbeast.core;



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
import dr.evomodel.branchratemodel.BranchRateModel;
import dr.evomodel.branchratemodel.DefaultBranchRateModel;
import dr.evomodel.coalescent.ConstantPopulationModel;
import dr.evomodel.siteratemodel.GammaSiteRateModel;
import dr.evomodel.substmodel.FrequencyModel;
import dr.evomodel.substmodel.nucleotide.HKY;
import dr.evomodel.tree.TreeModel;
import dr.inference.model.Parameter;
import junit.framework.TestCase;
import xbeast.core.BEASTObject;
import xbeast.util.JSONParser;
import xbeast.util.JSONParserException;
import xbeast.util.JSONProducer;
import xbeast.util.XMLProducer;

public class HKYInBeast1Test extends TestCase {

	
	
	@Test
	public void testHKYInBeast1() throws JSONParserException, JSONException {
		Parameter.Default kappaParameter = new Parameter.Default(2.0);
		Parameter.Default frequencyParameter = new Parameter.Default(new Double[]{0.25,0.25,0.25,0.25});
		dr.evolution.datatype.DataType dataType = Nucleotides.INSTANCE;
		FrequencyModel freqModel = new FrequencyModel(dataType, frequencyParameter);
		HKY hky = new HKY(kappaParameter, freqModel);
				
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
        
        Patterns patterns = new Patterns(alignment);
        
        Parameter.Default popSizeParameter = new Parameter.Default(0.0001);
        
        ConstantPopulationModel constantSize = new ConstantPopulationModel(popSizeParameter, Type.YEARS);
        CoalescentSimulator.Base coalescentSimulator = new CoalescentSimulator().new Base(taxa, constantSize);
		
        Parameter.Default rootHeight = new Parameter.Default(1.0);
        Parameter.Default internalNodes = new Parameter.Default(1.0);
        Parameter.Default allInternalNodeHeights = new Parameter.Default(1.0);
        
        
        TreeModel treeModel = new TreeModel("treeModel", coalescentSimulator);

        
        
        XMLProducer xmlp = new XMLProducer();
		String xml = xmlp.toXML(treeModel);
		System.err.println(xml);
        		

		
		
		JSONProducer producer = new JSONProducer();
		String json = producer.toJSON(treeModel);
		System.err.println(json);
		
		JSONParser parser = new JSONParser();
		List<Object> o = parser.parseFragment(json, false);
		json = producer.toJSON((BEASTObject) o.get(0));
		System.err.println(json);
		
	}
}
