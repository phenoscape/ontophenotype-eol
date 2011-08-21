package org.phenoscape.ontophenotype.phenotype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class AnnotationCollection {

	private int total;
	private List<Annotation> annotations;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	/**
	 * Groups all the annotations by taxon.
	 * 
	 * @return annotated taxa
	 */
	public List<Taxon> getAnnotatedTaxa() {
		List<Taxon> annotatedTaxa = new ArrayList<Taxon>();

		for (Annotation annotation : annotations) {
			Taxon currentTaxon = annotation.getTaxon();

			if (annotatedTaxa.contains(currentTaxon)) {

				Taxon annotatedTaxon = annotatedTaxa.get(annotatedTaxa
						.indexOf(currentTaxon));
				annotatedTaxon.fillAnnotationCollections(annotation);
			} else {
				currentTaxon
						.setPhenotypeAnnotations(new ArrayList<Annotation>());
				currentTaxon.setEntitiesIDsInAnnotations(new HashSet<String>());
				currentTaxon.setEntityIdAnnotationMap(new HashMap<String, Annotation>());
				currentTaxon.fillAnnotationCollections(annotation);
				annotatedTaxa.add(currentTaxon);
			}
		}

		return annotatedTaxa;
	}

}
