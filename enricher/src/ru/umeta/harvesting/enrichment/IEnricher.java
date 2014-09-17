package ru.umeta.harvesting.enrichment;

/**
 * Created by k.kosolapov on 16.09.2014.
 */
public interface IEnricher<T> {
    /**
     * Makes an enrichment of result with source material
     * @param source
     * @param enriched
     * @return
     */
    public T enrich( T source, T enriched);
}
