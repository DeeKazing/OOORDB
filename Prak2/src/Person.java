import java.util.ArrayList;
import java.util.List;

public abstract class Person {
    long Id;
    String Name;
    String PhoneNumber;
    List<Call> allCalls;

    public Person(long id, String name, String phone) {
        Id = id;
        Name = name;
        PhoneNumber = phone;
        allCalls = new ArrayList<>();
    }

    public void display() {
        System.out.printf("Name: %s, ID: %x, Phone Number: %s",
                Name, Id, PhoneNumber);
    }

    protected void displayCalls() {
        allCalls.forEach(call -> {
            System.out.print(" └──── ");
            call.display();
            System.out.print("\n");
        });
        System.out.print("\n");
    }
}
