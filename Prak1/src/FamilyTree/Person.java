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

    public void print(int level) {
        System.out.println(this.toString());

        if (father != null) {
            System.out.print("└");
            for (int i = 0; i < level + 10; ++i) {
                System.out.print("─");
            }
            father.print(level + 10);
        }

        if (mother != null) {
            System.out.print("└");
            for (int i = 0; i < level + 10; ++i) {
                System.out.print("─");
            }
            mother.print(level + 10);
        }
    }

    @Override
    public String toString() {
        return String.format("Name: %s %s, Age: %d", firstName, surName, age);
    }
}
