
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;

import org.phenoscape.ontophenotype.input.JSONReader;

import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;
import org.phenoscape.ontophenotype.phenotype.Taxon;
import org.phenoscape.ontophenotype.text.BasicTextGenerator;
import org.phenoscape.ontophenotype.text.PatternFileReader;
import org.phenoscape.ontophenotype.text.TextAugmentedTaxon;
import org.xml.sax.SAXException;

public class BasicTextGeneratorTest {

	@Test
	public void testCommonAnnotations() throws ParserConfigurationException, SAXException, IOException {
		JSONReader jsonReader = new JSONReader();

		AnnotationCollection annotationCollection = jsonReader
				.readFile("test\\test resources\\Ictalurus_punctatus.json");
		List<Taxon> annotatedTaxa = annotationCollection.getAnnotatedTaxa();

		BasicTextGenerator textGenerator = new BasicTextGenerator();

		Map<String, List<String>> qualityToPatternMap = PatternFileReader
				.parsePatternFile("test\\test resources\\text_patterns_test.xml");

		TextAugmentedTaxon textAugmentedTaxon = textGenerator
				.buildText(annotatedTaxa.get(0), qualityToPatternMap, true);
		System.out.println(textAugmentedTaxon.getName() + " - "
				+ textAugmentedTaxon.getDcIdentifier() + " - "
				+ textAugmentedTaxon.getPhenotypeDescription());
	}

}
