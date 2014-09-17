package ru.umeta.harvesting.enrichment;

/**
 * Created by k.kosolapov on 16.09.2014.
 */
public class Document {
    public final int id;
    public final String title;
    public final String isbn;
    public final String xml;
    public Document(int id, String title, String isbn, String xml) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.xml = xml;
    }
}
