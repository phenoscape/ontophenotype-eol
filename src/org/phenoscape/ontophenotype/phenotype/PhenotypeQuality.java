package org.phenoscape.ontophenotype.phenotype;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class PhenotypeQuality {
	
	private String id;
	private String name;	
	
	
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
	
	@Override
	public String toString() {
		return id + " " + name ;		
	}
	

}
