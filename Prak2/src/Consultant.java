public class Consultant extends Person {
    String MobilePhone;

    public Consultant(long id, String name, String phone, String mobile) {
        super(id, name, phone);
        this.MobilePhone = mobile;
    }

    @Override
    public void display() {
        System.out.print("Consultant: ");
        super.display();
        System.out.printf(", Mobile Phone: %s%n", MobilePhone);
        super.displayCalls();
    }
}
