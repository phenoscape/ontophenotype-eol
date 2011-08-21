package org.phenoscape.ontophenotype.main;

import org.phenoscape.ontophenotype.util.OntophenotypeLogger;

/**
 * 
 * @author Alex Ginsca
 * @version 1.0
 * @since 2011
 */
public class Main {

	public static void main(String[] args) {
		/*
		 * To run this method in an IDE, provide the corresponding args[] parameters.
		 * An example is presented below.
		 * 
		 * args = new String[4]; args[0] = "-i"; args[1] =
		 * "E:\\GSoC\\resources\\Ictalurus.json"; args[2] = "-o"; args[3] =
		 * "test_main_output.xml";
		 */

		if (args.length < 4 || args.length > 8 || args.length % 2 == 1) {
			OntophenotypeLogger.warning("Incorrect number of parameters !");
		} else if (!args[0].equals("-i") || !args[2].equals("-o")) {
			OntophenotypeLogger.warning("Incorrect parameters !");
		} else if (args.length == 4) {

			ExecutionController.run(args[1], args[3], null, true, true);

		} else if (args.length == 6) {
			resolveSixArguments(args);
		} else {
			resolveEightArguments(args);
		}

	}

	private static void resolveSixArguments(String[] args) {
		if (!args[4].equals("links") || !args[4].equals("group")) {
			OntophenotypeLogger.warning("Incorrect third parameter !");
		} else if (args[4].equals("links")) {
			if (!args[5].equals("true") || !args[5].equals("false")) {
				OntophenotypeLogger
						.warning("Incorrect third parameter value !");
			} else if (args[5].equals("true")) {

				ExecutionController.run(args[1], args[3], null, true, true);

			} else {

				ExecutionController.run(args[1], args[3], null, false, true);

			}
		} else {

			if (!args[5].equals("true") || !args[5].equals("false")) {
				OntophenotypeLogger
						.warning("Incorrect third parameter value !");
			} else if (args[5].equals("true")) {

				ExecutionController.run(args[1], args[3], null, true, true);

			} else {

				ExecutionController.run(args[1], args[3], null, true, false);

			}
		}
	}

	private static void resolveEightArguments(String[] args) {
		if (!args[4].equals("links") || !args[4].equals("group")) {
			OntophenotypeLogger.warning("Incorrect third parameter !");
		} else if (args[4].equals("links")) {
			if (!args[6].equals("group")) {
				OntophenotypeLogger.warning("Incorrect fourth parameter !");
			} else if (!args[5].equals("true") || !args[5].equals("false")) {
				OntophenotypeLogger
						.warning("Incorrect third parameter value !");
			} else if (!args[7].equals("true") || !args[7].equals("false")) {
				OntophenotypeLogger
						.warning("Incorrect fourth parameter value !");
			} else if (args[5].equals("false")) {
				if (args[7].equals("false")) {
					ExecutionController.run(args[1], args[3], null, false,
							false);
				} else {
					ExecutionController
							.run(args[1], args[3], null, false, true);
				}
			} else {
				if (args[7].equals("false")) {
					if (args[7].equals("false")) {
						ExecutionController.run(args[1], args[3], null, true,
								false);
					}
				} else {
					ExecutionController.run(args[1], args[3], null, true, true);
				}

			}
		} else {
			if (!args[6].equals("links")) {
				OntophenotypeLogger.warning("Incorrect fourth parameter !");
			} else if (!args[5].equals("true") || !args[5].equals("false")) {
				OntophenotypeLogger
						.warning("Incorrect third parameter value !");
			} else if (!args[7].equals("true") || !args[7].equals("false")) {
				OntophenotypeLogger
						.warning("Incorrect fourth parameter value !");
			} else if (args[5].equals("false")) {
				if (args[7].equals("false")) {
					ExecutionController.run(args[1], args[3], null, false,
							false);
				} else {
					ExecutionController
							.run(args[1], args[3], null, true, false);
				}
			} else {
				if (args[7].equals("false")) {
					if (args[7].equals("false")) {
						ExecutionController.run(args[1], args[3], null, false,
								true);
					}
				} else {
					ExecutionController.run(args[1], args[3], null, true, true);
				}

			}
		}
	}
}
