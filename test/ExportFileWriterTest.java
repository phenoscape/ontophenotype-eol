import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.phenoscape.ontophenotype.eol.ExportFileWriter;
import org.phenoscape.ontophenotype.input.JSONReader;
import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;
import org.phenoscape.ontophenotype.phenotype.Taxon;
import org.phenoscape.ontophenotype.text.PatternFileReader;
import org.xml.sax.SAXException;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class ExportFileWriterTest {

	/**
	 * This test requires an internet connection.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void testTaxaCollectionExportFileWriter()
			throws ParserConfigurationException, SAXException, IOException {
		JSONReader jsonReader = new JSONReader();

		System.out.println("before json reader ");
		AnnotationCollection annotationCollection = jsonReader
				.readFile("test/test resources/Ictalurus.json");
		List<Taxon> annotatedTaxa = annotationCollection.getAnnotatedTaxa();

		System.out.println("after json reader ");

		Map<String, List<String>> qualityToPatternMap = PatternFileReader
				.parsePatternFile("/test resources/text_patterns_test.xml");

		ExportFileWriter exportFileWriter = new ExportFileWriter();

		try {
			exportFileWriter.writeTaxaCollectionExportFile(annotatedTaxa,
					qualityToPatternMap,
					"test/test results/EoL_taxa_export_file_links.xml", null,
					true, true);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

}
