package ontology;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;
import org.phenoscape.ontophenotype.ontology.AnatomyOntologyProcessor;
import org.phenoscape.ontophenotype.util.TextConstants;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class OntologyProcessorTest {

	/**
	 * Tests the reasoner for finding all the component classes of an owl class.
	 * For this test case, it is used the "head" class.
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
