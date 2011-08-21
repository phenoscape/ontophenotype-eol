package org.phenoscape.ontophenotype.util;

public enum AnatomicalSystem {

	Nervous_System("http://purl.obolibrary.org/obo/TAO_0000396"), 
	Skeletal_System("http://purl.obolibrary.org/obo/TAO_0000434"),
	Cardiovascular_System("http://purl.obolibrary.org/obo/TAO_0000010"),
	Digestive_System("http://purl.obolibrary.org/obo/TAO_0000339"),
	Endocrine_System("http://purl.obolibrary.org/obo/TAO_0001158"),
	Hematopoietic_System("http://purl.obolibrary.org/obo/TAO_0005023"),
	Immune_System("http://purl.obolibrary.org/obo/TAO_0001159"),
	Liver_And_Biliary_System("http://purl.obolibrary.org/obo/TAO_0000036"),
	Musculature_System("http://purl.obolibrary.org/obo/TAO_0000548"),
	Renal_System("http://purl.obolibrary.org/obo/TAO_0000163"),
	Respiratory_System("http://purl.obolibrary.org/obo/TAO_0000272"),
	Sensory_System("http://purl.obolibrary.org/obo/TAO_0000282"),
	Reproductive_System("http://purl.obolibrary.org/obo/TAO_0000632");

	private final String iri;

	AnatomicalSystem(String iri) {
		this.iri = iri;
	}
	
	public String iri() {
		return iri;
	}

}
