package adam;

public class Assignment {
    private String name;
    private String hours;
    private String start;
    private String end;
    protected String course;
    protected String points;
    protected String comment;

    public Assignment(String name, String hours, String start, String end, String course, String points, String comment) {
        this.name = name;
        this.hours = hours;
        this.start = start;
        this.end = end;
        this.course = course;
        this.points = points;
        this.comment = comment;
    }

    public Assignment(){

    }
    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}