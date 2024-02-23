public class Person implements Payable {  //interf imiplemant, to class extends
    private static int talon = 1; //talon id sanaidy
    private final Integer id; // adamnyn IDi
    private String  name;
    private String surname;
    public Person(){
        id = Person.talon++;
    }
    public Person(String name, String surname) {
        this.id = Person.talon++;
        this.name = name;
        this.surname = surname;
    }
    public int getId() {
        return id;
    }
    public String getname() {return name;}
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {this.surname = surname;}
    public String getPosition() {
        return "Person";
    }

    @Override
    public String toString() {
        return getPosition() + ": " + id + ". " + name + " " + surname;
    }
    public double getPaymentAmount() {
        return 0.0; // Default implementation for Person
    }
}

