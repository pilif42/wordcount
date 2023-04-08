package com.example.wordcount.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class DisplayService {
    public void print(Set<Map.Entry<String, Integer>> entrySet) {
        if (entrySet == null || entrySet.isEmpty()) {
            System.out.println("No word found");
        } else {
            entrySet.forEach(stringIntegerEntry -> System.out.println(stringIntegerEntry.getKey() + ": " + stringIntegerEntry.getValue()));
        }
    }
}
