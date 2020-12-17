import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    static String dir = "E:\\Programming\\Git\\OOORDB\\libs\\neo4j-community-3.5.25\\data\\databases\\graph.db";

    private enum RelTypes implements RelationshipType {
        FATHER_OF, MOTHER_OF
    }

    public enum MyLabels implements Label {
        Person
    }

    public static void main(String[] args) {
        GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(new File(dir.replaceAll("\\\\", "/")));
        Runtime.getRuntime().addShutdownHook(new Thread(graphDB::shutdown));

        /*try (Transaction tx = graphDB.beginTx()) {
            graphDB.execute("CREATE (absi:Person {name:'Abraham Simpson'})" +
                    "CREATE (mosi:Person {name:'Mona Simpson'})" +
                    "CREATE (jabo:Person {name:'Jacqueline Bouvier'})" +
                    "CREATE (clbo:Person {name:'Clancy Bouvier'})" +
                    "CREATE (hosi:Person {name:'Homer Simpson'})" +
                    "CREATE (masi:Person {name:'Marge Simpson'})" +
                    "CREATE (pabo:Person {name:'Patty Bouvier'})" +
                    "CREATE (sebo:Person {name:'Selma Bouvier'})" +
                    "CREATE (mesi:Person {name:'Meggie Simpson'})" +
                    "CREATE (lisi:Person {name:'Lisa Simpson'})" +
                    "CREATE (basi:Person {name:'Bart Simpson'})" +
                    "CREATE (absi)-[:FATHER_OF]->(hosi)" +
                    "CREATE (mosi)-[:MOTHER_OF]->(hosi)" +
                    "CREATE (clbo)-[:FATHER_OF]->(masi)" +
                    "CREATE (jabo)-[:MOTHER_OF]->(masi)" +
                    "CREATE (clbo)-[:FATHER_OF]->(pabo)" +
                    "CREATE (jabo)-[:MOTHER_OF]->(pabo)" +
                    "CREATE (clbo)-[:FATHER_OF]->(sebo)" +
                    "CREATE (jabo)-[:MOTHER_OF]->(sebo)" +
                    "CREATE (hosi)-[:FATHER_OF]->(lisi)" +
                    "CREATE (masi)-[:MOTHER_OF]->(lisi)" +
                    "CREATE (hosi)-[:FATHER_OF]->(basi)" +
                    "CREATE (masi)-[:MOTHER_OF]->(basi)" +
                    "CREATE (hosi)-[:FATHER_OF]->(mesi)" +
                    "CREATE (masi)-[:MOTHER_OF]->(mesi)");

            tx.success();
        }*/

        // LEAF
        try (Transaction tx = graphDB.beginTx()) {
            System.out.println("Leaf First:");
            try (ResourceIterator<Node> people = graphDB.findNodes(MyLabels.Person)) {
                var sortedPeople = new ArrayList<Node>();
                while (people.hasNext()) {
                    Node person = people.next();
                    sortedPeople.add(person);
                }
                Collections.reverse(sortedPeople);
                sortedPeople.forEach(x -> {
                            printSubtree(x, 0);
                            System.out.println("-----------------------------------------------");
                        }
                );
            }
            tx.success();
        }

        System.out.println("\n\n\n");

        // ROOT
        try (Transaction tx = graphDB.beginTx()) {
            System.out.println("Root First:");
            try (ResourceIterator<Node> people = graphDB.findNodes(MyLabels.Person)) {
                while (people.hasNext()) {
                    Node person = people.next();
                    for (Path position : graphDB.traversalDescription()
                            .depthFirst()
                            .relationships(RelTypes.FATHER_OF, Direction.OUTGOING)
                            .relationships(RelTypes.MOTHER_OF, Direction.OUTGOING)
                            .evaluator(Evaluators.toDepth(6))
                            .traverse(person)) {
                        for (int i = 0; i < position.length(); ++i) {
                            System.out.print("\t\t\t");
                        }
                        System.out.println(position.endNode().getProperty("name"));
                    }
                    System.out.println("----------------------------------------------------------");
                }
            }
            tx.success();
        }

    }

    private static void printSubtree(Node person, int indentation) {
        for (int i = 0; i < indentation; ++i) {
            System.out.print("\t\t\t");
        }
        System.out.println(person.getProperty("name"));

        if (person.hasRelationship(Direction.INCOMING, RelTypes.FATHER_OF)) {
            Node father = person.getRelationships(Direction.INCOMING, RelTypes.FATHER_OF).iterator().next().getStartNode();
            printSubtree(father, indentation + 1);
        }

        if (person.hasRelationship(Direction.INCOMING, RelTypes.MOTHER_OF)) {
            Node mother = person.getRelationships(Direction.INCOMING, RelTypes.MOTHER_OF).iterator().next().getStartNode();
            printSubtree(mother, indentation + 1);
        }
    }
}
