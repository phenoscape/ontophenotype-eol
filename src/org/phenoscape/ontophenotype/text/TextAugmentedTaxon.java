package org.phenoscape.ontophenotype.text;

public class TextAugmentedTaxon {
	
	private String name;
	private String id;
	private String phenotypeDescription;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhenotypeDescription() {
		return phenotypeDescription;
	}
	public void setPhenotypeDescription(String phenotypeDescription) {
		this.phenotypeDescription = phenotypeDescription;
	}
	
	public void buildIdFromName() {
		id = name.toLowerCase().replaceAll(" ", "_");
	}

}
