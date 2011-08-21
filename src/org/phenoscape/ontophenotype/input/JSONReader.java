package org.phenoscape.ontophenotype.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;
import org.phenoscape.ontophenotype.util.OntophenotypeLogger;

import com.google.gson.Gson;

public class JSONReader implements IPhenotypeDataReader {

	protected Gson gson;

	public int lineCountStep = 3000;

	@Override
	public AnnotationCollection readFile(String filePath) {

		String jsonText = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String line;

			int lineCount = 1;
			while ((line = in.readLine()) != null) {
				jsonText += line;

				lineCount++;
			}
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}

		gson = new Gson();
		AnnotationCollection annotationCollection = gson.fromJson(jsonText,
				AnnotationCollection.class);

		return annotationCollection;
	}

	/**
	 * Method used if a large JSON file is used as input. This method breaks the
	 * file into smaller pieces using buffers, reconstructs the correct JSON
	 * formatting and returns an AnnotationCollection object for each piece.
	 * 
	 * @param file
	 *            the JSON file with the annotated taxa.
	 * @return a list of AnnotationCollection objects containing all the taxa in
	 *         the input file
	 * @throws IOException
	 */
	public List<AnnotationCollection> readBigFile(String file)
			throws IOException {

		List<AnnotationCollection> annotationCollections = new ArrayList<AnnotationCollection>();

		gson = new Gson();

		FileInputStream fis = new FileInputStream(file);
		byte buf[] = new byte[(int) new File(file).length()];

		int cnt = 0;
		int n;

		n = fis.read(buf);

		byte[] partialBuf = new byte[1024000];

		int iThreshold = 0;
		for (int i = 0; i < n; i++) {

			partialBuf[i - iThreshold] = buf[i];

			if (buf[i] == '\n') {
				cnt++;

				if (cnt > lineCountStep) {

					iThreshold = i + 1;

					cnt = 0;

					String partialText = new String(partialBuf).trim();

					if (!partialText.contains("annotations")) {

						partialText = "{\"annotations\":[" + partialText;
					}
					if (partialText.charAt(partialText.length() - 1) == ',') {

						partialText = partialText.substring(0,
								partialText.length() - 1);

						partialText += "]}";
					}

					OntophenotypeLogger.info("Processing component number "
							+ annotationCollections.size() + " ... \n");

					AnnotationCollection annotationCollection = gson.fromJson(
							partialText, AnnotationCollection.class);

					annotationCollections.add(annotationCollection);

					partialBuf = new byte[1024000];

				}
			}
		}

		fis.close();

		return annotationCollections;

	}
}
