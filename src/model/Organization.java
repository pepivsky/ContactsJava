package model;

import model.Contact;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Organization extends Contact implements Serializable {
    private static final long serialVersionUID = 88L;
    private String address;


    public Organization() {
    }


    @Override
    public void edit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a field (name, address, number): >");
        String field = scanner.nextLine();
        this.changeValue(field);
    }

    @Override
    public String showName() {
        return super.getName();
    }

    @Override
    void changeValue(String value) {
        Scanner scanner = new Scanner(System.in);

        switch (value) {
            case "name":
                System.out.println("Enter name: >");
                super.setName(scanner.nextLine());
                break;
            case "address":
                System.out.println("Enter address: >");
                this.setAddress(scanner.nextLine());
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


    public String getAddress() {
        return address == null ? "[no data]" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "model.Organization name: " + super.getName() + "\n" +
                "Address: " + address + "\n" +
                "Number: " + super.getPhoneNumber() + "\n" +
                "Time created: " + super.getTimeCreated() + "\n" +
                "Time last edit: " + (super.getLastEdit() == null ? "[no data]" : super.getLastEdit()) + "\n";
    }
}
