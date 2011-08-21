import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.phenoscape.ontophenotype.input.JSONReader;
import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;
import org.phenoscape.ontophenotype.phenotype.Taxon;

public class TaxonListExtractorTest {	

	@Test
	public void testTaxonListExtractor() {
		JSONReader jsonReader = new JSONReader();

		AnnotationCollection annotationCollection = jsonReader
				.readFile("test\\test resources\\Ictalurus.json");
		List<Taxon> annotatedTaxa = annotationCollection.getAnnotatedTaxa();
		
		for (Taxon taxon : annotatedTaxa) {
			System.out.println(" name " + taxon.getName() + "ann "
					+ taxon.getPhenotypeAnnotations().size());
		}

		assertEquals(9, annotatedTaxa.size());		

	}

}
