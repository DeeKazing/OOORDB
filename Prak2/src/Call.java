public class Call {
    int Duration;
    long Time;
    int Date;
    Consultant Consultant;
    Client Client;

    public Call( int date, long time, int duration, Client client, Consultant consultant) {
        this.Client = client;
        this.Consultant = consultant;
        this.Date = date;
        this.Time = time;
        this.Duration = duration;
        if (client != null) client.allCalls.add(this);
        if (consultant != null) consultant.allCalls.add(this);
    }

    public void display() {
        System.out.printf("Call between Client %s and Consultant %s | Day: %d, Time: %x, Duration: %d",
                Client.Name, Consultant.Name, Date, Time, Duration);
    }
}
