package adam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ADAM {

  protected final String[] args;
  protected Configuration configuration;
  protected List<String> fileLines;

  public ADAM(String[] args) {
    this.args = args;
    this.fileLines = new ArrayList<>();
    this.configuration = Configuration.getInstance();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.isEmpty()) {
          break;
        }
        fileLines.add(line);
      }


    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void run() {
    ConfigurationReader.readConfiguration(configuration, fileLines);

    if (configuration.isWelcomeMessage()) {
      System.out.printf(Messages.WELCOME.toString());
      System.out.println();
    }


    List<Assignment> assignmentList = BasicFileReader.processFiles(configuration, args);
    if (configuration.isShowComments()) {
      for (Assignment assignment : assignmentList) {
        if (!assignment.getComment().isEmpty()) {
          System.out.printf(Messages.APPLICATION_COMMENT.toString(), assignment.getComment());
          System.out.println();
        }
      }
    }

    if (assignmentList.isEmpty()) {
      System.out.println(Messages.NO_ASSIGNMENTS.toString());
    }

    Printer.printWorkload(configuration, assignmentList);
    if (configuration.isExitMessage()) {
      System.out.printf(Messages.EXIT.toString());
      System.out.println();
    }

  }


  public static void main(String[] args) {
    ADAM adam = new ADAM(args);
    adam.run();
  }
}
