package com.enzo.testaufgabe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by enzo on 11.04.18.
 */

public class Person implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("company")
    @Expose
    private Company company;

    @SerializedName("address")
    @Expose
    private Address address;

    public Person(String id, String username, String name,
                  String email, String phone, String website,
                  String companyName, String catchPhrase,
                  String businessServices, String fullAddress,
                  String zipcode, String coordinates) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.company = new Company(companyName, catchPhrase, businessServices);
        this.address = new Address(fullAddress, zipcode, coordinates);
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public Company getCompany() {
        return company;
    }

    public Address getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!id.equals(person.id)) return false;
        if (!name.equals(person.name)) return false;
        if (!username.equals(person.username)) return false;
        if (!email.equals(person.email)) return false;
        if (!phone.equals(person.phone)) return false;
        if (!website.equals(person.website)) return false;
        if (!company.equals(person.company)) return false;
        return address.equals(person.address);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + website.hashCode();
        result = 31 * result + company.hashCode();
        result = 31 * result + address.hashCode();
        return result;
    }
}