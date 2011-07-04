package org.phenoscape.ontophenotype.eol;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.phenoscape.ontophenotype.text.TextAugmentedTaxon;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class ExportFileWriter {

	public void writeTaxonExportFile(TextAugmentedTaxon textAugmentedTaxon,
			String outputFile) throws ParserConfigurationException {

		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element root = doc.createElement("response");
		doc.appendChild(root);

		root.setAttribute("xmlns", "http://www.eol.org/transfer/content/0.3");
		root.setAttribute("xmlns:xsd",
				"http://www.w3.org/2001/XMLSchema");
		root.setAttribute("xmlns:dc",
				"http://purl.org/dc/elements/1.1/");
		root.setAttribute("xmlns:dcterms",
				"http://purl.org/dc/terms/");
		root.setAttribute("xmlns:dwc",
				"http://rs.tdwg.org/dwc/dwcore/");
		root.setAttribute("xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:schemaLocation",
		"http://services.eol.org/schema/content_0_3.xsd");
		
		Element taxonElement = doc.createElement("taxon");
		root.appendChild(taxonElement);
		
		Element identifierElement = doc.createElement("dc:identifier");
		taxonElement.appendChild(identifierElement);		
		Text identifierText = doc.createTextNode(textAugmentedTaxon.getId()); 
		identifierElement.appendChild(identifierText);
		
		Element scientificNameElement = doc.createElement("dwc:ScientificName");
		taxonElement.appendChild(scientificNameElement);
		Text scientificNameText = doc.createTextNode(textAugmentedTaxon.getName()); 
		scientificNameElement.appendChild(scientificNameText);
		
		Element dataObjectElement = doc.createElement("dataObject");
		taxonElement.appendChild(dataObjectElement);
		
		Element dataTypeElement = doc.createElement("dataType");
		dataObjectElement.appendChild(dataTypeElement);
		Text dataTypeText = doc.createTextNode("http://purl.org/dc/dcmitype/Text");
		dataTypeElement.appendChild(dataTypeText);
		
		Element subjectElement = doc.createElement("subject");
		dataObjectElement.appendChild(subjectElement);
		Text subjectElementText = doc.createTextNode("http://rs.tdwg.org/ontology/voc/SPMInfoItems#Morphology");
		subjectElement.appendChild(subjectElementText);
		
		Element descriptionElement = doc.createElement("dc:description");
		descriptionElement.setAttribute("xml:lang", "en");
		dataObjectElement.appendChild(descriptionElement);
		Text descriptionElementText = doc.createTextNode(textAugmentedTaxon.getPhenotypeDescription());
		descriptionElement.appendChild(descriptionElementText);
		
		
		writeXmlFile(doc, outputFile);

	}

	private void writeXmlFile(Document doc, String filename) {
		try {
			
			Source source = new DOMSource(doc);

			File file = new File(filename);
			Result result = new StreamResult(file);

			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			xformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}

	}

}
