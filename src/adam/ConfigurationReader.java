package adam;

import javax.swing.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ConfigurationReader {
    public ConfigurationReader(InputStream in) {
    }

    public static String nowString = "2024-04-29T09:00+01:00[Europe/London]";
    public static boolean hasChanged = false;
    public static String commentValue = "";

    public static void readConfiguration(Configuration configuration, List<String> fileLines) {



        ZonedDateTime now = ApplicationConfigurationConstants.DEFAULT_NOW;
        boolean show_comments = ApplicationConfigurationConstants.DEFAULT_SHOW_COMMENTS;
        boolean welcome_message = ApplicationConfigurationConstants.DEFAULT_SHOW_WELCOME_MESSAGE;
        boolean exit_message = ApplicationConfigurationConstants.DEFAULT_SHOW_EXIT_MESSAGE;
        boolean show_config_status = ApplicationConfigurationConstants.DEFAULT_SHOW_CONFIG_STATUS;
        boolean assignment_file_loading = ApplicationConfigurationConstants.DEFAULT_ASSIGNMENT_FILE_LOADING;
        boolean assignment_item_processing = ApplicationConfigurationConstants.DEFAULT_ASSIGNMENT_ITEM_PROCESSING;
        boolean ignore_incomplete_assignments = ApplicationConfigurationConstants.DEFAULT_IGNORE_INCOMPLETE_ASSIGNMENTS;
        String output_type = ApplicationConfigurationConstants.DEFAULT_OUTPUT_TYPE;
        String comment = String.valueOf(Messages.DEFAULT_COMMENT);


        for (String line : fileLines) {
            String[] parts = line.trim().split("\t", 2);
            if (parts.length != 2) {
                // Invalid line, skip it
                continue;
            }
            String command = parts[0].trim().toLowerCase().replace(" ", "_");
            String data = parts[1].trim();
            boolean isValid = true;


            switch (command) {
                case "now":
                    nowString = data;
                    configuration.setnow(ZonedDateTime.parse(data, DateTimeFormatter.ISO_ZONED_DATE_TIME));
                    break;
                case "show_comments":
                    show_comments = isTrue(data);
                    data = String.valueOf(isTrue(data));
                    configuration.setShowComments(show_comments);
                    if (hasChanged) {
                        System.out.printf(Messages.OVERWRITING_APP_CONFIG_ITEM.toString(), "COMMENTS", commentValue.toUpperCase(), String.valueOf(isTrue(data)).toUpperCase());
                        System.out.println();
                    } else {

                        System.out.printf(Messages.SETTING_APP_CONFIG_ITEM.toString(), "COMMENTS", data.toUpperCase());
                        System.out.println();
                        hasChanged = true;
                        data = String.valueOf(isTrue(data));

                    }
                    commentValue = String.valueOf(isTrue(data));
                    break;

                case "welcome_message":
                    welcome_message = isTrue(data);
                    data = String.valueOf(isTrue(data));
                    configuration.setWelcomeMessage(welcome_message);
                    break;
                case "exit_message":
                    exit_message = isTrue(data);
                    data = String.valueOf(isTrue(data));
                    configuration.setExitMessage(exit_message);
                    break;
                case "show_config_status":
                    show_config_status = isTrue(data);
                    data = String.valueOf(isTrue(data));
                    configuration.setShowConfigStatus(show_config_status);
                    break;
                case "assignment_file_loading":
                    assignment_file_loading = isTrue(data);
                    data = String.valueOf(isTrue(data));
                    configuration.setAssignmentFileLoading(assignment_file_loading);
                    break;
                case "assignment_item_processing":
                    assignment_item_processing = isTrue(data);
                    data = String.valueOf(isTrue(data));
                    configuration.setAssignmentItemProcessing(assignment_item_processing);
                    break;
                case "ignore_incomplete_assignments":
                    ignore_incomplete_assignments = isTrue(data);
                    data = String.valueOf(isTrue(data));
                    configuration.setIgnoreIncompleteAssignments(ignore_incomplete_assignments);
                    break;
                case "output_type":
                    if (data.equalsIgnoreCase("SUMMARY") || data.equalsIgnoreCase("DAILY")) {
                        output_type = data;
                        configuration.setOutputType(output_type);

                    } else {
                        System.out.printf(Messages.INVALID_SUMMARY_TYPE.toString(), data.toUpperCase(), "[SUMMARY,DAILY]");
                    }
                    break;
                case "comment":
                    comment = data;
                    configuration.setComment(comment);
                    if (configuration.isShowComments()) {
                        System.out.println(comment);
                    }
                    break;
                default:
                    System.out.printf(Messages.INVALID_APP_CONFIG_ITEM.toString(), command.toUpperCase());
                    System.out.println();
                    System.out.printf(Messages.VALID_POSSIBILITIES.toString(),
                            "[ASSIGNMENT_FILE_LOADING, ASSIGNMENT_ITEM_PROCESSING," +
                                    " COMMENT, EXIT_MESSAGE, IGNORE_INCOMPLETE_ASSIGNMENTS," +
                                    " NOW, OUTPUT_TYPE, SHOW_COMMENTS, SHOW_CONFIG_STATUS, WELCOME_MESSAGE]");
                    isValid = false;
                    break;
            }
            if (isValid) {
                if (command.equalsIgnoreCase("show_comments")) {
                    continue;
                } else if (command.equalsIgnoreCase("show_config_status")) {
                    System.out.printf(Messages.SETTING_APP_CONFIG_ITEM.toString(), "CONFIG_STATUS", data.toUpperCase());
                } else if (command.equalsIgnoreCase("now")) {
                    System.out.printf(Messages.SETTING_APP_CONFIG_ITEM.toString(), "NOW", data);
                } else if (command.equalsIgnoreCase("comment")) {
                    continue;
                } else {
                    System.out.printf(Messages.SETTING_APP_CONFIG_ITEM.toString(), command.toUpperCase(), data.toUpperCase());
                }

            }
            System.out.println();
        }



        if (configuration.isShowConfigStatus()) {
            printConfiguration(configuration);
        }


    }
    public static boolean isTrue(String word){
        List<String> listOfValues = new ArrayList<>();
        listOfValues.add("on");
        listOfValues.add("display");
        listOfValues.add("yes");
        listOfValues.add("show");
        listOfValues.add("true");
        if (listOfValues.contains(word.toLowerCase())) {
            return true;
        }
        return false;
    }

    private static void printConfiguration(Configuration configuration){
        System.out.println(Messages.APPLICATION_SETTINGS.toString());
        System.out.println("ASSIGNMENT_FILE_LOADING: "+ String.valueOf(configuration.isAssignmentFileLoading()).toUpperCase());
        System.out.println("ASSIGNMENT_ITEM_PROCESSING: "+String.valueOf(configuration.isAssignmentItemProcessing()).toUpperCase());
        System.out.println("COMMENT: TRUE");
        System.out.println("EXIT_MESSAGE: "+String.valueOf(configuration.isExitMessage()).toUpperCase());
        System.out.println("IGNORE_INCOMPLETE_ASSIGNMENTS: "+String.valueOf(configuration.isIgnoreIncompleteAssignments()).toUpperCase());
        System.out.println("NOW: "+ nowString);
        System.out.println("OUTPUT_TYPE: "+String.valueOf(configuration.getOutputType()).toUpperCase());
        System.out.println("SHOW_COMMENTS: "+String.valueOf(configuration.isShowComments()).toUpperCase());
        System.out.println("SHOW_CONFIG_STATUS: "+String.valueOf(configuration.isShowConfigStatus()).toUpperCase());
        System.out.println("WELCOME_MESSAGE: "+String.valueOf(configuration.isWelcomeMessage()).toUpperCase());

    }
}