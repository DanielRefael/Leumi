package com.example.danielrefael.leumi;

/**
 * Created by daniel refael on 18/01/2018.
 */

public class User {

   private String username, password,lastName, privateName , email ,address;

    public User(String username,String password,String lastName,String privateName ,String email ,String address){

        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.privateName = privateName;
        this.email = email;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPrivateName() {
        return privateName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
