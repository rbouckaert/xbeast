package test.beast.math.distributions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import beast.evolution.alignment.Alignment;
import beast.evolution.alignment.Taxon;
import beast.evolution.alignment.TaxonSet;
import beast.math.distributions.Exponential;
import beast.math.distributions.MRCAPrior;
import beast.util.TreeParser;
import junit.framework.TestCase;
import test.beast.BEASTTestCase;

public class MRCAPriorTest extends TestCase {

    @Test
    public void testSingleMonophyleticConstraint() throws Exception {
        Alignment data = BEASTTestCase.getAlignment();
        TreeParser tree = new TreeParser();
        tree.initByName("taxa", data,
                "newick", "((human:0.024003,(chimp:0.010772,bonobo:0.010772):0.013231):0.012035," +
                "(gorilla:0.024003,(orangutan:0.010772,siamang:0.010772):0.013231):0.012035);",
                "IsLabelledNewick", true);

        Taxon human = new Taxon();
        human.setId("human");
        Taxon bonobo = new Taxon();
        bonobo.setId("bonobo");
        Taxon chimp = new Taxon();
        chimp.setId("chimp");
        Taxon gorilla = new Taxon();
        gorilla.setId("gorilla");
        Taxon orangutan = new Taxon();
        orangutan.setId("orangutan");
        Taxon siamang = new Taxon();
        siamang.setId("siamang");

        MRCAPrior prior = new MRCAPrior();

        /* check (human, bonobo, chimp) is monophyletic **/
        TaxonSet set = new TaxonSet();
        set.initByName("taxon", human, "taxon", bonobo, "taxon", chimp);
        prior.initByName("tree", tree, "taxonset", set, "monophyletic", true);
        double logP = prior.calculateLogP();
        assertEquals(logP, 0, 0);

        /* check (gorilla, siamang) is NOT monophyletic **/
        set = new TaxonSet();
        set.initByName("taxon", gorilla, "taxon", siamang);
        prior.initByName("tree", tree, "taxonset", set, "monophyletic", true);
        logP = prior.calculateLogP();
        assertEquals(logP, Double.NEGATIVE_INFINITY, 0);

        /* check (gorilla, orangutan, siamang) is monophyletic **/
        set = new TaxonSet();
        set.initByName("taxon", gorilla, "taxon", orangutan, "taxon", siamang);
        prior.initByName("tree", tree, "taxonset", set, "monophyletic", true);
        logP = prior.calculateLogP();
        assertEquals(logP, 0, 0);

        /* check (human, gorilla) is NOT monophyletic **/
        set = new TaxonSet();
        set.initByName("taxon", human, "taxon", gorilla);
        prior.initByName("tree", tree, "taxonset", set, "monophyletic", true);
        logP = prior.calculateLogP();
        assertEquals(logP, Double.NEGATIVE_INFINITY, 0);

    
        set.setId("test");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        prior.init(ps);
        String log = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(log, "mrcatime(test)\t");

        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);
        prior.initByName("tree", tree, "taxonset", set, "monophyletic", true, "useOriginate", true);
        prior.init(ps);
        log = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(log, "mrcatime(test.originate)\t");
    }

    @Test
    public void testMRCATimePrior() throws Exception {
        Alignment data = BEASTTestCase.getAlignment();
        TreeParser tree = new TreeParser();
        tree.initByName("taxa", data,
                "newick", "((human:0.024003,(chimp:0.010772,bonobo:0.010772):0.013231):0.012035," +
                "(gorilla:0.024003,(orangutan:0.010772,siamang:0.010772):0.013231):0.012035);",
                "IsLabelledNewick", true);

        Taxon human = new Taxon();
        human.setId("human");
        Taxon bonobo = new Taxon();
        bonobo.setId("bonobo");
        Taxon chimp = new Taxon();
        chimp.setId("chimp");
        Taxon gorilla = new Taxon();
        gorilla.setId("gorilla");
        Taxon orangutan = new Taxon();
        orangutan.setId("orangutan");
        Taxon siamang = new Taxon();
        siamang.setId("siamang");

        MRCAPrior prior = new MRCAPrior();

        TaxonSet set = new TaxonSet();
        set.initByName("taxon", human, "taxon", bonobo, "taxon", chimp);
        Exponential exp = new Exponential();

        /* get distribution for set (human, bonobo, chimp) */
        prior.initByName("tree", tree, "taxonset", set, "monophyletic", true, "distr", exp);
        double logP = prior.calculateLogP();
        assertEquals(-0.024003, logP, BEASTTestCase.PRECISION);

        /* get distribution for set (human, chimp), do not require the set to by monophyletic */
        set = new TaxonSet();
        set.initByName("taxon", human, "taxon", chimp);
        prior.initByName("tree", tree, "taxonset", set, "monophyletic", false);
        logP = prior.calculateLogP();
        assertEquals(-0.024003, logP, BEASTTestCase.PRECISION);

        /* get distribution for set (human, chimp), DO require the set to by monophyletic */
        prior.initByName("tree", tree, "taxonset", set, "monophyletic", true);
        logP = prior.calculateLogP();
        assertEquals(Double.NEGATIVE_INFINITY, logP, 0);

        /* get distribution for set (human, gorilla) = root, not monophyletic */
        set = new TaxonSet();
        set.initByName("taxon", human, "taxon", gorilla);
        prior.initByName("tree", tree, "taxonset", set, "monophyletic", false);
        logP = prior.calculateLogP();
        assertEquals(-0.024003 - 0.012035, logP, BEASTTestCase.PRECISION);
    }

}
