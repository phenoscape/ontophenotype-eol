package org.phenoscape.ontophenotype.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.phenoscape.ontophenotype.phenotype.AnnotationCollection;
import com.google.gson.Gson;


public class JSONReader implements IPhenotypeDataReader {
	
	protected Gson gson;

	/**
	 * Reads the phenotype data given in the JSON format
	 */
	@Override
	public AnnotationCollection readFile(String filePath) {
		
		String jsonText = "";
		try {
		    BufferedReader in = new BufferedReader(new FileReader(filePath));		    
		    String line;
		    		    
		    while ((line = in.readLine()) != null) {
		    	jsonText += line;
		    }
		    in.close();
		} catch (IOException e) {
			System.out.println(e);
		}		
		
		gson = new Gson();
		AnnotationCollection annotationCollection = gson.fromJson(jsonText, 
				AnnotationCollection.class);
		
		return annotationCollection;
	}

}
