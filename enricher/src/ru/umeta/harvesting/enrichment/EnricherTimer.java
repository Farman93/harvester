package ru.umeta.harvesting.enrichment;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;



public class EnricherTimer {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final Database database = new Database();
                try {
                    List<Document> documentList = database.checkNew();
                    if (documentList != null) {
                        for (Document source : documentList) {
                            final Document enriched = database.getEnrichedDocument(source);
                            IEnricher<String> enricher = new ModsRdfEnricher();
                            enricher.enrich(source.xml, enriched.xml);
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
            }, 30*1000, 30*1000);

    }
}