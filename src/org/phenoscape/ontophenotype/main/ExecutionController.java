package org.phenoscape.ontophenotype.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.phenoscape.ontophenotype.eol.ExportFileWriter;
import org.phenoscape.ontophenotype.input.JSONReader;
import org.phenoscape.ontophenotype.phenotype.Annotation;
import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;
import org.phenoscape.ontophenotype.phenotype.Taxon;
import org.phenoscape.ontophenotype.text.PatternFileReader;
import org.phenoscape.ontophenotype.util.OntophenotypeLogger;
import org.phenoscape.ontophenotype.util.TextConstants;
import org.xml.sax.SAXException;

public class ExecutionController {

	public static void run(String inputFilePath, String exportFilePath,
			String ontologyFilePath, boolean useLinks, boolean groupEntities) {

		File inputFile = new File(inputFilePath);
		if (inputFile.length() > 1024000) {
			largeFileRun(inputFilePath, exportFilePath, ontologyFilePath,
					useLinks, groupEntities);
		} else {
			smallFileRun(inputFilePath, exportFilePath, ontologyFilePath,
					useLinks, groupEntities);
		}

	}

	private static void smallFileRun(String inputFilePath,
			String exportFilePath, String ontologyFilePath, boolean useLinks,
			boolean groupEntities) {

		JSONReader jsonReader = new JSONReader();

		OntophenotypeLogger.info("Parsing JSON file... \n");

		AnnotationCollection annotationCollection = jsonReader
				.readFile(inputFilePath);

		OntophenotypeLogger.info("Input file succesfully read. \n");

		List<Taxon> annotatedTaxa = annotationCollection.getAnnotatedTaxa();

		try {							
						
			Map<String, List<String>> qualityToPatternMap = PatternFileReader
					.parsePatternFile("/text_patterns.xml");

			ExportFileWriter exportFileWriter = new ExportFileWriter();

			exportFileWriter.writeTaxaCollectionExportFile(annotatedTaxa,
					qualityToPatternMap, exportFilePath, ontologyFilePath,
					useLinks, groupEntities);

		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private static void largeFileRun(String inputFilePath,
			String exportFilePath, String ontologyFilePath, boolean useLinks,
			boolean groupEntities) {

		OntophenotypeLogger
				.info("Large file detected (> 1024 KB) \n If necessary, please increase the JVM Heap Size. \n");

		JSONReader jsonReader = new JSONReader();

		OntophenotypeLogger
				.info("Breaking JSON file into smaller components ... \n");

		try {

			List<AnnotationCollection> annotationCollections = jsonReader
					.readBigFile(inputFilePath);

			OntophenotypeLogger
					.info("Finished processing the whole file ... \n");

			List<Taxon> annotatedTaxa = new ArrayList<Taxon>();

			for (AnnotationCollection annotationCollection : annotationCollections) {

				OntophenotypeLogger
						.info("Extracting information from component number "
								+ annotationCollections
										.indexOf(annotationCollection)
								+ " ... \n");

				//System.out.println(annotationCollections
						//.indexOf(annotationCollection));

				for (Annotation annotation : annotationCollection
						.getAnnotations()) {
					Taxon currentTaxon = annotation.getTaxon();

					if (annotatedTaxa.contains(currentTaxon)) {

						Taxon annotatedTaxon = annotatedTaxa.get(annotatedTaxa
								.indexOf(currentTaxon));
						annotatedTaxon.fillAnnotationCollections(annotation);
					} else {
						currentTaxon
								.setPhenotypeAnnotations(new ArrayList<Annotation>());
						currentTaxon
								.setEntitiesIDsInAnnotations(new HashSet<String>());
						currentTaxon
								.setEntityIdAnnotationMap(new HashMap<String, Annotation>());
						currentTaxon.fillAnnotationCollections(annotation);
						annotatedTaxa.add(currentTaxon);
					}
				}
			}

			Map<String, List<String>> qualityToPatternMap = PatternFileReader
					.parsePatternFile(TextConstants.PATTERNS_FILE_PATH);

			ExportFileWriter exportFileWriter = new ExportFileWriter();

			exportFileWriter.writeTaxaCollectionExportFile(annotatedTaxa,
					qualityToPatternMap, exportFilePath, ontologyFilePath,
					useLinks, groupEntities);

		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
