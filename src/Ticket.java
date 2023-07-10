public class Ticket {
    // declare variables
    private int row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(int row, int seat, double price, Person person) {  // constructor for the Ticket class with four arguments
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }


    // get methods return the value of the corresponding instance variable and the set methods set the value of the corresponding instance variable
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    // prints the details of a Ticket object including the Person object associated with the ticket
    public void print() {
        System.out.println("Person name   : " + person.getName());
        System.out.println("Person surname: " + person.getSurname());
        System.out.println("Person email  : " + person.getEmail());
        System.out.println("Row  : " + (row + 1));
        System.out.println("Seat : " + (seat+1));
        System.out.println("Price: $" + price);
    }
}

