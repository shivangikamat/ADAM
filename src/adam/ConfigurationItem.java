package adam;

public record ConfigurationItem(String filename, String command, String data, int lineNumber) {
    /**
     * @param command The name of the line from the input
     * @param data     The String that accompanied the command in the input
     * @param filename The name of the file
     * @param lineNumber The line number
     */
    public ConfigurationItem(String filename, String command, String data, int lineNumber) {
        this.filename =filename.substring(filename.lastIndexOf("\\")+1) ;
        this.command = command;
        this.data= data;
        this.lineNumber= lineNumber;

    }
}

