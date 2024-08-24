package adam;

import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.ZonedDateTime;
public class Configuration {
    private static Configuration instance;
    private ZonedDateTime now;
    private boolean show_comments;
    private boolean welcome_message;
    private boolean exit_message;
    private boolean show_config_status;
    private boolean assignment_file_loading;
    private boolean assignment_item_processing;
    private boolean ignore_incomplete_assignments;
    private String output_type;
    private String comment;

    // Constructor
    private Configuration() {
        assignment_file_loading = ApplicationConfigurationConstants.DEFAULT_ASSIGNMENT_FILE_LOADING;
        assignment_item_processing = ApplicationConfigurationConstants.DEFAULT_ASSIGNMENT_ITEM_PROCESSING;
        exit_message = ApplicationConfigurationConstants.DEFAULT_SHOW_EXIT_MESSAGE;
        welcome_message = ApplicationConfigurationConstants.DEFAULT_SHOW_WELCOME_MESSAGE;
        ignore_incomplete_assignments = ApplicationConfigurationConstants.DEFAULT_IGNORE_INCOMPLETE_ASSIGNMENTS;
        now = ApplicationConfigurationConstants.DEFAULT_NOW;
        output_type = ApplicationConfigurationConstants.DEFAULT_OUTPUT_TYPE;
        show_comments = ApplicationConfigurationConstants.DEFAULT_SHOW_COMMENTS;
        show_config_status = ApplicationConfigurationConstants.DEFAULT_SHOW_CONFIG_STATUS;
        comment = String.valueOf(Messages.DEFAULT_COMMENT);
    }

    public static synchronized Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public ZonedDateTime getnow() {
        return now;
    }

    public boolean isShowComments() {
        return show_comments;
    }

    public boolean isWelcomeMessage() {
        return welcome_message;
    }

    public boolean isExitMessage() {
        return exit_message;
    }

    public boolean isShowConfigStatus() {
        return show_config_status;
    }

    public boolean isAssignmentFileLoading() {
        return assignment_file_loading;
    }

    public boolean isAssignmentItemProcessing() {
        return assignment_item_processing;
    }

    public boolean isIgnoreIncompleteAssignments() {
        return ignore_incomplete_assignments;
    }

    public String getOutputType() {
        return output_type;
    }

    public String getComment() {
        return comment;
    }

    // Setters
    public void setnow(ZonedDateTime now) {
        this.now = now;
    }

    public void setShowComments(boolean showComments) {
        this.show_comments = showComments;
    }

    public void setWelcomeMessage(boolean welcomeMessage) {
        this.welcome_message = welcomeMessage;
    }

    public void setExitMessage(boolean exitMessage) {
        this.exit_message = exitMessage;
    }

    public void setShowConfigStatus(boolean showConfigStatus) {
        this.show_config_status = showConfigStatus;
    }

    public void setAssignmentFileLoading(boolean assignmentFileLoading) {
        this.assignment_file_loading = assignmentFileLoading;
    }

    public void setAssignmentItemProcessing(boolean assignmentItemProcessing) {
        this.assignment_item_processing = assignmentItemProcessing;
    }

    public void setIgnoreIncompleteAssignments(boolean ignoreIncompleteAssignments) {
        this.ignore_incomplete_assignments = ignoreIncompleteAssignments;
    }

    public void setOutputType(String outputType) {
        this.output_type = outputType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
