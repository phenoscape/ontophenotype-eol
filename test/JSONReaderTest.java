import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.phenoscape.ontophenotype.input.JSONReader;
import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;

public class JSONReaderTest {

	/**
	 * Tests the JSON Reader on a basic test case, using the Ictalurus_punctatus
	 * taxon
	 */
	@Test
	public void testJSONReaderBasic() {
		JSONReader jsonReader = new JSONReader();

		AnnotationCollection annotationCollection = jsonReader
				.readFile("test\\test resources\\Ictalurus_punctatus.json");

		assertEquals(700, annotationCollection.getTotal());

	}

}
