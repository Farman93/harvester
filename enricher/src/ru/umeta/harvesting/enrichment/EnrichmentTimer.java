package ru.umeta.harvesting.enrichment;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;



public class EnrichmentTimer {
    public static void main(String[] args) {





        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final Database database = new Database();
                try {
                    List<Document> documentList = database.checkNew();
                    if (documentList != null && documentList.size() > 0) {
                        for (Document source : documentList) {
                            Document enriched = database.getEnrichedDocument(source);
                            if (enriched == null) {
                                database.createEnrichedDocument(source);
                                enriched = database.getEnrichedDocument(source);
                            }
                            IEnricher<String> enricher = new ModsRdfEnricher();
                            enricher.enrich(source.xml, enriched.xml);
                            database.updateEnrichedDocument(enriched);
                            database.setEnrichedForDocument(source.id,enriched.id);
                            System.out.println("Successfully enriched " + enriched.id + " with source " + source.id);
                        }
                    }
                    else {
                        System.out.println("Nothing new");
                    }

                } catch (ClassNotFoundException | FileNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 30*60*1000);

    }
}