package com.example.wordcount.service;

import com.example.wordcount.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WordcountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WordcountService.class);

    public Set<Map.Entry<String, Integer>> processFile(String filePath) throws FileNotFoundException, CustomException {
        LOGGER.info("printWordCount for file {}", filePath);
        Set<Map.Entry<String, Integer>> result = null;
        final long startTime = System.currentTimeMillis();

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                Map<String, Integer> wordMap = new HashMap<>();
                reader.lines().forEach(aLine -> {
                    String[] wordList = aLine.toLowerCase().split("\\s*[^a-zA-Z]+\\s*");
                    for (String aWord : wordList) {
                        if (!wordMap.containsKey(aWord)) {
                            wordMap.put(aWord, 1);
                        } else {
                            Integer existingCount = wordMap.get(aWord);
                            wordMap.replace(aWord, existingCount + 1);
                        }
                    }
                });

                if (!wordMap.isEmpty()) {
                    result = wordMap.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey())
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                }

                System.out.printf("It took %d milliseconds to complete the word count for file %s.%n",
                        System.currentTimeMillis() - startTime, filePath);
                return result;
            } catch (IOException e) {
                String errorMsg = String.format("Unexpected error while reading file %s.", filePath);
                LOGGER.error(errorMsg, e);
                throw new CustomException(errorMsg);
            }
        } else {
            String errorMsg = String.format("No file at path %s", filePath);
            LOGGER.error(errorMsg);
            throw new FileNotFoundException(errorMsg);
        }
    }
}
