package ontology;

import java.util.List;

import org.junit.Test;
import org.phenoscape.ontophenotype.input.JSONReader;
import org.phenoscape.ontophenotype.ontology.AnatomicalSystemBinder;
import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;
import org.phenoscape.ontophenotype.phenotype.Taxon;
import org.phenoscape.ontophenotype.util.TextConstants;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class AnatomicalSystemBinderTest {

	/**
	 * Complete test for the AnatomicalSystemBinder class that groups anatomical
	 * entities in anatomical systems using the Teleost ontology.
	 * 
	 * @throws OWLOntologyCreationException
	 */
	@Test
	public void testCommonAnnotations() throws OWLOntologyCreationException {

		AnatomicalSystemBinder binder = AnatomicalSystemBinder
				.getAnatomicalSystemBinder(TextConstants.TAO_ONTOLOGY_URL);

		System.out.println("after binder create() ");

		JSONReader jsonReader = new JSONReader();

		AnnotationCollection annotationCollection = jsonReader
				.readFile("test\\resources\\Ictalurus_punctatus.json");
		List<Taxon> annotatedTaxa = annotationCollection.getAnnotatedTaxa();

		binder.getAnnotationsByAnatomicalSystem(annotatedTaxa.get(0));

	}

}
