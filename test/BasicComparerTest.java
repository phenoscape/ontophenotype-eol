import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.phenoscape.ontophenotype.compare.TaxaComparer;
import org.phenoscape.ontophenotype.input.JSONReader;
import org.phenoscape.ontophenotype.phenotype.Annotation;
import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;
import org.phenoscape.ontophenotype.phenotype.Taxon;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class BasicComparerTest {

	@Test
	public void testCommonAnnotations() {
		JSONReader jsonReader = new JSONReader();

		AnnotationCollection annotationCollection = jsonReader
				.readFile("test/test resources/Ictalurus.json");
		List<Taxon> annotatedTaxa = annotationCollection.getAnnotatedTaxa();

		List<Annotation> commonAnnotations = TaxaComparer
				.getSimmilarAnnotations(annotatedTaxa);
		System.out.println("size " + commonAnnotations.size());

		assertEquals(3, commonAnnotations.size());

		for (Annotation annotation : commonAnnotations) {
			System.out.println("Common annotation " + annotation);
		}
	}

	@Test
	public void testDifferentiatingAnnotations() {
		JSONReader jsonReader = new JSONReader();

		AnnotationCollection annotationCollection = jsonReader
				.readFile("test\\test resources\\Ictalurus.json");
		List<Taxon> annotatedTaxa = annotationCollection.getAnnotatedTaxa();

		AnnotationCollection baseTaxonAnnotationCollection = jsonReader
				.readFile("test\\test resources\\Ictalurus_punctatus.json");
		List<Taxon> baseTaxonAnnotatedTaxa = baseTaxonAnnotationCollection
				.getAnnotatedTaxa();

		List<Annotation> differentiatingAnnotations = TaxaComparer
				.getDifferentiatingAnnotations(baseTaxonAnnotatedTaxa.get(0),
						annotatedTaxa);

		 assertEquals(234, differentiatingAnnotations.size());

		for (Annotation annotation : differentiatingAnnotations) {
			System.out.println("Differentiating annotation " + annotation);
		}
	}

}
