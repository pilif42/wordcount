package com.example.wordcount.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ArgumentsService {
    private static final String FILEPATH_ARG_NAME = "filepath";

    public String getFilePath(ApplicationArguments args) {
        String result = null;
        List<String> filePaths = args.getOptionValues(FILEPATH_ARG_NAME);
        if (filePaths != null && filePaths.size() == 1) {
            String trimmedFilePath = filePaths.get(0).trim();
            if (StringUtils.hasLength(trimmedFilePath)) {
                result = trimmedFilePath;
            }
        }
        return result;
    }
}
