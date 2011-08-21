package org.phenoscape.ontophenotype.ontology;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.phenoscape.ontophenotype.phenotype.Annotation;
import org.phenoscape.ontophenotype.phenotype.Taxon;
import org.phenoscape.ontophenotype.util.AnatomicalSystem;
import org.phenoscape.ontophenotype.util.OntophenotypeLogger;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class AnatomicalSystemBinder {

	/** instance of the AnatomicalSystemBinder singleton class */
	private static AnatomicalSystemBinder anatomicalSystemBinder;

	private AnatomyOntologyProcessor ontologyProcessor;
	private Map<AnatomicalSystem, Set<String>> classesIRIsByAnatomicalSystem = new HashMap<AnatomicalSystem, Set<String>>();

	private AnatomicalSystemBinder(File ontologyFile) {
		ontologyProcessor = new AnatomyOntologyProcessor(ontologyFile);
		try {
			fillClassesIRIsMap();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}

	private AnatomicalSystemBinder(String ontologyURL) {
		ontologyProcessor = new AnatomyOntologyProcessor(ontologyURL);
		try {
			fillClassesIRIsMap();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}

	public static AnatomicalSystemBinder getAnatomicalSystemBinder(
			String ontologyURL) {
		if (anatomicalSystemBinder == null) {
			anatomicalSystemBinder = new AnatomicalSystemBinder(ontologyURL);
		}
		return anatomicalSystemBinder;
	}

	public static AnatomicalSystemBinder getAnatomicalSystemBinder(
			File ontologyFile) {
		if (anatomicalSystemBinder == null) {
			anatomicalSystemBinder = new AnatomicalSystemBinder(ontologyFile);
		}
		return anatomicalSystemBinder;
	}

	/**
	 * Groups the annotations of a taxon in anatomical systems. An annotation is
	 * classified based on its primary anatomical entity.
	 * 
	 * @param taxon
	 * @return a mapping between an anatomical system and a list of annotations
	 */
	public Map<AnatomicalSystem, List<Annotation>> getAnnotationsByAnatomicalSystem(
			Taxon taxon) {

		Map<AnatomicalSystem, List<Annotation>> annotationsByAnatomicalSystemMap = new HashMap<AnatomicalSystem, List<Annotation>>();

		for (AnatomicalSystem anatomicalSystem : classesIRIsByAnatomicalSystem
				.keySet()) {

			Set<String> intersectionIRISet = new HashSet<String>(
					classesIRIsByAnatomicalSystem.get(anatomicalSystem));
			intersectionIRISet.retainAll(taxon.getEntitiesIDsInAnnotations());

			List<Annotation> intersectionAnnotations = new ArrayList<Annotation>();

			Iterator<String> iIRI = intersectionIRISet.iterator();
			while (iIRI.hasNext()) {
				String intersectionIRI = iIRI.next();

				Annotation intersectionAnnotation = taxon
						.getEntityIdAnnotationMap().get(intersectionIRI);
				intersectionAnnotations.add(intersectionAnnotation);
			}

			annotationsByAnatomicalSystemMap.put(anatomicalSystem,
					intersectionAnnotations);

		}

		return annotationsByAnatomicalSystemMap;
	}

	/**
	 * Groups the anatomical entities represented by owl classes from the
	 * Teleost ontology in anatomical systems. Method is used in class
	 * constructor. The AnatomicalSystemBinder is a Singleton class and this
	 * method is called only once in a normal application run cycle.
	 * 
	 * @throws OWLOntologyCreationException
	 */
	private void fillClassesIRIsMap() throws OWLOntologyCreationException {
		for (AnatomicalSystem anatomicalSystem : AnatomicalSystem.values()) {

			Set<String> subClassesIRIs = ontologyProcessor
					.findComponentClasses(anatomicalSystem.iri());

			Set<String> subClassesTAOIds = convertToTAOId(subClassesIRIs);

			classesIRIsByAnatomicalSystem.put(anatomicalSystem,
					subClassesTAOIds);

			OntophenotypeLogger.info("Retrieving components of the "
					+ anatomicalSystem.name() + " ... \n");
		}

	}

	/**
	 * Extracts the TAO id from the owl class IRI and converts it to the its
	 * Phenoscape representation.
	 * 
	 * @param subClassesIRIs
	 * @return
	 */
	private Set<String> convertToTAOId(Set<String> subClassesIRIs) {

		Set<String> subClassesTAOIds = new HashSet<String>();

		Iterator<String> iIRI = subClassesIRIs.iterator();
		while (iIRI.hasNext()) {
			String iri = iIRI.next();

			String[] iriComponents = iri.split("/");
			String taoID = iriComponents[iriComponents.length - 1].replace("_",
					":");

			subClassesTAOIds.add(taoID);
		}

		return subClassesTAOIds;

	}

}
