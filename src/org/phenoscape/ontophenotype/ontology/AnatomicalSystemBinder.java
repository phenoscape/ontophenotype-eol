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

public class AnatomicalSystemBinder {
	
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
	
	public static AnatomicalSystemBinder getAnatomicalSystemBinder(String ontologyURL) {
		if (anatomicalSystemBinder == null) {
			anatomicalSystemBinder = new AnatomicalSystemBinder(ontologyURL);
		}
		return anatomicalSystemBinder;
	}
	
	public static AnatomicalSystemBinder getAnatomicalSystemBinder(File ontologyFile) {
		if (anatomicalSystemBinder == null) {
			anatomicalSystemBinder = new AnatomicalSystemBinder(ontologyFile);
		}
		return anatomicalSystemBinder;
	}


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
			
			annotationsByAnatomicalSystemMap.put(anatomicalSystem, intersectionAnnotations);
	        
			//System.out.println(anatomicalSystem.name() + " : " + intersectionAnnotations.size());
	        
			
		}

		return annotationsByAnatomicalSystemMap;
	}

	private void fillClassesIRIsMap() throws OWLOntologyCreationException {
		for (AnatomicalSystem anatomicalSystem : AnatomicalSystem.values()) {

			//System.out.println(anatomicalSystem.name() + " "
			//		+ anatomicalSystem.iri());

			Set<String> subClassesIRIs = ontologyProcessor
					.findComponentClasses(anatomicalSystem.iri());

			Set<String> subClassesTAOIds = convertToTAOId(subClassesIRIs);

			classesIRIsByAnatomicalSystem.put(anatomicalSystem,
					subClassesTAOIds);
			
			OntophenotypeLogger.info("Retrieving components of the " + anatomicalSystem.name() + " ... \n");
		}

	}

	private Set<String> convertToTAOId(Set<String> subClassesIRIs) {

		Set<String> subClassesTAOIds = new HashSet<String>();

		Iterator<String> iIRI = subClassesIRIs.iterator();
		while (iIRI.hasNext()) {
			String iri = iIRI.next();

			String[] iriComponents = iri.split("/");
			String taoID = iriComponents[iriComponents.length - 1].replace("_",
					":");

			//System.out.println("TAO " + taoID);

			subClassesTAOIds.add(taoID);
		}

		return subClassesTAOIds;

	}

}
