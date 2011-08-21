package org.phenoscape.ontophenotype.phenotype;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class Taxon {

	private String id;
	private String name;
	private boolean extinct;
	private TaxonRank rank;
	private List<Annotation> phenotypeAnnotations;
	private Map<String, Annotation> entityIdAnnotationMap;
	private Set<String> entitiesIDsInAnnotations;

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

	public Map<String, Annotation> getEntityIdAnnotationMap() {
		return entityIdAnnotationMap;
	}

	public void setEntityIdAnnotationMap(
			Map<String, Annotation> entityIdAnnotationMap) {
		this.entityIdAnnotationMap = entityIdAnnotationMap;
	}

	public Set<String> getEntitiesIDsInAnnotations() {
		return entitiesIDsInAnnotations;
	}

	public void setEntitiesIDsInAnnotations(Set<String> entitiesIDsInAnnotations) {
		this.entitiesIDsInAnnotations = entitiesIDsInAnnotations;
	}

	public void fillAnnotationCollections(Annotation annotation) {
		
		phenotypeAnnotations.add(annotation);
		entitiesIDsInAnnotations.add(annotation.getEntity().getId());
		entityIdAnnotationMap.put(annotation.getEntity().getId(), annotation);
	}

	@Override
	public String toString() {
		return id + " " + name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Taxon)) {
			return false;
		}
		Taxon taxon = (Taxon) obj;
		return this.toString().equals(taxon.toString());

	}

	@Override
	public int hashCode() {
		final int multiplier = 23;
		int code = 133;
		code = multiplier * code + id.hashCode();
		code = multiplier * code + name.hashCode();

		return code;
	}

}
