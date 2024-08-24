package adam;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BasicFileReader {

    public static List<Assignment> processFiles(Configuration configuration, String[] fileNames) {
        List <Assignment> assignmentList = new ArrayList<>();
        for (String fileName : fileNames) {
            Assignment assignment= new Assignment();
            if (configuration.isAssignmentFileLoading()) {
                System.out.printf(Messages.LOADING_ASSIGNMENT.toString(),  fileName.substring(fileName.lastIndexOf("\\")+1));
                System.out.println();
            }

            try {
                File myObj = new File(fileName); // Use the fileName passed as argument
                Scanner myReader = new Scanner(myObj);

                int i=0;
                List<ConfigurationItem> listOfConfigurationItem = new ArrayList<>();
                while (myReader.hasNextLine()) {
                    i++;
                    String line = myReader.nextLine();
                    String[] parts = line.split("\t");
                    if (parts.length != 2) {
                        // Skip invalid lines
                        continue;
                    }

                    String command = parts[0];
                    String value = parts[1];

                    ConfigurationItem configLine= new ConfigurationItem(fileName, command.toUpperCase(), value, i);
                    listOfConfigurationItem.add(configLine);

                    // Assign values to Assignment parameters based on command
                    boolean isValid = true;
                    switch (command) {
                        case "name":
                            assignment.setName(value);
                            break;
                        case "start":
                            assignment.setStart(String.valueOf(ZonedDateTime.parse(value)));
                            break;
                        case "end":
                            assignment.setEnd(String.valueOf(ZonedDateTime.parse(value)));
                            break;
                        case "hours":
                            assignment.setHours(String.valueOf(Integer.parseInt(value)));
                            break;
                        case "course":
                            assignment.setCourse(value);
                            break;
                        case "points":
                            assignment.setPoints(value);
                            break;
                        case "comment":
                            assignment.setComment(value);
                            break;
                        default:
                            isValid= false;
                            break;

                    }
                    if (configuration.isAssignmentItemProcessing()) {
                        System.out.printf(Messages.READ_CONFIGURATION_ITEM.toString(), configLine);
                        System.out.println();

                        if (!isValid){
                            System.out.printf(Messages.UNKNOWN_ASSIGNMENT_COMMAND.toString(), command);
                            System.out.println();
                            System.out.printf(Messages.VALID_POSSIBILITIES.toString(), "[COMMENT, COURSE, END, HOURS, NAME, POINTS, START]");
                            System.out.println();
                        }
                    }


                }

                if (assignment.getName()==null) {
                    System.out.printf(Messages.MISSING_ASSIGNMENT_ITEM.toString(),fileName.substring(fileName.lastIndexOf("\\")+1),"NAME");
                    System.out.println();

                }
                if (assignment.getStart()==null) {
                    System.out.printf(Messages.MISSING_ASSIGNMENT_ITEM.toString(), fileName.substring(fileName.lastIndexOf("\\")+1), "START");
                    System.out.println();

                }
                if (assignment.getEnd()==null) {
                    System.out.printf(Messages.MISSING_ASSIGNMENT_ITEM.toString(), fileName.substring(fileName.lastIndexOf("\\")+1), "END");
                    System.out.println();

                }
                if (assignment.getHours()==null) {
                    System.out.printf(Messages.MISSING_ASSIGNMENT_ITEM.toString(), fileName.substring(fileName.lastIndexOf("\\")+1), "HOURS");
                    System.out.println();

                }
                myReader.close();

            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            assignment.course= assignment.getCourse()!=null?assignment.getCourse():Messages.DEFAULT_COURSE.toString();
            assignment.points= assignment.getPoints()!=null?assignment.getPoints():Messages.DEFAULT_POINTS.toString();
            assignment.comment= assignment.getComment()!=null?assignment.getComment():Messages.DEFAULT_COMMENT.toString();
            assignmentList.add(assignment);
        }

        return assignmentList;
    }
}
