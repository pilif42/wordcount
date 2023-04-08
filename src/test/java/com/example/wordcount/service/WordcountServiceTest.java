package com.example.wordcount.service;

import com.example.wordcount.exception.CustomException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WordcountServiceTest {
    private final WordcountService wordcountService = new WordcountService();

    @Nested
    class ProcessFile {
        @Test
        public void givenFileDoesNotExist_expectFileNotFoundException() {
            Exception exception = assertThrows(FileNotFoundException.class, () -> wordcountService.processFile("someRandomFilePath"));
            assertEquals("No file at path someRandomFilePath", exception.getMessage());
        }

        @Test
        public void givenFileIsEmpty_expectNull() throws FileNotFoundException, CustomException {
            assertNull(wordcountService.processFile(getFilePath("empty.txt")));
        }

        @Test
        public void givenFileContainsMathSymbolsOnly_expectNull() throws FileNotFoundException, CustomException {
            assertNull(wordcountService.processFile(getFilePath("mathsymbols.txt")));
        }

        @Test
        public void givenBufferedReaderThrowsIOException_expectCustomException() {
            try (MockedStatic<Files> mockedStatic = Mockito.mockStatic(Files.class)) {
                mockedStatic.when(() -> Files.exists(ArgumentMatchers.any(Path.class))).thenReturn(true);
                mockedStatic.when(() -> Files.newBufferedReader(ArgumentMatchers.any(Path.class))).thenThrow(new IOException());
                assertThrows(CustomException.class, () -> wordcountService.processFile("somePath"));
            }
        }

        @Test
        public void givenSmallSampleFile_expectCorrectlyOrderedMap() throws FileNotFoundException, CustomException {
            Map<String, Integer> expectedMap = new LinkedHashMap<>();
            expectedMap.put("et", 6);
            expectedMap.put("eget", 5);
            expectedMap.put("in", 5);
            expectedMap.put("at", 4);
            expectedMap.put("eu", 4);
            expectedMap.put("id", 4);
            expectedMap.put("volutpat", 1);
            assertEquals(expectedMap.entrySet(), wordcountService.processFile(getFilePath("smallsample.txt")));
        }

        private String getFilePath(String fileName) {
            File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
            return file.getPath();
        }
    }
}
