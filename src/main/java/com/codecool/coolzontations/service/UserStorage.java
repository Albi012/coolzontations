package com.codecool.coolzontations.service;

import com.codecool.coolzontations.model.User;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserStorage {

    private List<User> users = new LinkedList<>();


    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }
}
