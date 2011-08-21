package org.phenoscape.ontophenotype.text;

import org.phenoscape.ontophenotype.util.TextConstants;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class TextAugmentedTaxon {

	private String name;
	private String dcIdentifier;
	private String ttoID;
	private String phenotypeDescription;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDcIdentifier() {
		return dcIdentifier;
	}

	public void setDcIdentifier(String id) {
		this.dcIdentifier = id;
	}

	public String getPhenotypeDescription() {
		return phenotypeDescription;
	}

	public void setPhenotypeDescription(String phenotypeDescription) {
		this.phenotypeDescription = phenotypeDescription;
	}

	public String getTtoID() {
		return ttoID;
	}

	public void setTtoID(String ttoID) {
		this.ttoID = ttoID;
	}

	public void buildIdFromName() {
		dcIdentifier = name.toLowerCase().replaceAll(" ", "_");
	}

	/**
	 * Builds the link structure for this taxon's page at
	 * http://kb.phenoscape.org/ .
	 * 
	 * @return the link to this taxon's page at http://kb.phenoscape.org/
	 */
	public String getSourceURL() {
		if (this.ttoID != null && !this.ttoID.equals("")) {
			String sourceURL = TextConstants.PHENOSCAPE_TAXON_BASE_URL
					+ this.ttoID;
			return sourceURL;
		} else {
			return "";
		}
	}

}
