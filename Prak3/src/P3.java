import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;


public class P3 {
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collReparatur;
    MongoCollection<Document> collAuto;
    MongoCollection<Document> collKunde;

    public void initialize(){
        client = MongoClients.create();
        db = client.getDatabase("P3");
        db.drop();
        collReparatur = db.getCollection("Reparatur");
        collAuto = db.getCollection("Auto");
        collKunde = db.getCollection("Kunde");
    }

    public void exerciseA(){
        System.out.println("-- ALLE KUNDEN MIT IHREN AUTOS + REPARATUREN --");
        try (MongoCursor<Document> kundeCursor = collKunde.find().iterator()) {
            while (kundeCursor.hasNext()) {
                Document currentKunde = kundeCursor.next();
                System.out.print("Kunde: ");
                System.out.println(currentKunde.toJson());

                //Auto
                for (Document currentAuto : collAuto.find(Filters.eq("Kunde",
                        currentKunde.get("_id")))) {
                    System.out.print("\tAuto: ");
                    System.out.println(currentAuto.toJson());

                    //Reparatur
                    for (Document currentReparatur : collReparatur.find(Filters.eq("Auto",
                            currentAuto.get("_id")))) {
                        System.out.print("\t\tReparatur: ");
                        System.out.println(currentReparatur.toJson());
                    }
                }
            }
        }
    }

    public void exerciseB(){
        System.out.println("-- AUSGABE VON KUNDE MUELLER --");
        var mueller = collKunde.find(Filters.eq("Name", "Mueller")).first();
        System.out.println(mueller != null ? mueller.toJson() : "Mueller not found");
    }

    public void exerciseC(){
        System.out.println("-- ALLE KUNDEN MIT LACK REPARATUR --");
        try (MongoCursor<Document> kundeCursor = collKunde.find().iterator()) {
            while (kundeCursor.hasNext()) {
                boolean lack = false;
                Document currentKunde = kundeCursor.next();

                //Auto
                for (Document currentAuto : collAuto.find(Filters.eq("Kunde",
                        currentKunde.get("_id")))) {
                    //Reparatur
                    for (Document currentReparatur : collReparatur.find(Filters.eq("Auto",
                            currentAuto.get("_id")))) {
                        if (currentReparatur.get("Bezeichnung").equals("Lack")) {
                            lack = true;
                        }
                    }
                }
                if (lack) {
                    System.out.println(currentKunde.toJson());
                }
            }
        }
    }

    public void exerciseD() {
        System.out.println("-- KUNDE MUELLER HAT SICH EIN AUTO GEKAUFT: SEINE AUTOS --");
        Document autoNeu = new Document("_id", "auto4")
                .append("Name", "Audi A1")
                .append("Kennzeichen", "M-XX 12")
                .append("Baujahr", "2019")
                .append("Kunde", "kunde2");

        collAuto.insertOne(autoNeu);

        for (Document document : collAuto.find(Filters.eq("Kunde","kunde2")))
            System.out.println(document.toJson());
    }

    public void exerciseE() {
        System.out.println("-- KUNDE RICHTER ENTFERNT: GEBE ALLE RESTLICHEN KUNDEN AUS --");
        collKunde.deleteOne(Filters.eq("Name", "Richter"));
        try (MongoCursor<Document> kundeCursor = collKunde.find().iterator()) {
            while (kundeCursor.hasNext()) {
                System.out.println(kundeCursor.next().toJson());
            }
        }
    }

    public void insertTemplate() {
        Document rep1 = new Document("_id", "rep1")
                .append("Bezeichnung", "Bremse")
                .append("Datum", "22.01.2001")
                .append("Auto", "auto1");
        Document rep2 = new Document("_id", "rep2")
                .append("Bezeichnung", "Lack")
                .append("Datum", "17.06.2005")
                .append("Auto", "auto2");
        Document rep3 = new Document("_id", "rep3")
                .append("Bezeichnung", "Scheibe")
                .append("Datum", "10.01.1995")
                .append("Auto", "auto2");

        Document auto1 = new Document("_id", "auto1")
                .append("Name", "Opel Admiral")
                .append("Kennzeichen", "DA-OA 338")
                .append("Baujahr", "1938")
                .append("Kunde", "kunde1");
        Document auto2 = new Document("_id", "auto2")
                .append("Name", "Opel Manta")
                .append("Kennzeichen", "DA-OM 447")
                .append("Baujahr", "1972")
                .append("Kunde", "kunde1");
        Document auto3 = new Document("_id", "auto3")
                .append("Name", "Mercedes SLK")
                .append("Kennzeichen", "S-PG 12")
                .append("Baujahr", "2005")
                .append("Kunde", "kunde2");

        Document kunde1 = new Document("_id", "kunde1")
                .append("Name", "Schmidt")
                .append("TelNr", "061421141")
                .append("Strasse", "Bahnhofstr. 1")
                .append("PLZ", "65428")
                .append("Ort", "Ruesselsheim");
        Document kunde2 = new Document("_id", "kunde2")
                .append("Name", "Mueller")
                .append("TelNr", "0711566")
                .append("Strasse", "Alleeweg 7")
                .append("PLZ", "70173")
                .append("Ort", "Stuttgart");
        Document kunde3 = new Document("_id", "kunde3")
                .append("Name", "Richter")
                .append("Strasse", "Hauptstr 8")
                .append("PLZ", "70173")
                .append("Ort", "Stuttgart");

        collReparatur.insertOne(rep1);
        collReparatur.insertOne(rep2);
        collReparatur.insertOne(rep3);

        collAuto.insertOne(auto1);
        collAuto.insertOne(auto2);
        collAuto.insertOne(auto3);

        collKunde.insertOne(kunde1);
        collKunde.insertOne(kunde2);
        collKunde.insertOne(kunde3);
    }

    public void close(){
        client.close();
    }
}
