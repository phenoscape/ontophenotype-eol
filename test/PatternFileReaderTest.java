import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.phenoscape.ontophenotype.text.PatternFileReader;
import org.xml.sax.SAXException;

public class PatternFileReaderTest {

	@Test
	public void testJSONReaderBasic() throws ParserConfigurationException,
			SAXException, IOException {

		PatternFileReader.parsePatternFile("test\\resources\\text_patterns_test.xml");
	}

}
