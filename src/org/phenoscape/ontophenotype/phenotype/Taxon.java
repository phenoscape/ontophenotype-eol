package org.phenoscape.ontophenotype.phenotype;

import java.util.List;

public class Taxon {
	
	private String id;
	private String name;
	private boolean extinct;
	private TaxonRank rank;
	private List<Annotation> phenotypeAnnotations;
	
	
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
	
	public List<Annotation> getPhenotypeAnnotations() {
		return phenotypeAnnotations;
	}
	public void setPhenotypeAnnotations(List<Annotation> phenotypeAnnotations) {
		this.phenotypeAnnotations = phenotypeAnnotations;
	}
	
	public void addAnnotaion(Annotation annotation) {
		phenotypeAnnotations.add(annotation);
	}	
	
	@Override
	public String toString() {
		return id + " " + name ;		
	}
	
	@Override
	public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (!(obj instanceof Taxon)) {
            return false; 
        }
        Taxon taxon = (Taxon)obj;
        return this.toString().equals(taxon.toString());
 
    }
 
	@Override
    public int hashCode () {
        final int multiplier = 23;        
            int code = 133;
            code = multiplier * code + id.hashCode();
            code = multiplier * code + name.hashCode();        
       
        return code;
    }
	
	

}
