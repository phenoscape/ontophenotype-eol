package org.phenoscape.ontophenotype.phenotype;

import java.util.ArrayList;
import java.util.List;

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
	 * Groups all the annotations by taxon
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
				annotatedTaxon.addAnnotaion(annotation);
			} else {
				currentTaxon
						.setPhenotypeAnnotations(new ArrayList<Annotation>());
				currentTaxon.addAnnotaion(annotation);
				annotatedTaxa.add(currentTaxon);
			}
		}

		return annotatedTaxa;
	}

}
