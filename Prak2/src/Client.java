public class Client extends Person {
    String Account;

    public Client(long id, String name, String phone, String account) {
        super(id, name, phone);
        this.Account = account;
    }

    @Override
    public void display() {
        System.out.print("Client: ");
        super.display();
        System.out.printf(", Account: %s%n", Account);
        super.displayCalls();
    }
}
