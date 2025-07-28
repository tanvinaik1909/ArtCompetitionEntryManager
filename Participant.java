package com.artcomp;

public class Participant {
    private int id;
    private String name;
    private int age;
    private String artType;
    private String contact;

    public Participant(int id, String name, int age, String artType, String contact) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.artType = artType;
        this.contact = contact;
    }

    public Participant(String name, int age, String artType, String contact) {
        this.name = name;
        this.age = age;
        this.artType = artType;
        this.contact = contact;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getArtType() { return artType; }
    public String getContact() { return contact; }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age +
               ", Art Type: " + artType + ", Contact: " + contact;
    }
}
