package org.phenoscape.ontophenotype.eol;

import java.io.File;
import java.util.List;
import java.util.Map;

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

import org.phenoscape.ontophenotype.phenotype.Taxon;
import org.phenoscape.ontophenotype.text.BasicTextGenerator;
import org.phenoscape.ontophenotype.text.TextAugmentedTaxon;
import org.phenoscape.ontophenotype.util.OntophenotypeLogger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class ExportFileWriter {

	/**
	 * Initial version of the export file write method. It writes a single
	 * taxon. For proper use, see the writeTaxaCollectionExportFile method that
	 * writes a single export file for any number of taxa.
	 * 
	 * @param textAugmentedTaxon
	 * @param outputFile
	 * @throws ParserConfigurationException
	 */
	@Deprecated
	public void writeTaxonExportFile(TextAugmentedTaxon textAugmentedTaxon,
			String outputFile) throws ParserConfigurationException {

		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element root = doc.createElement("response");
		doc.appendChild(root);

		root.setAttribute("xmlns", "http://www.eol.org/transfer/content/0.3");
		root.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		root.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
		root.setAttribute("xmlns:dcterms", "http://purl.org/dc/terms/");
		root.setAttribute("xmlns:dwc", "http://rs.tdwg.org/dwc/dwcore/");
		root.setAttribute("xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:schemaLocation",
				"http://services.eol.org/schema/content_0_3.xsd");

		Element taxonElement = doc.createElement("taxon");
		root.appendChild(taxonElement);

		Element identifierElement = doc.createElement("dc:identifier");
		taxonElement.appendChild(identifierElement);
		Text identifierText = doc.createTextNode(textAugmentedTaxon
				.getDcIdentifier());
		identifierElement.appendChild(identifierText);

		Element scientificNameElement = doc.createElement("dwc:ScientificName");
		taxonElement.appendChild(scientificNameElement);
		Text scientificNameText = doc.createTextNode(textAugmentedTaxon
				.getName());
		scientificNameElement.appendChild(scientificNameText);

		Element dataObjectElement = doc.createElement("dataObject");
		taxonElement.appendChild(dataObjectElement);

		Element dataTypeElement = doc.createElement("dataType");
		dataObjectElement.appendChild(dataTypeElement);
		Text dataTypeText = doc
				.createTextNode("http://purl.org/dc/dcmitype/Text");
		dataTypeElement.appendChild(dataTypeText);

		Element subjectElement = doc.createElement("subject");
		dataObjectElement.appendChild(subjectElement);
		Text subjectElementText = doc
				.createTextNode("http://rs.tdwg.org/ontology/voc/SPMInfoItems#Morphology");
		subjectElement.appendChild(subjectElementText);

		Element descriptionElement = doc.createElement("dc:description");
		descriptionElement.setAttribute("xml:lang", "en");
		dataObjectElement.appendChild(descriptionElement);
		Text descriptionElementText = doc.createTextNode(textAugmentedTaxon
				.getPhenotypeDescription());
		descriptionElement.appendChild(descriptionElementText);

		writeXmlFile(doc, outputFile);

	}

	/**
	 * The main method for writing an export file from a list of taxa.
	 * 
	 * @param initialTaxa
	 *            the list of taxa objects.
	 * @param qualityToPatternMap
	 *            a mapping between a phenotype quality and a list of possible
	 *            patterns given by the PatternFileReader class
	 * @param outputFile
	 *            the path of the export file
	 * @param ontologyLocation
	 *            the location of the Teleost ontology owl file. If null, the
	 *            file will be retrieved using the purl link ; if a non null
	 *            String, then the ontologyLocation parameter should be the
	 *            local path of the .owl file
	 * @param useLinks
	 *            boolean value indicating whether links to anatomy terms
	 *            definitions should be used in the export file
	 * @param groupEntities
	 *            boolean value indicating whether anatomy terms should be
	 *            grouped in anatomical systems in the export file
	 * @throws ParserConfigurationException
	 */
	public void writeTaxaCollectionExportFile(List<Taxon> initialTaxa,
			Map<String, List<String>> qualityToPatternMap, String outputFile,
			String ontologyLocation, boolean useLinks, boolean groupEntities)
			throws ParserConfigurationException {

		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element root = doc.createElement("response");
		doc.appendChild(root);

		root.setAttribute("xmlns", "http://www.eol.org/transfer/content/0.3");
		root.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		root.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
		root.setAttribute("xmlns:dcterms", "http://purl.org/dc/terms/");
		root.setAttribute("xmlns:dwc", "http://rs.tdwg.org/dwc/dwcore/");
		root.setAttribute("xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:schemaLocation",
				"http://services.eol.org/schema/content_0_3.xsd");

		BasicTextGenerator textGenerator = new BasicTextGenerator();

		OntophenotypeLogger.info("Building the export file ... \n");

		for (Taxon initialTaxon : initialTaxa) {

			TextAugmentedTaxon textAugmentedTaxon;
			if (groupEntities) {
				textAugmentedTaxon = textGenerator.buildStructuredText(
						initialTaxon, qualityToPatternMap, ontologyLocation,
						useLinks);
			} else {
				textAugmentedTaxon = textGenerator.buildText(initialTaxon,
						qualityToPatternMap, useLinks);
			}

			if (!textAugmentedTaxon.getPhenotypeDescription().equals("")) {
				Element taxonElement = doc.createElement("taxon");
				root.appendChild(taxonElement);

				Element identifierElement = doc.createElement("dc:identifier");
				taxonElement.appendChild(identifierElement);
				Text identifierText = doc.createTextNode(textAugmentedTaxon
						.getDcIdentifier());
				identifierElement.appendChild(identifierText);

				Element scientificNameElement = doc
						.createElement("dwc:ScientificName");
				taxonElement.appendChild(scientificNameElement);
				Text scientificNameText = doc.createTextNode(textAugmentedTaxon
						.getName());
				scientificNameElement.appendChild(scientificNameText);

				Element dataObjectElement = doc.createElement("dataObject");
				taxonElement.appendChild(dataObjectElement);

				Element dataTypeElement = doc.createElement("dataType");
				dataObjectElement.appendChild(dataTypeElement);
				Text dataTypeText = doc
						.createTextNode("http://purl.org/dc/dcmitype/Text");
				dataTypeElement.appendChild(dataTypeText);

				Element sourceElement = doc.createElement("dc:source");
				dataObjectElement.appendChild(sourceElement);
				Text sourceElementText = doc.createTextNode(textAugmentedTaxon
						.getSourceURL());
				sourceElement.appendChild(sourceElementText);

				Element subjectElement = doc.createElement("subject");
				dataObjectElement.appendChild(subjectElement);
				Text subjectElementText = doc
						.createTextNode("http://rs.tdwg.org/ontology/voc/SPMInfoItems#Morphology");
				subjectElement.appendChild(subjectElementText);

				Element descriptionElement = doc
						.createElement("dc:description");
				descriptionElement.setAttribute("xml:lang", "en");
				dataObjectElement.appendChild(descriptionElement);
				Text descriptionElementText = doc
						.createTextNode(textAugmentedTaxon
								.getPhenotypeDescription());
				descriptionElement.appendChild(descriptionElementText);
			}
		}

		OntophenotypeLogger.info("Export file built with success ... \n");

		writeXmlFile(doc, outputFile);

	}

	/**
	 * Writes the DOM document to a file.
	 * 
	 * @param doc
	 *            the DOM document
	 * @param filename
	 *            the name of the file that the document should be written to
	 */
	private void writeXmlFile(Document doc, String filename) {
		try {

			OntophenotypeLogger.info("Starting to write export file ... \n");

			Source source = new DOMSource(doc);

			File file = new File(filename);
			Result result = new StreamResult(file);

			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			xformer.transform(source, result);

			OntophenotypeLogger.info("Export file wrote with success ... \n");
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}

	}

}
