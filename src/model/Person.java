package model;

import model.Contact;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Person extends Contact implements Serializable {
    private static final long serialVersionUID = 77L;
    private String surname;
    private LocalDate birthDate;
    private Character gender;

    public Person() {
    }

    @Override
    public void edit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a field (name, surname, birth, gender, number): >");
        String field = scanner.nextLine();
        this.changeValue(field);
    }

    @Override
    public String showName() {
        return super.getName() + " " + surname;
    }

    @Override
    void changeValue(String value) {
        Scanner scanner = new Scanner(System.in);

        switch (value) {
            case "name":
                System.out.println("Enter name: >");
                super.setName(scanner.nextLine());
                break;
            case "surname":
                System.out.println("Enter surname: >");
                this.setSurname(scanner.nextLine());
                break;
            case "birth":
                System.out.println("Enter birth: >");
                this.setBirthDate(LocalDate.parse(scanner.nextLine()));
                break;
            case "gender":
                System.out.println("Enter gender: >");
                this.setGender(scanner.nextLine().charAt(0));
                break;
            case "number":
                System.out.println("Enter number: >");
                super.setPhoneNumber(scanner.nextLine());
                break;
            default:
        }
        super.setLastEdit(LocalDateTime.now());
        System.out.println("Saved");
        System.out.println(this.toString());
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Name: " + super.getName() + "\n" +
                "Surname: " + surname + "\n" +
                "Birth date: " + (birthDate == null ? "[no data]" : birthDate) + "\n" +
                "Gender: " + (gender == null ? "[no data]" : gender) + "\n" +
                "Number: " + super.getPhoneNumber() + "\n" +
                "Time created: " + super.getTimeCreated() + "\n" +
                "Time last edit: " + (super.getLastEdit() == null ? "[no data]" : super.getLastEdit()) + "\n";
    }
}
