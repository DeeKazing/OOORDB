package FamilyTree;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static final String path = "familytree.db";

    public static void main(String[] args) {
        try {
            Files.delete(Path.of(path));
        } catch (IOException e) {
            //ignored
        }
        ObjectContainer db = Db4oEmbedded.openFile(path);

        Person abe = new Person("Simpson", "Abraham", 80, null, null);
        Person jac = new Person("Bouvier", "Jacqueline", 80, null, null);
        Person clancy = new Person("Bouvier", "Clancy", 80, null, null);
        Person edwina = new Person("?", "Edwina", 80, null, null);
        Person mona = new Person("Simpson", "Mona", 80, null, null);
        Person questionMark = new Person("?", "?", 40, null, null);
        Person patty = new Person("Bouvier", "Patty", 40, clancy, jac);
        Person selma = new Person("Bouvier", "Selma", 40, clancy, jac);
        Person herbert = new Person("Powel", "Herbert", 40, abe, questionMark);
        Person abbie = new Person("?", "Abbie", 40, abe, edwina);
        Person homer = new Person("Simpson", "Homer", 40, abe, mona);
        Person marge = new Person("Simpson", "Marge", 40, clancy, jac);
        Person ling = new Person("?", "Ling", 3, null, selma);
        Person maggie = new Person("Simpson", "Maggie", 5, homer, marge);
        Person lisa = new Person("Simpson", "Lisa", 12, homer, marge);
        Person bart = new Person("Simpson", "Bart", 14, homer, marge);

        db.store(maggie);
        db.store(herbert);
        db.store(abbie);
        db.store(lisa);
        db.store(bart);
        db.store(patty);
        db.store(ling);
        db.commit();

        ObjectSet<Person> people = db.query(Person.class);

        people.forEach(p -> {
            p.print(0);
            System.out.println("ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー");
        });

        db.close();
    }
}
