package org.phenoscape.ontophenotype.compare;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;


import org.phenoscape.ontophenotype.phenotype.Annotation;
import org.phenoscape.ontophenotype.phenotype.Taxon;

public class TaxaComparer {

	public static List<Annotation> getSimmilarAnnotations(
			List<Taxon> annotatedTaxa) {

		HashSet<Annotation> commonAnnotationsSet = new HashSet<Annotation>();
		commonAnnotationsSet.addAll(annotatedTaxa.get(0)
				.getPhenotypeAnnotations());

		for (Taxon taxon : annotatedTaxa) {
			HashSet<Annotation> annotationSet = new HashSet<Annotation>();
			annotationSet.addAll(taxon.getPhenotypeAnnotations());

			commonAnnotationsSet.retainAll(annotationSet);
		}
		return new ArrayList<Annotation>(commonAnnotationsSet);
	}

	public static List<Annotation> getDifferentiatingAnnotations(
			Taxon baseTaxon, List<Taxon> annotatedTaxa) {

		HashSet<Annotation> differentiatingAnnotationsSet = new HashSet<Annotation>();
		differentiatingAnnotationsSet.addAll(baseTaxon
				.getPhenotypeAnnotations());

		for (Taxon taxon : annotatedTaxa) {
			HashSet<Annotation> annotationSet = new HashSet<Annotation>();
			annotationSet.addAll(taxon.getPhenotypeAnnotations());

			if (!taxon.equals(baseTaxon)) {
				differentiatingAnnotationsSet = setDifference(
						differentiatingAnnotationsSet, annotationSet);
			}
		}

		return new ArrayList<Annotation>(differentiatingAnnotationsSet);
	}

	private static HashSet<Annotation> setDifference(HashSet<Annotation> setA,
			HashSet<Annotation> setB) {

		HashSet<Annotation> tmp = new HashSet<Annotation>(setA);
		tmp.removeAll(setB);
		return tmp;
	}

}
