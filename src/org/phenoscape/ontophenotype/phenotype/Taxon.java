package org.phenoscape.ontophenotype.phenotype;

public class Taxon {
	
	private String id;
	private String name;
	private boolean extinct;
	private TaxonRank rank;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isExtinct() {
		return extinct;
	}
	public void setExtinct(boolean extinct) {
		this.extinct = extinct;
	}
	public TaxonRank getRank() {
		return rank;
	}
	public void setRank(TaxonRank rank) {
		this.rank = rank;
	}
	
	

}
