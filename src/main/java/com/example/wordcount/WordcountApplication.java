package com.example.wordcount;

import com.example.wordcount.exception.CustomException;
import com.example.wordcount.service.ArgumentsService;
import com.example.wordcount.service.DisplayService;
import com.example.wordcount.service.WordcountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class WordcountApplication implements ApplicationRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(WordcountApplication.class);

	private final ArgumentsService argumentsService;
	private final WordcountService wordcountService;
	private final DisplayService displayService;

	public WordcountApplication(ArgumentsService argumentsService, WordcountService wordcountService, DisplayService displayService) {
		this.argumentsService = argumentsService;
		this.wordcountService = wordcountService;
		this.displayService = displayService;
	}

	public static void main(String[] args) {
		SpringApplication.run(WordcountApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		LOGGER.info("Executing the word count app...");
		String filePath = argumentsService.getFilePath(args);
		if (filePath == null) {
			String errorMsg = "Invalid arguments provided. " + Arrays.toString(args.getSourceArgs());
			LOGGER.error(errorMsg);
			System.out.println(errorMsg + "\nPlease use --filepath=FULL_PATH_TO_FILE");
		} else {
			try {
				Set<Map.Entry<String, Integer>> result = wordcountService.processFile(filePath);
				displayService.print(result);
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage() + "\nPlease verify the location provided for argument filepath");
			} catch (CustomException e) {
				System.out.println(e.getMessage() + "\nPlease retry.");
			}
		}
	}
}
