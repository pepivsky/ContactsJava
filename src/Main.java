import model.Contact;
import model.Organization;
import model.Person;
import utils.SerializationUtils;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String fileName = "phonebook.db";
    public static File contactsDB;
    public static final List<Contact> contactList = new ArrayList<>();
    public static final List<Contact> searchedContacts = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length > 0) { // si hay argumentos entonces hay que cargar el archivo
            contactsDB = new File(args[0] + ".db");
            if (!contactsDB.exists()) {
                try {
                    if (contactsDB.createNewFile()) {
                        // creado
                        initMenu();
                    } else { // si existe hay que cargar datos
                        System.out.println("open " + contactsDB.getName());
                        // cargar datos
                        List<Contact> contacts = (ArrayList<Contact>) SerializationUtils.deserialize(contactsDB.getName());
                        contactList.addAll(contacts);

                        initMenu();
                    }

                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    // cargar datos
                    List<Contact> contacts = (ArrayList<Contact>) SerializationUtils.deserialize(contactsDB.getName());
                    contactList.addAll(contacts);

                    initMenu();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("no se pudo cargar el archivo");
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                } catch (ClassCastException e) {
                    System.out.println("casteo imposible");
                }
            }
        } else { // crear el archivo
            contactsDB = new File(fileName);
            try {
                contactsDB.createNewFile();
                initMenu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void initMenu() {
        Scanner scanner = new Scanner(System.in);
        String menuInput = "";

        while (!"exit".equals(menuInput)) {
            System.out.println("[menu] Enter action (add, list, search, count, exit): >");
            menuInput = scanner.nextLine();
            switch (menuInput) {
                case "add":
                    addNewContact();
                    break;
                case "list":
                    listMyContacts();
                    break;
                case "search":
                    searchContact();
                    break;
                case "count":
                    System.out.println("The Phone Book has " + contactList.size() + " records.");
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }

    private static void searchContact() {
        searchedContacts.clear();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter search query: >");
        String searchQuery = scanner.nextLine().toLowerCase();

        for (Contact contact : contactList) {
            if (contact.getClass() == Person.class) { // company
                Person person = (Person) contact;
                if (person.getName().toLowerCase().contains(searchQuery) || person.getSurname().toLowerCase().contains(searchQuery)) {
                    searchedContacts.add(person);
                }
                // search by number
                if (person.getPhoneNumber().contains(searchQuery)) {
                    searchedContacts.add(contact);
                }
            } else { // organization
                if (contact.getName().toLowerCase().contains(searchQuery)) {
                    searchedContacts.add(contact);
                }
                // search by number
                if (contact.getPhoneNumber().contains(searchQuery)) {
                    searchedContacts.add(contact);
                }
            }
        }
        listMySearchedContacts();
    }

    private static void listMySearchedContacts() {
        if (!searchedContacts.isEmpty()) {
            System.out.println("Found " + searchedContacts.size() + " results:");
            for (int i = 0; i < searchedContacts.size(); i++) {
                Contact contact = searchedContacts.get(i);
                System.out.println(i + 1 + ". " + contact.showName());
            }
            showSearchMenu();
        }
    }

    private static void showSearchMenu() {
        Scanner scanner = new Scanner(System.in);
        String option = "";
        while (!"back".equals(option)) {
            System.out.println("[search] Enter action ([number], back, again): >");
            option = scanner.nextLine();

            switch (option) {
                case "back":
                    break;
                case "again":
                    searchedContacts.clear();
                    searchContact();
                    break;
                default:
                    int index = Integer.parseInt(option) - 1;
                    Contact contact = searchedContacts.get(index);
                    System.out.println(contact.toString());
                    int realIndex = contactList.indexOf(contact);
                    recordMenu(realIndex);
                    break;
            }
            break;
        }
    }

    private static void listMyContacts() {
        if (contactList.isEmpty()) {
            System.out.println("No records to list!");
        } else {
            int index = 1;
            for (Contact contact : contactList) {
                System.out.println(index + ". " + contact.getName());
                index++;
            }
            System.out.println();
            showListMenu();
        }
    }

    private static void showListMenu() {
        Scanner scanner = new Scanner(System.in);
        String optionList = "";
        while (!"back".equals(optionList)) {

            System.out.println("[list] Enter action ([number], back): >");
            optionList = scanner.nextLine();

            if ("back".equals(optionList)) {
                break;
            } else {
                int index = Integer.parseInt(optionList) - 1;
                Contact contact = contactList.get(index);
                System.out.println(contact.toString());
                recordMenu(index);
                break;
            }
        }
    }

    private static void editContact(int index) {
        Contact currentContact = contactList.get(index);
        currentContact.edit();
        saveInFile();
    }

    private static void recordMenu(int index) {
        Scanner scanner = new Scanner(System.in);
        String menuInput = "";

        while (!"menu".equals(menuInput)) {
            System.out.println("[record] Enter action (edit, delete, menu): >");
            menuInput = scanner.nextLine();

            switch (menuInput) {
                case "edit":
                    editContact(index);
                    break;
                case "delete":
                    contactList.remove(index);
                    break;
                case "menu":
                    break;
                default:
                    break;
            }
        }
    }
    private static void addNewContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the type (person, organization): >");
        String type = scanner.nextLine();

        if (type.equals("person")) {
            addNewPerson();
        }
        if (type.equals("organization")) {
            addNewOrganization();
        }
    }

    private static void addNewOrganization() {
        Organization organization = new Organization();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the organization name: >");
        String name = scanner.nextLine();
        organization.setName(name);

        System.out.println("Enter the address: >");
        String address = scanner.nextLine();
        organization.setAddress(address);

        System.out.println("Enter number: >");
        String number = scanner.nextLine();
        organization.setPhoneNumber(number);

        contactList.add(organization);
        saveInFile();

        System.out.println("The record added.");
        System.out.println();
    }

    private static void addNewPerson() {
        Person contact = new Person();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter name: >");
        String newName = scanner.nextLine();
        contact.setName(newName);
        System.out.println("Enter surname: >");
        String newSurname = scanner.nextLine();
        contact.setSurname(newSurname);

        try {
            System.out.println("Enter birth date: >");
            LocalDate birthDate = LocalDate.parse(scanner.nextLine());
            contact.setBirthDate(birthDate);
        } catch (Exception e) {
            contact.setBirthDate(null);
        }

        try {
            System.out.println("Enter the gender (M, F): >");
            Character gender = scanner.nextLine().charAt(0);
            contact.setGender(gender);

        } catch (Exception e) {
            contact.setGender(null);
        }

        System.out.println("Enter number: >");
        String number = scanner.nextLine();
        contact.setPhoneNumber(number);

        contactList.add(contact);
        saveInFile();

        System.out.println("The record added.");
        System.out.println();
    }

    private static void saveInFile() {
        try {
            SerializationUtils.serialize(contactList, contactsDB.getName());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}

