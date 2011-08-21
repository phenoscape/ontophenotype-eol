package ontology;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;
import org.phenoscape.ontophenotype.ontology.AnatomyOntologyProcessor;
import org.phenoscape.ontophenotype.util.TextConstants;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class OntologyProcessorTest {

	/**
	 * Tests the reasoner for finding all the component classes of an owl class.
	 * For this test case, it is used the "head" class.
	 * This test requires an internet connection.
	 * 
	 * @throws OWLOntologyCreationException
	 */
	@Test
	public void testCommonAnnotations() throws OWLOntologyCreationException {

		AnatomyOntologyProcessor ontoProcessor = new AnatomyOntologyProcessor(
				TextConstants.TAO_ONTOLOGY_URL);

		Set<String> subClaseesIRIs = ontoProcessor
				.findComponentClasses("http://purl.obolibrary.org/obo/TAO_0001114");

		assertEquals(480, subClaseesIRIs.size());

	}

}
