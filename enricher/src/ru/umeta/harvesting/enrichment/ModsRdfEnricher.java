package ru.umeta.harvesting.enrichment;

/**
 * Created by k.kosolapov on 16.09.2014.
 */
public class ModsRdfEnricher implements IEnricher<String>{
    @Override
    public String enrich(String source, String enriched) {
            return source;
    }
}
