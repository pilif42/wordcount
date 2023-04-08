# wordcount
A command-line application that takes a path to a file as an argument and prints a word count of its contents.

Instructions have been written and tested on Ubuntu 22.10 using OpenJDK 17.0.6.
In the commands below, replace the placeholder PROJECT_DIR with the location where you have cloned the project, e.g. /home/philippe/code/wordcount

## To build
./mvnw clean install

## To run

### Cmd line option
./mvnw clean spring-boot:run -Dspring-boot.run.arguments=--filepath=PROJECT_DIR/src/test/resources/sample.txt -Dspring-boot.run.profiles=production

### IntelliJ option
Create an Application Run Config:
- Point to class com.example.wordcount.WordcountApplication
- For CLI args, use: --filepath=PROJECT_DIR/src/test/resources/sample.txt

## To test

### Error scenarios

#### Invalid arguments
./mvnw clean spring-boot:run -Dspring-boot.run.arguments=--customArgument=custom
Expect:
Invalid arguments provided. [--customArgument=custom]
Please use --filepath=FULL_PATH_TO_FILE

#### File does not exist
./mvnw clean spring-boot:run -Dspring-boot.run.arguments=--filepath=PROJECT_DIR/src/test/resources/unknown.txt
Expect:
No file at path PROJECT_DIR/src/test/resources/unknown.txt
Please verify the location provided for argument filepath

### Positive scenarios
./mvnw spring-boot:run -Dspring-boot.run.arguments=--filepath=PROJECT_DIR/src/test/resources/sample.txt
Expect:
et: 6
eget: 5
...
