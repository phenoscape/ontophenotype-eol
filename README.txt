===============================================================================
This is the repository for the "Export ontology-based phenotype descriptions to the Encyclopedia of Life" GSOC project

For more info:
http://informatics.nescent.org/wiki/PhyloSoC:Export_ontology-based_phenotype_descriptions_to_EOL

For project blog:
http://alexgansca.com/ontophenotype/
===============================================================================

= Using the executable jar =

The jar can be obtained by pulling and building the project from github.

The jar can be run from the command line with the following set of parameters:

java -jar ontophenotype-eol.jar -i input_file_path -o export_file_path [-links] [true|false] [-group] [true|false]

    -i      - Mandatory parameter. The full path of the JSON file with the phenotype annotations for taxa. 
              Such files can contain any number of taxa and can be obtained by requesting an export
              in JSON format from the Phenoscape Knowledgebase phenotype annotations to taxa section
              found at http://kb.phenoscape.org/taxon_annotations
    -o      - Mandatory parameter. The full path of the file where the export file will be written. 
              The file should have the .xml extension.
    -links  - Optional parameter. Boolean value indicating whether links to anatomy terms definitions should be used in the export file.
    -group  - Optional parameter. Boolean value indicating whether anatomy terms should be grouped in anatomical systems in the export file. 

The implicit value of the two optional parameters is set to true . Running the jar with 
only the first two parameters will produce an export file that contains links 
to anatomy terms definitions and groups of anatomical systems. 