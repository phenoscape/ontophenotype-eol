package org.phenoscape.ontophenotype.ontology;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.phenoscape.ontophenotype.util.OntophenotypeLogger;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class AnatomyOntologyProcessor {

	private OWLOntology anatomyOntology;
	private OWLDataFactory factory;

	public AnatomyOntologyProcessor(File ontologyFile) {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		try {
			anatomyOntology = manager
					.loadOntologyFromOntologyDocument(ontologyFile);
			factory = manager.getOWLDataFactory();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}

	public AnatomyOntologyProcessor(String ontologyURL) {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		try {

			OntophenotypeLogger
					.info("Remotly connecting to http://purl.obolibrary.org/obo/tao.owl ... \n");

			anatomyOntology = manager.loadOntologyFromOntologyDocument(IRI
					.create(ontologyURL));
			factory = manager.getOWLDataFactory();

			OntophenotypeLogger
					.info("Successfully connected to http://purl.obolibrary.org/obo/tao.owl. \n");

		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}

	public OWLOntology getAnatomyOntology() {
		return anatomyOntology;
	}

	public void setAnatomyOntology(OWLOntology anatomyOntology) {
		this.anatomyOntology = anatomyOntology;
	}

	public OWLDataFactory getFactory() {
		return factory;
	}

	public void setFactory(OWLDataFactory factory) {
		this.factory = factory;
	}

	/**
	 * Finds all the component owl classes of a class identified by its IRI.
	 * 
	 * @param classIRI
	 *            the IRI of the class for which the component classes should be
	 *            found
	 * @return a set of component owl classes IRIs
	 * @throws OWLOntologyCreationException
	 */
	public Set<String> findComponentClasses(String classIRI)
			throws OWLOntologyCreationException {

		OWLObjectProperty part_of = factory.getOWLObjectProperty(IRI
				.create("http://purl.obolibrary.org/obo/OBO_REL_part_of"));
		OWLClass skeletalSystem = factory.getOWLClass(IRI.create(classIRI));
		OWLObjectSomeValuesFrom restriction = factory
				.getOWLObjectSomeValuesFrom(part_of, skeletalSystem);
		OWLObjectUnionOf queryClass = factory.getOWLObjectUnionOf(
				skeletalSystem, restriction);

		OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createReasoner(anatomyOntology);

		Set<OWLClass> subClasses = reasoner.getSubClasses(queryClass, false)
				.getFlattened();

		Set<String> subClassesIRIs = new HashSet<String>();
		Iterator<OWLClass> iOWLClass = subClasses.iterator();
		while (iOWLClass.hasNext()) {
			String subClassIRI = iOWLClass.next().getIRI().toString();
			subClassesIRIs.add(subClassIRI);
		}

		return subClassesIRIs;
	}

	@Deprecated
	public List<String> findComponentClassesWalk(String classIRI)
			throws OWLOntologyCreationException {

		IRI iri = IRI.create(classIRI);
		OWLClass clsHead = factory.getOWLClass(iri);

		List<String> componentClassesIRIs = new ArrayList<String>();

		recursiveSubClassesWalker(componentClassesIRIs, clsHead,
				anatomyOntology, factory);

		return componentClassesIRIs;

	}

	@Deprecated
	private void recursiveSubClassesWalker(List<String> componentClassesIRIs,
			OWLClass superClass, OWLOntology onto, OWLDataFactory factory) {

		Iterator<OWLAxiom> iAxiom = superClass.getReferencingAxioms(onto)
				.iterator();
		while (iAxiom.hasNext()) {
			OWLAxiom axiom = iAxiom.next();
			String subClassIRIText = getSubClassIRIFromAxiom(axiom);

			/*
			 * Only Axioms of the Subclass_Of type are selected.
			 * 
			 * Subclass_Of axiom also represent superclass statements. The
			 * OBO_REL_part_of and the TODO_develops_from relations are
			 * described within the ObjectSomeValuesFrom object property.
			 * 
			 * It is easy to identify the subclass and the superclass referenced
			 * in such an axiom using its signature. The subclass's IRI will
			 * always be on the first position after the "SubClassOf" statement.
			 */
			if (axiom.isOfType(AxiomType.SUBCLASS_OF)
					&& axiom.toString().contains("ObjectSomeValuesFrom")
					&& !superClass.getIRI().toString().equals(subClassIRIText)) {

				System.out.println("axiom " + axiom.getAxiomType() + " "
						+ axiom.toString());

				componentClassesIRIs.add(subClassIRIText);
				IRI subClassIRI = IRI.create(subClassIRIText);
				OWLClass subClass = factory.getOWLClass(subClassIRI);

				recursiveSubClassesWalker(componentClassesIRIs, subClass, onto,
						factory);
			}
		}

		/*
		 * Subclasses identified in the owl taxonomy are also considered
		 */
		Iterator<OWLClassExpression> iClass = superClass.getSubClasses(onto)
				.iterator();
		while (iClass.hasNext()) {
			OWLClass subClass = (OWLClass) iClass.next();
			if (!componentClassesIRIs.contains(subClass.getIRI().toString())) {
				componentClassesIRIs.add(subClass.getIRI().toString());
			}
			recursiveSubClassesWalker(componentClassesIRIs, subClass, onto,
					factory);
		}

	}

	@Deprecated
	private String getSubClassIRIFromAxiom(OWLAxiom axiom) {
		String axiomSignature = axiom.toString();

		String subClassIRI = axiomSignature.split(" ")[0];
		String subClassIRIClean = subClassIRI.replaceAll(
				"\\(|\\)|<|>|SubClassOf", "").trim();

		return subClassIRIClean;

	}

}
