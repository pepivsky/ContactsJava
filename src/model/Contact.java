package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Contact implements Serializable {
    private static final long serialVersionUID = 99L;
    private String name;
    private String phoneNumber;
    private final LocalDateTime timeCreated;
    private LocalDateTime lastEdit;


    public Contact() {
        timeCreated = LocalDateTime.now();
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public LocalDateTime getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(LocalDateTime lastEdit) {
        this.lastEdit = lastEdit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^(^((\\+\\d )|(\\+))?\\(?\\w+\\)?(-| )?(\\w{2,})?(-| )?(\\w{2,})?(-| )?(\\w{2,})?$)|(^((\\+\\d )|(\\+))?\\w+(-| )?\\(?(\\w{2,})\\)?(-| )?(\\w{2,})?(-| )?(\\w{2,})?$)$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.find()) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Wrong number format!");
            this.phoneNumber = "[no number]";
        }
    }

    abstract void changeValue(String value);

    public abstract void edit();

    public abstract String showName();


}
