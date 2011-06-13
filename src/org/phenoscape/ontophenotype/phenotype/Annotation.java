package org.phenoscape.ontophenotype.phenotype;

public class Annotation {

	private PhenotypeEntity entity;
	private PhenotypeEntity related_entity;
	private PhenotypeQuality quality;
	private Taxon taxon;

	public PhenotypeEntity getEntity() {
		return entity;
	}

	public void setEntity(PhenotypeEntity entity) {
		this.entity = entity;
	}

	public PhenotypeEntity getRelated_entity() {
		return related_entity;
	}

	public void setRelated_entity(PhenotypeEntity related_entity) {
		this.related_entity = related_entity;
	}

	public PhenotypeQuality getQuality() {
		return quality;
	}

	public void setQuality(PhenotypeQuality quality) {
		this.quality = quality;
	}

	public Taxon getTaxon() {
		return taxon;
	}

	public void setTaxon(Taxon taxon) {
		this.taxon = taxon;
	}

	@Override
	public String toString() {
		return entity.toString() + " " + quality.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Annotation)) {
			return false;
		}
		Annotation annotation = (Annotation) obj;
		return this.toString().equals(annotation.toString());

	}

	@Override
	public int hashCode() {
		final int multiplier = 23;
		int code = 133;
		code = multiplier * code + entity.getId().hashCode();
		code = multiplier * code + quality.getId().hashCode();		

		return code;
	}

}
