public class Person {
    // declare variables
    private String name;
    private String surname;
    private String email;

    public Person(String name, String surname, String email) {  // constructor for the Person class with three parameters name, surname, and email
        this.name = name;  // assigns the value of the name parameter to the name instance variable using this keyword, which refers to the current object
        this.surname = surname;
        this.email = email;
    }

    public String getName() {  // declares a public method named getName that returns a String
        return name;  // returns the value of the name instance variable
    }

    public void setName(String name) {   // declares a public method named setName that takes a parameter of type String name
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
