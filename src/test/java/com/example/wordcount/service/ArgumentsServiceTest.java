package com.example.wordcount.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.ApplicationArguments;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArgumentsServiceTest {
    private static final String FILEPATH_ARG_NAME = "filepath";

    private final ArgumentsService argumentsService = new ArgumentsService();

    @Nested
    class GetFilePath {
        @Test
        public void givenFilepathMissing_expectNull() {
            ApplicationArguments applicationArguments = mock(ApplicationArguments.class);
            assertNull(argumentsService.getFilePath(applicationArguments));
        }

        @Test
        public void givenFilepathContainingMultiplePaths_expectNull() {
            ApplicationArguments applicationArguments = mock(ApplicationArguments.class);
            when(applicationArguments.getOptionValues(FILEPATH_ARG_NAME)).thenReturn(List.of("path1", "path2"));
            assertNull(argumentsService.getFilePath(applicationArguments));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   "})
        public void givenFilepathContainingEmptyOrMadeOfSpacesSinglePath_expectNull(String path) {
            ApplicationArguments applicationArguments = mock(ApplicationArguments.class);
            when(applicationArguments.getOptionValues(FILEPATH_ARG_NAME)).thenReturn(List.of(path));
            assertNull(argumentsService.getFilePath(applicationArguments));
        }

        @ParameterizedTest
        @ValueSource(strings = {"path1", "path1  ", "  path1", "  path1   "})
        public void givenFilepathContainingNonEmptySinglePath_expectTrimmedFilePath(String path) {
            ApplicationArguments applicationArguments = mock(ApplicationArguments.class);
            when(applicationArguments.getOptionValues(FILEPATH_ARG_NAME)).thenReturn(List.of(path));
            assertEquals(path.trim(), argumentsService.getFilePath(applicationArguments));
        }
    }
}
