package ru.itis.longpolling.repository;

import ru.itis.longpolling.controller.rest.RestModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Messages {

    private static Messages m;

    public static Messages getInstance() {
        if (m == null) {
            m = new Messages();
        }
        return m;
    }

    public Map<Integer, ArrayList<String>> messages = new HashMap<>();

    public Map<Integer, String> newmessage = new HashMap<>();

    public Map<Integer, RestModel> fromadminmessage = new HashMap<Integer, ru.itis.longpolling.controller.rest.RestModel>();


}
