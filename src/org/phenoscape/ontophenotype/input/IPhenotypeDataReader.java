package org.phenoscape.ontophenotype.input;

import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
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
