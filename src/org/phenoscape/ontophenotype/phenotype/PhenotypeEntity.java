package org.phenoscape.ontophenotype.phenotype;

import org.phenoscape.ontophenotype.util.TextConstants;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class PhenotypeEntity {

	private String id;
	private String name;

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	/**
	 * Builds the link structure for this entity's page at
	 * http://kb.phenoscape.org/ .
	 * 
	 * @return the link to this entity's page at http://kb.phenoscape.org/
	 */
	public String getLinkedName() {
		if (name != null && !name.equals("")) {
			String linkedName = "<a href=\"";
			linkedName += TextConstants.PHENOSCAPE_ENTITY_BASE_URL + this.id
					+ "\">";
			linkedName += this.name;
			linkedName += "</a>";
			return linkedName;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return id + " " + name;
	}

}
