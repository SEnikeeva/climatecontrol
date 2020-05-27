package ru.itis.dto;

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

    public Map<Integer, RestModel> fromadminmessage = new HashMap<>();


}
