
package adam;



import javax.xml.transform.SourceLocator;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;


public class Printer {

    public static float hoursPerDay(Duration duration, BigDecimal totalHours) {
        long daysLeft= duration.toDays();
        float hours = (float) totalHours.longValue() /daysLeft;
        BigDecimal bigDecimal= new BigDecimal(hours);
        bigDecimal = bigDecimal.setScale(ApplicationConfigurationConstants.DECIMAL_PLACES, ApplicationConfigurationConstants.ROUNDING_MODE);
        return bigDecimal.floatValue();
    }

    public static void printWorkload(Configuration config, List<Assignment> assignmentList) {
        switch ( config.getOutputType() ) {
            case "summary": {
                printSummary(config, assignmentList);
                break;
            }
            case "daily": {
                printWeekly(config, assignmentList);
                break;
            }

            default: printSummary(config, assignmentList);
        }
    }

    public static void printSummary(Configuration configuration, List<Assignment> assignmentList) {
        for (Assignment assignment : assignmentList) {
            Duration duration = calculateDuration(configuration, assignment);
            System.out.printf(Messages.TIMETABLE_SUMMARY.toString(), assignment.getName(), assignment.getCourse(),
                    hoursPerDay(duration, new BigDecimal(assignment.getHours())),duration.toDays(), assignment.getEnd());
            System.out.println();
        }

    }

    public static void printWeekly(Configuration configuration, List<Assignment> assignmentList) {
        ZonedDateTime earlyDate= configuration.getnow();
        ZonedDateTime lateDate= configuration.getnow();
        for (Assignment assignment : assignmentList) {
            ZonedDateTime assignmentStartDate = ZonedDateTime.parse(assignment.getStart());
            ZonedDateTime assignmentEndDate = ZonedDateTime.parse(assignment.getEnd());
            if (assignmentStartDate.isBefore(earlyDate)) {
                earlyDate = assignmentStartDate;
            }
            if (assignmentEndDate.isAfter(lateDate)) {
                lateDate = assignmentEndDate;
            }
        }
        int duration=(int)Duration.between(earlyDate,lateDate).toDays();
        for (int day=0; day<=duration; day++){
            ZonedDateTime currentDate= earlyDate.plusDays(day);
            String dayOfWeek = currentDate.getDayOfWeek().toString();
            String formattedDayofWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1).toLowerCase();

            int dayOfMonth = currentDate.getDayOfMonth();
            String month = currentDate.getMonth().toString();
            String formattedMonth = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();
            int year = currentDate.getYear();
            System.out.println(formattedDayofWeek+ ", "+ formattedMonth + " "+ dayOfMonth +", " + year);
            float totalHours=0;
            for ( Assignment assignment:assignmentList) {
                ZonedDateTime assignmentStartDate = ZonedDateTime.parse(assignment.getStart());
                ZonedDateTime assignmentLateDate = ZonedDateTime.parse(assignment.getEnd());
                if ((assignmentStartDate.isBefore(currentDate) || assignmentStartDate.isEqual(currentDate) || assignmentStartDate.getDayOfMonth() == currentDate.getDayOfMonth()) && (assignmentLateDate.isAfter(currentDate) || assignmentLateDate.isEqual(currentDate))) {
                    totalHours += hoursPerDay(calculateDuration(configuration, assignment), new BigDecimal(assignment.getHours()));
                    System.out.printf(Messages.HOURS_ON.toString(), hoursPerDay(calculateDuration(configuration, assignment), new BigDecimal(assignment.getHours())), assignment.getName(), assignment.getCourse());
                    System.out.println();
                }

            }
            System.out.println(Messages.TOTAL_TOP_LINE);
            System.out.printf(Messages.DAILY_TOTAL.toString(), totalHours);
            System.out.println();
            System.out.println(Messages.TOTAL_BOTTOM_LINE);




        }

    }

    private static Duration calculateDuration(Configuration configuration, Assignment assignment) {
        ZonedDateTime now = configuration.getnow();
        ZonedDateTime start = ZonedDateTime.parse(assignment.getStart()).isBefore(now) ? now : ZonedDateTime.parse(assignment.getStart());
        Duration duration = Duration.between(start, ZonedDateTime.parse(assignment.getEnd())).plusDays(1);
        return duration;
    }
}