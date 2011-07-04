import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.phenoscape.ontophenotype.eol.ExportFileWriter;
import org.phenoscape.ontophenotype.input.JSONReader;
import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;
import org.phenoscape.ontophenotype.phenotype.Taxon;
import org.phenoscape.ontophenotype.text.BasicTextGenerator;
import org.phenoscape.ontophenotype.text.TextAugmentedTaxon;

public class ExportFileWriterTest {

	@Test
	public void testCommonAnnotations() {
		JSONReader jsonReader = new JSONReader();

		AnnotationCollection annotationCollection = jsonReader
				.readFile("test\\resources\\Ictalurus_punctatus.json");
		List<Taxon> annotatedTaxa = annotationCollection.getAnnotatedTaxa();

		BasicTextGenerator textGenerator = new BasicTextGenerator();

		TextAugmentedTaxon textAugmentedTaxon = textGenerator
				.buildSampleText(annotatedTaxa.get(0));

		ExportFileWriter exportFileWriter = new ExportFileWriter();
		
		try {
			exportFileWriter.writeTaxonExportFile(textAugmentedTaxon,
					"test\\results\\EoL_export_file.xml");
		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		}
	}

}
