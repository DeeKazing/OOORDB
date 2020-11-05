package FamilyTree;

public class Person {
    private String surName;
    private String firstName;
    private int age;
    Person father;
    Person mother;

    public Person() {

    }

    public Person(String lastName, String firstName, int age, Person father, Person mother) {
        this.surName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.father = father;
        this.mother = mother;
    }

    @Override
    public String toString() {
        return String.format("Name: %s %s, Age: %d", firstName, surName, age);
    }
}
