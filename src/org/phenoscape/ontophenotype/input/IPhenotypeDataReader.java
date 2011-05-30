package org.phenoscape.ontophenotype.input;

import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;

public interface IPhenotypeDataReader {
	
	public AnnotationCollection readFile(String filePath);

}
