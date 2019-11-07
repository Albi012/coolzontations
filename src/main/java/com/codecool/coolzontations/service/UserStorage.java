package com.codecool.coolzontations.service;

import com.codecool.coolzontations.model.User;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserStorage {

    private List<User> users = new LinkedList<>();


    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserByID(Integer id){
        try {
            Optional<User> foundUser = users.stream().filter(user -> user.getId() == id).findFirst();
            return foundUser.orElseThrow();
        } catch (NoSuchElementException e){
            return null;
        }

        }
    }

