import com.mongodb.WriteConcern;
import com.mongodb.client.*;
import com.mongodb.client.model.InsertOneOptions;
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
        //db.withWriteConcern(WriteConcern.ACKNOWLEDGED);
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
