package org.phenoscape.ontophenotype.input;

import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;

public interface IPhenotypeDataReader {

	/**
	 * Interface method for reading an input file.
	 * 
	 * @param filePath
	 * @return an AnnotationCollection object for all the taxa in the input
	 *         file
	 */
	public AnnotationCollection readFile(String filePath);

}
