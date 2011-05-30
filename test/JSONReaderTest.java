import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.phenoscape.ontophenotype.input.JSONReader;
import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;


public class JSONReaderTest {
	
	/**
	 * Tests the JSON Reader on a basic test case, using a taxon with 3 annotations
	 */
	@Test
	public void testJSONReaderBasic() {
		JSONReader jsonReader = new JSONReader();
		
		AnnotationCollection annotationCollection = jsonReader.readFile("test\\resources\\Taxon Annotations part.json");
		
		assertEquals(700, annotationCollection.getTotal());
		assertEquals(3, annotationCollection.getAnnotations().size());		
	}
	
	/**
	 * Tests the JSON Reader on a JSON file containing all taxon phenotype annotations
	 */
	@Test
	public void testJSONReaderFullTaxon() {
		JSONReader jsonReader = new JSONReader();
		
		AnnotationCollection annotationCollection = jsonReader.readFile("test\\resources\\Taxon Annotations.json");
		
		assertEquals(700, annotationCollection.getTotal());
		assertEquals(700, annotationCollection.getAnnotations().size());		
	}

}
