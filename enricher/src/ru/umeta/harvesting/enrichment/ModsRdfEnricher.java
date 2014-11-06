package ru.umeta.harvesting.enrichment;

/**
 * Created by k.kosolapov on 16.09.2014.
 */

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class ModsRdfEnricher implements IEnricher<String>{

    public static String RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";


    @Override
    public String enrich(String source, String enriched) {
            return source;
    }

    public String printPropertiesForOWLClass() {
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        FileManager.get().readModel( m, "C:\\import\\modsrdf.owl.txt" );

        OntClass modsResource = m.getOntClass( RDF + "ModsResource" );
        return modsResource.listSubClasses().toString();
    }

    public static void main(String[] args) {
        
    }
}
