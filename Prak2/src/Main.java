import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    static final String path = "helpdesk.db";

    public static void main(String[] args) {

        Client cl1 = new Client(123, "Martin", "897454", "hjhje");
        Client cl2 = new Client(524, "Thomas", "845599", "jkkle");
        Client cl3 = new Client(565, "Johann", "897899", "polje");

        Consultant co1 = new Consultant(999, "David", "444444", "44");
        Consultant co2 = new Consultant(888, "Kevin", "333333", "33");
        Consultant co3 = new Consultant(777, "Stefan", "555555", "55");

        Call ca1 = new Call(1, 100, 20, cl1, co1);
        Call ca2 = new Call(2, 210, 30, cl1, co2);
        Call ca3 = new Call(3, 20, 40, cl2, co2);
        Call ca4 = new Call(4, 400, 50, cl2, co1);

        try {
            Files.delete(Path.of(path));
        } catch (IOException e) {
            //ignored
        }
        ObjectContainer db = Db4oEmbedded.openFile(path);

        db.store(ca1);
        db.store(ca2);
        db.store(ca3);
        db.store(ca4);
        db.store(cl3);
        db.store(co3);

        ObjectSet<Person> people = db.queryByExample(Person.class);
        people.forEach(Person::display);

        db.close();
    }
}
