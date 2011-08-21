package org.phenoscape.ontophenotype.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class OntophenotypeLogger {
	
	  /**
     * Logs information.
     *    
     * @param message
     */
    public static void info(String message) {        
        Logger.getAnonymousLogger().log(Level.INFO, message);
    }
    
    public static void warning(String message) {        
        Logger.getAnonymousLogger().log(Level.WARNING, message);
    }

}
